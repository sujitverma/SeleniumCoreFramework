

package igt.test.automation.customexceptions;

/**
 * Class to create Invalid Browser custom exception.
 * 
 * @author 
 */
public class InvalidBrowserException extends Exception {

    /** serialVersionUID. */
    private static final long serialVersionUID = -1148548317293709578L;

    /**
     * constructor to handle the InvalidBrowserException.
     * 
     * @param message
     *            to show in the exception.
     */
    public InvalidBrowserException(final String message) {

        super(message);

    }

}
