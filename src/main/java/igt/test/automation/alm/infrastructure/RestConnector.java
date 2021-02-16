
package igt.test.automation.alm.infrastructure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class keeps the state of the connection for the examples. This class is
 * a thus sharing state singleton. All examples get the instance in their
 * default constructors - (cookies, server url).
 *
 * Some simple methods are implemented to get commonly used paths.
 *
 */
public final class RestConnector {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager.getLogger(RestConnector.class);

    /** Cookies collection. */
    protected Map<String, String> cookies;

    /**
     * This is the URL to the ALM application. For example:
     * http://myhost:8080/qcbin. Make sure that there is no slash at the end.
     */
    protected String serverUrl;

    protected String domain;

    protected String project;

    public RestConnector init(final Map<String, String> cookies, final String serverUrl,
            final String domain, final String project) {

        this.cookies = cookies;
        this.serverUrl = serverUrl;
        this.domain = domain;
        this.project = project;

        return this;
    }

    private RestConnector() {
    }

    private static RestConnector instance = new RestConnector();

    public static RestConnector getInstance() {
        return instance;
    }

    public String buildEntityCollectionUrl(final String entityType) {
        return buildUrl("rest/domains/" + domain + "/projects/" + project + "/"
                + entityType + "s");
    }

    /**
     * @param path
     *            on the server to use
     * @return a url on the server for the path parameter
     */
    public String buildUrl(final String path) {

        return String.format("%1$s/%2$s", serverUrl, path);
    }
    // ====================================================================================================

    public String buildEntityUrl(final String entityType, final String id)
            throws Exception {
        return buildEntityCollectionUrl(entityType) + "/" + id;
    }

    // ======================================================================
    /**
     * @return the cookies
     */
    public Map<String, String> getCookies() {
        return cookies;
    }

    /**
     * @param cookies
     *            the cookies to set
     */
    public void setCookies(final Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Response httpPut(final String url, final byte[] data,
            final Map<String, String> headers) throws Exception {

        return doHttp("PUT", url, null, data, headers, cookies);
    }

    public Response httpPost(final String url, final byte[] data,
            final Map<String, String> headers) throws Exception {

        return doHttp("POST", url, null, data, headers, cookies);
    }

    public Response httpDelete(final String url, final Map<String, String> headers)
            throws Exception {

        return doHttp("DELETE", url, null, null, headers, cookies);
    }

    public Response httpGet(final String url, final String queryString,
            final Map<String, String> headers) throws Exception {

        return doHttp("GET", url, queryString, null, headers, cookies);
    }

    /**
     * @param type
     *            http operation: get post put delete
     * @param url
     *            to work on
     * @param queryString
     * @param data
     *            to write, if a writable operation
     * @param headers
     *            to use in the request
     * @param cookies
     *            to use in the request and update from the response
     * @return http response
     * @throws Exception
     */
    private Response doHttp(final String type, String url, final String queryString,
            final byte[] data, final Map<String, String> headers,
            final Map<String, String> cookies) throws Exception {

        if ((queryString != null) && !queryString.isEmpty()) {

            url += "?" + queryString;
        }

        HttpURLConnection con = (HttpURLConnection) new URL(url)
                .openConnection();

        con.setRequestMethod(type);
        String cookieString = getCookieString();

        prepareHttpRequest(con, headers, data, cookieString);
        con.connect();
        Response ret = retrieveHtmlResponse(con);

        updateCookies(ret);

        return ret;
    }

    /**
     * @param con
     *            connection to set the headers and bytes in
     * @param headers
     *            to use in the request, such as content-type
     * @param bytes
     *            the actual data to post in the connection.
     * @param cookieString
     *            the cookies data from clientside, such as lwsso, qcsession,
     *            jsession etc.
     * @throws java.io.IOException
     */
    private void prepareHttpRequest(final HttpURLConnection con,
            final Map<String, String> headers, final byte[] bytes, final String cookieString)
            throws IOException {

        String contentType = null;

        // attach cookie information if such exists
        if ((cookieString != null) && !cookieString.isEmpty()) {

            con.setRequestProperty("Cookie", cookieString);
        }

        // send data from headers
        if (headers != null) {

            // Skip the content-type header - should only be sent
            // if you actually have any content to send. see below.
            contentType = headers.remove("Content-Type");

            Iterator<Entry<String, String>> headersIterator = headers.entrySet()
                    .iterator();
            while (headersIterator.hasNext()) {
                Entry<String, String> header = headersIterator.next();
                con.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        // If there's data to attach to the request, it's handled here.
        // Note that if data exists, we take into account previously removed
        // content-type.
        if ((bytes != null) && (bytes.length > 0)) {

            con.setDoOutput(true);

            // warning: if you add content-type header then you MUST send
            // information or receive error.
            // so only do so if you're writing information...
            if (contentType != null) {
                con.setRequestProperty("Content-Type", contentType);
            }

            OutputStream out = con.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
        }
    }

    /**
     * @param con
     *            that is already connected to its url with an http request, and
     *            that should contain a response for us to retrieve
     * @return a response from the server to the previously submitted http
     *         request
     * @throws Exception
     */
    private Response retrieveHtmlResponse(final HttpURLConnection con)
            throws Exception {

        Response ret = new Response();

        ret.setStatusCode(con.getResponseCode());
        ret.setResponseHeaders(con.getHeaderFields());

        InputStream inputStream;
        // select the source of the input bytes, first try 'regular' input
        try {
            inputStream = con.getInputStream();
        } catch (Exception e) {
            /*
             * If the connection to the server somehow failed, for example 404
             * or 500, con.getInputStream() will throw an exception, which we'll
             * keep. We'll also store the body of the exception page, in the
             * response data.
             */
            inputStream = con.getErrorStream();
            ret.setFailure(e);
        }

        // This actually takes the data from the previously set stream
        // (error or input) and stores it in a byte[] inside the response
        ByteArrayOutputStream container = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int read;
        while ((read = inputStream.read(buf, 0, 1024)) > 0) {
            container.write(buf, 0, read);
        }

        ret.setResponseData(container.toByteArray());

        return ret;
    }

    private void updateCookies(Response response) {

        Iterable<String> newCookies = response.getResponseHeaders()
                .get("Set-Cookie");
        if (newCookies != null) {

            for (String cookie : newCookies) {
                int equalIndex = cookie.indexOf('=');
                int semicolonIndex = cookie.indexOf(';');

                String cookieKey = cookie.substring(0, equalIndex);
                String cookieValue = cookie.substring(equalIndex + 1,
                        semicolonIndex);

                cookies.put(cookieKey, cookieValue);
            }
        }
    }

    public String getCookieString() {

        StringBuilder sb = new StringBuilder();

        if (!cookies.isEmpty()) {

            Set<Entry<String, String>> cookieEntries = cookies.entrySet();
            for (Entry<String, String> entry : cookieEntries) {
                sb.append(entry.getKey()).append("=").append(entry.getValue())
                        .append(";");
            }
        }

        return sb.toString();
    }

    // added per KM01754222
    public void getQCSession() {
        String qcsessionurl = this.buildUrl("rest/site-session");
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        try {
            Response resp = this.httpPost(qcsessionurl, null, requestHeaders);
            this.updateCookies(resp);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

}
