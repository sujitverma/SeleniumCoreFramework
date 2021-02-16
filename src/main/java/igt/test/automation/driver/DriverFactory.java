

package igt.test.automation.driver;

import igt.test.automation.customexceptions.InvalidBrowserException;
import igt.test.automation.selenium.constants.DriverTypeConstants;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.Architecture;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The anchor point for handling the drivers, is responsible for the desired
 * WebDriver instance. This class uses the WebDriverManager
 * {@link https://github.com/bonigarcia/webdrivermanager} to handle all the
 * Driver setup.
 *
 * @author 
 */
public final class DriverFactory {

    /**
     * Unrecognized browser.
     */
    private static final String UNRECOGNIZED_BROWSER = "Unrecognized browserName specified: ";

    /**
     * check Config.
     */
    private static final String CHECK_CONFIG = "Unrecognized browserName specified. Please check environment.properties or your testng.xml config";

    /**
     * creating threadlocal safe driver instance.
     */
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * private constructor.
     */
    private DriverFactory() {

    }

    /**
     * The Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(DriverFactory.class);

    /**
     * Returns a driver matching the parameters passed. This method is
     * thread-safe and is singleton in nature.
     *
     * @param browserSpecificOptions DriverOptions object
     * @param deviceOptions          DeviceDriverOptions object
     * @param desktopOptions         DesktopOptions object
     * @return {@link WebDriver}
     * @throws InvalidBrowserException when an invalid browser entry is specified.
     */
    public static synchronized WebDriver getDriver(
        final DriverOptions browserSpecificOptions,
        final DeviceDriverOptions deviceOptions,
        final DesktopOptions desktopOptions)
        throws InvalidBrowserException {
        String deviceName = deviceOptions.getDeviceName();

        WebDriver driver;
        if (!browserSpecificOptions.isGridEnabled()) {
            driver = intializeDriver(browserSpecificOptions, deviceOptions, desktopOptions);
            tlDriver.set(driver);

        } else {
            // code to handle Grid
            DesiredCapabilities capability = intializeCapability(browserSpecificOptions.getBrowserType(),
                                                                 browserSpecificOptions.getDesiredCapability(),
                                                                 deviceOptions.getDesiredCapability());
            capability.setCapability(CapabilityType.PLATFORM_NAME,
                                     browserSpecificOptions.getPlatformName());
            capability.setCapability(CapabilityType.BROWSER_VERSION,
                                     browserSpecificOptions.getBrowserVersion());
            capability.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            capability.setCapability(MobileCapabilityType.NO_RESET, false);
            capability.setCapability(MobileCapabilityType.APP, deviceOptions.getAppPath());
            capability.setCapability(MobileCapabilityType.UDID, deviceOptions.getUdid());
            capability.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                                     deviceOptions.getPlatformVersion());
            try {
                if (DriverTypeConstants.IPHONE.equalsIgnoreCase(deviceName)
                    || DriverTypeConstants.IPAD
                    .equalsIgnoreCase(deviceName)) {
                    capability.setCapability(
                        MobileCapabilityType.AUTOMATION_NAME,
                        AutomationName.IOS_XCUI_TEST);
                    driver = new IOSDriver<>(new URL(deviceOptions.getAppiumServerUrl()), capability);
                } else if (DriverTypeConstants.ANDROID
                    .equalsIgnoreCase(deviceName)) {
                    capability.setCapability(
                        MobileCapabilityType.AUTOMATION_NAME,
                        AutomationName.ANDROID_UIAUTOMATOR2);
                    driver = new AndroidDriver<>(new URL(deviceOptions.getAppiumServerUrl()),
                                                 capability);
                } else {
                    driver = new RemoteWebDriver(new URL(browserSpecificOptions.getGridHubUrl()),
                                                 capability);
                }
                tlDriver.set(driver);
            } catch (MalformedURLException e) {
                LOG.error(e);
            }

        }
        return tlDriver.get();
    }

    /**
     * initialises all the drivers according to the choice of the browserType
     * passed.
     *
     * @param browserSpecificOptions DriverOptions object
     * @param deviceOptions          DeviceDriverOptions object
     * @param desktopOptions         DesktopOptions object
     * @return {@link WebDriver}
     * @throws InvalidBrowserException - when the input parameter is not present then InvalidBrowserException is thrown
     */
    private static WebDriver intializeDriver(
        final DriverOptions browserSpecificOptions,
        final DeviceDriverOptions deviceOptions,
        final DesktopOptions desktopOptions)
        throws InvalidBrowserException {
        WebDriver driver = null;
        String browserType = browserSpecificOptions.getBrowserType();
        String browserDeviceType = browserSpecificOptions
            .getBrowserDeviceType();
        String browserVersion = browserSpecificOptions.getBrowserVersion();
        Boolean isSpecificBrowserDriverEnabled = browserSpecificOptions
            .getIsSpecificBrowserDriverEnabled();
        Capabilities desiredCapability = browserSpecificOptions
            .getDesiredCapability();

        if (StringUtils.isEmpty(browserSpecificOptions.getBrowserType())) {
            throw new InvalidBrowserException(
                UNRECOGNIZED_BROWSER + browserType);
        }
        if ("browser".equalsIgnoreCase(browserDeviceType)) {
            switch (browserType.toLowerCase()) {
                case DriverTypeConstants.FIREFOX:
                    return initializeFirefoxHeadless(false, desiredCapability,
                                                     browserVersion, isSpecificBrowserDriverEnabled);
                case DriverTypeConstants.FIREFOX_HEADLESS:
                    return initializeFirefoxHeadless(true, desiredCapability,
                                                     browserVersion, isSpecificBrowserDriverEnabled);
                case DriverTypeConstants.IE:
                    return intializeIE(desiredCapability, browserVersion,
                                       isSpecificBrowserDriverEnabled);
                case DriverTypeConstants.EDGE:
                    return initializeEdge(desiredCapability, browserVersion,
                                          isSpecificBrowserDriverEnabled);
                case DriverTypeConstants.CHROME:
                    return initializeChromeHeadless(false, desiredCapability,
                                                    browserVersion, isSpecificBrowserDriverEnabled);
                case DriverTypeConstants.CHROME_HEADLESS:
                    return initializeChromeHeadless(true, desiredCapability,
                                                    browserVersion, isSpecificBrowserDriverEnabled);
                case DriverTypeConstants.OPERA:
                    return initializeOpera(desiredCapability, browserVersion,
                                           isSpecificBrowserDriverEnabled);
                case DriverTypeConstants.SAFARI:
                    return new SafariDriver();
                default:
                    LOG.error(CHECK_CONFIG);
                    throw new InvalidBrowserException(
                        UNRECOGNIZED_BROWSER + browserType);
            }

        } else if ("device".equalsIgnoreCase(browserDeviceType)) {
            return initializeDevice(browserSpecificOptions, deviceOptions);
        } else if ("desktop".equalsIgnoreCase(browserDeviceType)) {
            return initializeWindowsDesktop(deviceOptions, desktopOptions);
        }

        return driver;

    }

    /**
     * helper method to handle Windows Desktop using WinAppDriver.
     *
     * @param deviceDriverOptions DeviceDriverOptions object
     * @param desktopOptions      DesktopOptions object
     * @return {@link WebDriver}
     */
    private static WebDriver initializeWindowsDesktop(
        final DeviceDriverOptions deviceDriverOptions,
        final DesktopOptions desktopOptions) {
        WebDriver driver = null;
        Capabilities desktopDesiredCapability = desktopOptions.getDesiredCapability();
        DesiredCapabilities capability = new DesiredCapabilities(desktopDesiredCapability);
        capability.setCapability(CapabilityType.PLATFORM_NAME,
                                 desktopOptions.getPlatformName());
        capability.setCapability(MobileCapabilityType.APP,
                                 deviceDriverOptions.getAppPath());
        capability.setCapability(MobileCapabilityType.DEVICE_NAME, desktopOptions.getDeviceName());
        capability.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                                 desktopOptions.getPlatformVersion());

        try {
            driver = new WindowsDriver(new URL(deviceDriverOptions.getAppiumServerUrl()),
                                       capability);
        } catch (MalformedURLException e) {
            LOG.error(e);
        }
        return driver;

    }

    /**
     * helper method to initialize iOS and android device using appium.
     *
     * @param browserSpecificOptions DriverOptions object
     * @param deviceOptions          DeviceDriverOptions object
     * @return {@link WebDriver}
     */
    private static WebDriver initializeDevice(
        final DriverOptions browserSpecificOptions,
        final DeviceDriverOptions deviceOptions) {
        WebDriver driver = null;
        String deviceName = deviceOptions.getDeviceName();
        Capabilities deviceDesiredCapability = deviceOptions
            .getDesiredCapability();
        String appiumServerUrl = deviceOptions.getAppiumServerUrl();
        String platformName = browserSpecificOptions.getPlatformName();
        boolean deviceBrowser = browserSpecificOptions.isDeviceBrowserEnabled();

        // code to handle Grid
        DesiredCapabilities capability = new DesiredCapabilities(deviceDesiredCapability);
        capability.setCapability(CapabilityType.PLATFORM_NAME,
                                 browserSpecificOptions.getPlatformName());
        capability.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capability.setCapability(MobileCapabilityType.NO_RESET, false);
        capability.setCapability(MobileCapabilityType.APP,
                                 deviceOptions.getAppPath());
        capability.setCapability(MobileCapabilityType.UDID,
                                 deviceOptions.getUdid());
        capability.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                                 deviceOptions.getPlatformVersion());
        try {
            if (platformName.equalsIgnoreCase(DriverTypeConstants.IOS)) {
                capability.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                                         AutomationName.IOS_XCUI_TEST);
                if (deviceBrowser) {
                    capability.setBrowserName(DriverTypeConstants.SAFARI);
                }
                driver = new IOSDriver<>(new URL(appiumServerUrl),
                                         capability);
            } else if (platformName
                .equalsIgnoreCase(DriverTypeConstants.ANDORID)) {
                capability.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                                         AutomationName.ANDROID_UIAUTOMATOR2);
                if (deviceBrowser) {
                    capability.setBrowserName(DriverTypeConstants.CHROME);
                }

                driver = new AndroidDriver<>(new URL(appiumServerUrl),
                                             capability);
            }

        } catch (MalformedURLException e) {
            LOG.error(e);
        }
        return driver;

    }

    /**
     * returns the drivers according to the choice of the browserType passed.
     *
     * @param browserType - name of the browser
     * @param capability  - browser desired capabilities
     * @return {@link DesiredCapabilities}
     * @throws InvalidBrowserException - when the input parameter is not present then InvalidBrowserException is thrown
     */
    private static DesiredCapabilities intializeCapability(
        final String browserType, final Capabilities capability,
        final Capabilities deviceCapability)
        throws InvalidBrowserException {
        DesiredCapabilities capabilities;
        if (StringUtils.isEmpty(browserType)) {
            throw new InvalidBrowserException(
                UNRECOGNIZED_BROWSER + browserType);
        }
        switch (browserType.toLowerCase()) {
            case DriverTypeConstants.CHROME:
                capabilities = new DesiredCapabilities(capability);
                capabilities.setBrowserName(DriverTypeConstants.CHROME);
                return capabilities;
            case DriverTypeConstants.IE:
                capabilities = new DesiredCapabilities(capability);
                capabilities.setBrowserName(DriverTypeConstants.IE);
                capabilities.setCapability("ignoreZoomSetting", true);
                capabilities.setCapability("ignoreProtectedModeSettings", true);
                return capabilities;
            case DriverTypeConstants.FIREFOX:
                capabilities = new DesiredCapabilities(capability);
                capabilities.setBrowserName(DriverTypeConstants.FIREFOX);
                return capabilities;
            case DriverTypeConstants.ANDROID:
                capabilities = new DesiredCapabilities(deviceCapability);
                capabilities.setBrowserName(DriverTypeConstants.ANDROID);
                capabilities = DesiredCapabilities.android();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
                                           MobilePlatform.ANDROID);
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                                           AutomationName.ANDROID_UIAUTOMATOR2);
                return capabilities;
            case DriverTypeConstants.EDGE:
                capabilities = new DesiredCapabilities(capability);
                capabilities.setBrowserName(DriverTypeConstants.EDGE);
                return capabilities;
            case DriverTypeConstants.IPHONE:
                capabilities = new DesiredCapabilities(deviceCapability);
                capabilities.setBrowserName(DriverTypeConstants.IPHONE);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
                                           MobilePlatform.IOS);
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                                           AutomationName.IOS_XCUI_TEST);
                return capabilities;
            case DriverTypeConstants.IPAD:
                capabilities = new DesiredCapabilities(deviceCapability);
                capabilities.setBrowserName(DriverTypeConstants.IPAD);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
                                           MobilePlatform.IOS);
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                                           AutomationName.IOS_XCUI_TEST);
                return capabilities;
            case DriverTypeConstants.SAFARI:
                capabilities = new DesiredCapabilities(capability);
                capabilities.setBrowserName(DriverTypeConstants.SAFARI);
                return capabilities;
            case DriverTypeConstants.OPERA_BLINK:
                capabilities = new DesiredCapabilities(capability);
                capabilities.setBrowserName(DriverTypeConstants.OPERA_BLINK);
                return capabilities;
            default:
                LOG.error(CHECK_CONFIG);
                throw new InvalidBrowserException(
                    UNRECOGNIZED_BROWSER + browserType);

        }
    }

    /**
     * initialises the Operadriver.
     *
     * @return {@link OperaDriver}
     */
    private static OperaDriver initializeOpera(final Capabilities operaCap,
                                               final String browserVer, final boolean specificBrowserEnabled) {
        if (!specificBrowserEnabled) {
            WebDriverManager.operadriver().setup();
        } else {
            WebDriverManager.operadriver().version(browserVer).setup();
        }
        return new OperaDriver(
            OptionsManager.getOperaOptions(browserVer, operaCap));
    }

    /**
     * initialises the Edgedriver.
     *
     * @return {@link EdgeDriver}
     */
    private static EdgeDriver initializeEdge(final Capabilities edgeCap,
                                             final String browserVer, final boolean specificBrowserEnabled) {
        if (!specificBrowserEnabled) {
            WebDriverManager.edgedriver().setup();
        } else {
            WebDriverManager.edgedriver().version(browserVer).setup();
        }
        return new EdgeDriver(OptionsManager.getEdgeOptions(edgeCap));

    }

    /**
     * initialises the InternetExplorerDriver.
     *
     * @return {@link InternetExplorerDriver}
     */
    private static InternetExplorerDriver intializeIE(final Capabilities ieCap,
                                                      final String browserVer, final boolean specificBrowserEnabled) {
        if (!specificBrowserEnabled) {
            WebDriverManager.iedriver().architecture(Architecture.X32).setup();
        } else {
            WebDriverManager.iedriver().architecture(Architecture.X32)
                .version(browserVer).setup();
        }

        return new InternetExplorerDriver(
            OptionsManager.getInternetExplorerOptions(ieCap));

    }

    /**
     * initialises the ChromeDriver.
     *
     * @return {@link ChromeDriver}
     */
    private static ChromeDriver initializeChromeHeadless(final boolean headless,
                                                         final Capabilities chromeCap, final String browserVer,
                                                         final boolean specificBrowserEnabled) {
        if (!specificBrowserEnabled) {
            WebDriverManager.chromedriver().setup();
        } else {
            WebDriverManager.chromedriver().version(browserVer).setup();
        }
        return new ChromeDriver(
            OptionsManager.getChromeOptions(headless, chromeCap));
    }

    /**
     * initialises the FirefoxDriver.
     *
     * @return {@link FirefoxDriver}
     */
    private static FirefoxDriver initializeFirefoxHeadless(
        final boolean headless, final Capabilities firefoxCapability,
        final String browserVer, final boolean specificBrowserEnabled) {
        if (!specificBrowserEnabled) {
            WebDriverManager.firefoxdriver().setup();
        } else {
            WebDriverManager.firefoxdriver().version(browserVer).setup();
        }
        return new FirefoxDriver(
            OptionsManager.getFirefoxOptions(headless, firefoxCapability));
    }

}
