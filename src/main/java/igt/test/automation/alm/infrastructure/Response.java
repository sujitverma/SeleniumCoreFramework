package igt.test.automation.alm.infrastructure;

import java.util.Map;

/**
 * This is a naive implementation of an HTTP response. We use it to simplify
 * matters in the examples. It is nothing more than a container of the response
 * headers and the response body.
 */
public class Response {

    /** responseHeaders. */
    private Map<String, ? extends Iterable<String>> responseHeaders = null;

    /** responseData. */
    private byte[] responseData = null;
    
    /** failure. */
    private Exception failure = null;

    /** statusCode 0. */
    private int statusCode = 0;

    /** 
     * Response constructor. 
     * 
     * @param responseHeaders - response headers
     * @param responseData - response data
     * @param failure - failure exception obj
     * @param statusCode - status code that you want to pass
     * 
     * */
    public Response(final Map<String, Iterable<String>> responseHeaders,
            final byte[] responseData, final Exception failure, final int statusCode) {
        super();
        this.responseHeaders = responseHeaders;
        this.responseData = responseData;
        this.failure = failure;
        this.statusCode = statusCode;
    }

    /**
     * default constructor.
     */
    public Response() {
    }

    /**
     * getting the response headers.
     * 
     * @return Map<String, ? extends Iterable<String>>
     */
    public Map<String, ? extends Iterable<String>> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * setResponseHeaders.
     * 
     * @param responseHeaders
     *            the responseHeaders to set
     */
    public void setResponseHeaders(
            final Map<String, ? extends Iterable<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    /**
     * getter method.
     * 
     * @return the responseData
     */
    public byte[] getResponseData() {
        return responseData;
    }

    /**
     * setter method.
     * 
     * @param responseData
     *            the responseData to set
     */
    public void setResponseData(final byte[] responseData) {
        this.responseData = responseData;
    }

    /**
     * accessor method getFailure.
     * 
     * @return the failure if the access to the requested URL failed, such as a
     *         404 or 500. If no such failure occured this method returns null.
     */
    public Exception getFailure() {
        return failure;
    }

    /**
     * accessor method setFailure.
     * 
     * @param failure
     *            the failure to set
     */
    public void setFailure(final Exception failure) {
        this.failure = failure;
    }

    /**
     * accessor method getStatusCode.
     * 
     * @return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * accessor method setStatusCode.
     * 
     * @param statusCode
     *            the statusCode to set
     */
    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Object method overrriden.
     * 
     * @see Object#toString() return the contents of the byte[] data as a
     *      string.
     */
    @Override public String toString() {

        return new String(this.responseData);
    }

}
