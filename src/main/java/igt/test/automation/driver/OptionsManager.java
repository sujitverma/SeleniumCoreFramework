
package igt.test.automation.driver;

import java.io.File;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;

/**
 * Class to handle driver options and Desired capabilities.
 *
 * @author 
 *
 */
public final class OptionsManager {

    /**
     * making the class as singleton by making the constructor as private.
     * 
     */
    private OptionsManager() {

    }

    /** headless. */
    private static final String HEADLESS = "--headless";

    /** window size. */
    private static final String WINDOW_SIZE_FORMAT = "window-size=1200x600";

    /**
     * Opera local app folder.
     * 
     */
    private static final String OPERA_LOCAL_APP_BINARY_PATH = System
            .getenv("LOCALAPPDATA") + File.separator + "Programs"
            + File.separator + "Opera" + File.separator;

    /** OPERA_EXE. */
    private static final String OPERA_EXE = "opera.exe";

    /**
     *
     * Method to set the chrome options.
     * 
     * @param headless
     *            is the boolean variable
     * @param  chromeCapability - the desired capability you want to pass before launching the chromedriver
     * @return ChromeOptions
     */
    public static ChromeOptions getChromeOptions(final boolean headless, final Capabilities chromeCapability) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments(HEADLESS);
            options.addArguments(WINDOW_SIZE_FORMAT);
            options.merge(chromeCapability);
        }
        return options;
    }

    /**
     *
     * Method to set the firefox options.
     * 
     * @param headless
     *            is the boolean variable
     * @param  firefoxCapability - the desired capability you want to pass before launching the gecko driver
     * @return FirefoxOptions
     */
    public static FirefoxOptions getFirefoxOptions(final boolean headless, final Capabilities firefoxCapability) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments(HEADLESS);
            options.addArguments(WINDOW_SIZE_FORMAT);
            options.merge(firefoxCapability);
        }
        return options;
    }

    /**
     * Method to set the opera options.
     * 
     * @param browserVersion
     *            is the opera browser version
     * @param  operaCapability - the desired capability you want to pass before launching the opera driver
     * @return OperaOptions
     */
    public static OperaOptions getOperaOptions(final String browserVersion, final Capabilities operaCapability) {
        OperaOptions options = new OperaOptions();
        options.setBinary(OPERA_LOCAL_APP_BINARY_PATH + browserVersion
                + File.separator + OPERA_EXE);
        options.merge(operaCapability);
        return options;

    }

    /**
     * Method to set the InternetExplorer options.
     * 
     * @param  ieCapability - the desired capability you want to pass before launching the ie driver
     * @return InternetExplorerOptions
     */
    public static InternetExplorerOptions getInternetExplorerOptions(final Capabilities ieCapability) {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        options.setCapability(
                InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                true);
        options.merge(ieCapability);
        return options;

    }
    
    /**
     * Method to set the InternetExplorer options.
     * 
     * @param  edgeCapability - the desired capability you want to pass before launching the edge driver
     * @return InternetExplorerOptions
     */
    public static EdgeOptions getEdgeOptions(final Capabilities edgeCapability) {
        EdgeOptions options = new EdgeOptions();
        options.merge(edgeCapability);
        return options;

    }

}
