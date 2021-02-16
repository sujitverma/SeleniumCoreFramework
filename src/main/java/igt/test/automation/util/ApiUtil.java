

package igt.test.automation.util;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Utility to handle the RESTful API.
 * 
 * @author Sujit
 */

public final class ApiUtil {

    /** private constructor. */
    private ApiUtil() {

    }

    /**
     * Perform a POST request to the <code>url</code> with the
     * <code>headers</code>.
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param inputJson
     *            - Input JSON you want to post
     * @return The {@link Response} of the request.
     * 
     * @author Sujit
     */
    public static Response postRequest(final String url,
            final Map<String, String> headers, final String inputJson) {
        return given().headers(headers).body(inputJson).post(url).then()
                .extract().response();
    }

    /**
     * Perform a POST request to the <code>url</code> with the
     * <code>headers</code> and with <code>cookies</code>.
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param cookies
     *            - cookies object that you want to send it across to keep
     *            session live
     * @param inputJson
     *            - Input JSON you want to post
     * @return The response of the request.
     * 
     * @author 
     */
    public static Response postRequest(final String url,
            final Map<String, String> headers,
            final Map<String, String> cookies, final String inputJson) {
        return given().cookies(cookies).headers(headers).body(inputJson)
                .post(url).then().extract().response();
    }

    /**
     * Perform a POST request to the <code>url</code> with <code>headers</code>,
     * <code>file</code> and <code>params</code>.
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param file
     *            - file object that you want to send it across
     * @param params
     *            - parameter objects that you want to send it across
     * @return - the response of the request.
     * 
     * @author 
     */
    public static Response postRequest(final String url,
            final Map<String, String> headers, final String file,
            final Map<String, String> params) {

        return given().headers(headers).multiPart(new File(file)).params(params)
                .post(url).then().extract().response();

    }

    /**
     * Perform a GET request to the <code>url</code> with the
     * <code>headers</code>.
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param inputJson
     *            - Input JSON you want to get
     * @return The {@link Response} of the request.
     * 
     * @author Sujit
     */
    public static Response getRequest(final String url,
            final Map<String, String> headers, final String inputJson) {
        return given().headers(headers).body(inputJson).get(url).then()
                .extract().response();
    }

    /**
     * Perform a GET request to the <code>url</code> with the
     * <code>headers</code> and <code>cookies</code>.
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param cookies
     *            - cookies object that you want to send it across to keep
     *            session live
     * @return The response of the request.
     * 
     * @author 
     */
    public static Response getRequest(final String url,
            final Map<String, String> headers,
            final Map<String, String> cookies) {
        return given().cookies(cookies).headers(headers).get(url).then()
                .extract().response();
    }

    /**
     * Perform a DELETE request to the <code>url</code> with the
     * <code>headers.</code>
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param inputJson
     *            - Input JSON you want to delete
     * @return The {@link Response} of the request.
     * 
     * @author Sujit
     */
    public static Response deleteRequest(final String url,
            final Map<String, String> headers, final String inputJson) {
        return given().headers(headers).body(inputJson).delete(url).then()
                .extract().response();
    }

    /**
     * Perform a PUT request to the <code>url</code> with the
     * <code>headers.</code>
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param inputJson
     *            - Input JSON you want to put
     * @return The {@link Response} of the request.
     * 
     * @author Sujit
     */
    public static Response putRequest(final String url,
            final Map<String, String> headers, final String inputJson) {
        return given().headers(headers).body(inputJson).put(url).then()
                .extract().response();
    }

    /**
     * Perform a PUT request to the <code>url</code> with <code>headers</code>
     * <code>file</code> and <code>params</code>.
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param file
     *            - file object that you want to send it across
     * @param params
     *            - parameter objects that you want to send it across
     * @return - the response of the request.
     * 
     * @author 
     */
    public static Response putRequest(final String url,
            final Map<String, String> headers, final String file,
            final Map<String, String> params) {

        return given().headers(headers).multiPart(new File(file)).params(params)
                .put(url).then().extract().response();

    }

    /**
     * Perform a PUT request to the <code>url</code> with the
     * <code>headers</code> and <code>cookies</code>.
     * 
     * @param url
     *            - the REST api that you want to access
     * @param headers
     *            - headers object that you want to send it across
     * @param cookies
     *            - cookies object that you want to send it across
     * @param inputJson
     *            - Input JSON you want to put
     * @return The response of the request.
     * 
     * @author 
     */
    public static Response putRequest(final String url,
            final Map<String, String> headers,
            final Map<String, String> cookies, final String inputJson) {
        return given().cookies(cookies).headers(headers).body(inputJson)
                .put(url).then().extract().response();
    }

    /**
     * Get the status code of the response.
     *
     * @param response
     *            the Response body
     * @return {@link int} The status code of the response.
     */
    public static int getStatusCode(final Response response) {
        return response.getStatusCode();
    }

    /**
     * Returns the response body.
     * 
     * @param response
     *            the Response body
     *
     * @return The {@link Response} response body.
     */
    public static ResponseBody<?> getResponseBody(final Response response) {
        return response.getBody();
    }

    /**
     * Get the status line of the response.
     * 
     * @param response
     *            the Response body
     * @return {@link String} status line of the response
     */
    public static String getStatusLine(final Response response) {
        return response.getStatusLine();
    }

    /**
     * Method to assert the status code from the response.
     * 
     * @param statusCode
     *            is the actual status code
     * @param response
     *            the Response body
     */
    public static void assertStatusCode(final int statusCode,
            final Response response) {
        assertThat(statusCode, equalTo(response.getStatusCode()));
    }

    /**
     * Method to assert the status line from the response.
     * 
     * @param statusLine
     *            is the actual status line
     * @param response
     *            the Response body
     */
    public static void assertStatusLine(final String statusLine,
            final Response response) {
        assertThat(statusLine, equalTo(response.getStatusLine()));
    }

    /**
     * Perform a POST request to the <code>url</code> with the <code>headers</code>.
     * Utilizes relaxedHttpsValidation for SSL host
     * @param url       - the REST api that you want to access
     * @param headers   - headers object that you want to send it across
     * @param bodyText - Input string you want to post
     * @return The {@link Response} of the request.
     * @author 
     *
     */
    public static Response postDataFromUrlRelaxHttps(final String url, final Map<String, String> headers,
                                                     final String bodyText) {
        return given().relaxedHTTPSValidation().headers(headers).and().body(bodyText).when().post(url).then().extract()
            .response();
    }

    /**
     * Function to convert credential i.e. <code>username</code> <code>password</code> into base64 string.
     *
     * @author 
     * @param username username value for encoding into base64
     * @param password  password value for encoding into base64
     * @return Base 64 String of username and password which will use to hit API.
     */
    public static String encodeCredentialsToBase64(final String username, final String password) {
        String encodedCredentials = null;
        encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        return encodedCredentials;
    }



}
