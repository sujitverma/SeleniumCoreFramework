package igt.test.automation.alm.functionlibrary;

import java.util.Map;

/**
 * Class to keep the ALM Headers and cookies information which require to hit
 * its REST API.
 * 
 * @author 
 */
public class ALMCookiesHeaders {

    /** variable to keep ALM REST API headers information. */
    private Map<String, String> headers;

    /**
     * variable to keep ALM REST API cookies information which received after
     * login.
     */
    private Map<String, String> cookies;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(final Map<String, String> cookies) {
        this.cookies = cookies;
    }

}
