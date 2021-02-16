

package igt.test.automation.base;

import igt.test.automation.customexceptions.InvalidBrowserException;
import igt.test.automation.driver.*;
import igt.test.automation.report.ReportManager;
import igt.test.automation.selenium.constants.IGlobalConstants;
import igt.test.automation.util.DataUtil;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * BaseTest helps to initialise the objects required to control the idealised
 * browser, also to handle test report and logs, taking screenshots, etc.
 *
 * @author Sujit
 */
@Listeners(igt.test.automation.listeners.TestListeners.class)
public abstract class TestBase extends TestNGDriver {

    /**
     * The extent report instance.
     */
    private ExtentReports report = ReportManager.getInstance();

    /**
     * driver instance.
     */
    private WebDriver driver;

    /**
     * browser Type.
     */
    private String browserType;

    /**
     * platform.
     */
    private String platformName;

    /**
     * browser version.
     */
    private String browserVersion;

    /**
     * device Name.
     */
    private String deviceName;

    /**
     * absolute path to the mobile application.
     */
    private String appPath;

    /**
     * Unique device identifier of the connected physical device.
     */
    private String udid;

    /**
     * Mobile OS version.
     */
    private String platformVersion;

    /**
     * device or browser choice.
     */
    private String browserDeviceType;

    /**
     * appium server url.
     */
    private String appiumServerUrl;

    /**
     * isDeviceBrowserEnabled.
     */
    private boolean isDeviceBrowserEnabled;

    /**
     * environmentType.
     */
    private String environmentType;

    /**
     * CONSTANTS.
     */

    private static final String ENVIRONMENT_TYPE = "environmentType";

    private static final String BROWSER_TYPE = "browserType";

    private static final String BROWSER_VERSION = "browserVersion";

    private static final String DEVICE_NAME = "deviceName";

    private static final String PLATFORM = "platform";

    private static final String USER_DIR = "user.dir";

    private static final String GRID_ENABLED_FLAG = "isGridEnabled";

    private static final String SPECIFIC_BROWSER_VERSION_ENABLED_FLAG = "specificBrowserDriverEnabled";

    private static final String SPECIFIC_DEVICE_VERSION_ENABLED_FLAG = "specificDeviceDriverEnabled";

    private static final String GRID_HUB_URL = "gridHubUrl";

    private static final String APPIUM_SERVER_URL = "appiumServerUrl";

    private static final String IS_DEVICE_BROWSER_ENABLED = "isDeviceBrowserEnabled";

    private static final String APP_PATH = "app";

    private static final String UDID_DEVICE = "udid";

    private static final String PLATFORM_VERSION = "platformVersion";

    private static final String BROWSER_DEVICE_TYPE = "browserDeviceType";

    private static final String IO_EXCEPTION = "IOException";

    private static final String TEST_FAILURE_IOE = "Test failed due to IOException";

    private static final String PROPERTIES = ".properties";

    private static final String SCREENSHOTS = "./target/screenshots/";

    private static final String SCREENCAPTUREPATH = "../../target/screenshots/";

    private static final String PNG = ".png";

    private static final String PARAMETER = ": {0}";

    private static final String DESKTOP = "desktop";

    /**
     * environmentContext.xml which loads the environment.properties from
     * /src/main/resources/ folder.
     */
    private static final String ENVIRONMENT_CONTEXT_XML = "environmentContext.xml";

    /**
     * The Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(TestBase.class);

    protected String getUserDir() {
        return USER_DIR;
    }

    public String getBrowserType() {
        return browserType;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public String getPlatformName() {
        return platformName;
    }

    /**
     * TestBase before method which reads all the properties file and also gives
     * the flavour of Browser requested as per the testng.xml or
     * environment.properties file.
     *
     */
    @SuppressWarnings("resource")
    @BeforeTest(alwaysRun = true)
    public void testBaseBefore(final ITestContext testContext)
        throws InvalidBrowserException {
        // loading the environment.properties by loading the
        // environmentContext.xml through Spring
        new ClassPathXmlApplicationContext(ENVIRONMENT_CONTEXT_XML);
        //this would load the specific environment data into properties.

        Map<String, String> testParameters = readTestParameters(testContext);

        setReport(ReportManager.getInstance());
        // if test specific browser is mentioned from testng.xml use it
        // otherwise use from environment.properties
        browserType = testParameters.get(BROWSER_TYPE) != null
            ? testParameters.get(BROWSER_TYPE)
            : System.getProperty(BROWSER_TYPE);
        // if test specific platform is mentioned from testng.xml use it
        // otherwise use from environment.properties
        platformName = testParameters.get(PLATFORM) != null
            ? testParameters.get(PLATFORM)
            : System.getProperty(PLATFORM);
        // if test specific platform is mentioned from testng.xml use it
        // otherwise use from environment.properties
        browserVersion = testParameters.get(BROWSER_VERSION) != null
            ? testParameters.get(BROWSER_VERSION)
            : System.getProperty(BROWSER_VERSION);

        // ** The kind of mobile device or emulator to use. *//*
        deviceName = testParameters.get(DEVICE_NAME) != null
            ? testParameters.get(DEVICE_NAME)
            : System.getProperty(DEVICE_NAME);

        // ** absolute path of the app file. *//*
        appPath = testParameters.get(APP_PATH) != null
            ? testParameters.get(APP_PATH)
            : System.getProperty(APP_PATH);

        // ** Unique device identifier of the connected physical device.. *//*
        udid = testParameters.get(UDID_DEVICE) != null
            ? testParameters.get(UDID_DEVICE)
            : System.getProperty(UDID_DEVICE);

        // ** Mobile OS version. *//*
        platformVersion = testParameters.get(PLATFORM_VERSION) != null
            ? testParameters.get(PLATFORM_VERSION)
            : System.getProperty(PLATFORM_VERSION);

        //** browser, device or desktop choice. */
        browserDeviceType = testParameters.get(BROWSER_DEVICE_TYPE) != null
            ? testParameters.get(BROWSER_DEVICE_TYPE)
            : System.getProperty(BROWSER_DEVICE_TYPE);

        appiumServerUrl = testParameters.get(APPIUM_SERVER_URL) != null
            ? testParameters.get(APPIUM_SERVER_URL)
            : System.getProperty(APPIUM_SERVER_URL);

        // if test specific browser is mentioned from testng.xml use it
        // otherwise use from environment.properties
        environmentType = testParameters.get(ENVIRONMENT_TYPE) != null
            ? testParameters.get(ENVIRONMENT_TYPE)
            : System.getProperty(ENVIRONMENT_TYPE);

        isDeviceBrowserEnabled = Boolean.parseBoolean(System.getProperty(IS_DEVICE_BROWSER_ENABLED));

        //read the environment.properties and fetch the parameter 'environmentType' and load the associated
        // .properties file. E.g. devEnvironment.properties and set it as System properties.
        setEnvironmentTypeData();


        // get the driver instance from the aero.igt.driver. DriverFactory if its not Null.
        if (StringUtils.isNotEmpty(browserType)) {
            //create browser capabilities object
            DriverOptions browserOptions = new DriverOptions(browserType,
                                                             browserVersion, platformName, isGridEnabled(), getGridHubUrl(),
                                                             getCapabilities(browserDeviceType),
                                                             isSpecificBrowserDriverEnabled(), browserDeviceType, isDeviceBrowserEnabled);
            //create device capabilities object
            DeviceDriverOptions deviceOptions = new DeviceDriverOptions(
                getDeviceName(), getAppPath(), getUdid(), getPlatformVersion(),
                getCapabilities(browserDeviceType), isSpecificDeviceDriverEnabled(), getAppiumServerUrl());
            //create desktop capabilities object
            DesktopOptions desktopOptions = new DesktopOptions(getCapabilities(browserDeviceType));

            driver = DriverFactory.getDriver(browserOptions, deviceOptions, desktopOptions);
            // don't maximise if its a IOS or Android or desktop application
            if (!(platformName.equalsIgnoreCase(Platform.IOS.toString())
                || platformName.equalsIgnoreCase(Platform.ANDROID.toString())
                || DESKTOP.equalsIgnoreCase(browserDeviceType))) {
                driver.manage().window().maximize();
            }

        }

    }

    /**
     * returns the driver field read from testng.xml if present otherwise from
     * enviornment.properties.
     *
     * @return the driver
     */

    public WebDriver getDriver() {
        return this.driver;
    }

    private Map<String, String> readTestParameters(
        final ITestContext testContext) {
        return testContext.getCurrentXmlTest().getLocalParameters();
    }

    /**
     * is used to determine whether the Grid mode is enabled or not by values
     * configured in environment.properties file.
     *
     * @return {@code boolean}
     */
    public boolean isGridEnabled() {
        return Boolean.parseBoolean(System.getProperty(GRID_ENABLED_FLAG));
    }

    /**
     * is used to determine whether a specific driver browser version flag is
     * enabled or not by values configured in environment.properties file.
     *
     * @return {@code boolean}
     */
    public boolean isSpecificBrowserDriverEnabled() {
        return Boolean.parseBoolean(
            System.getProperty(SPECIFIC_BROWSER_VERSION_ENABLED_FLAG));
    }


    /**
     * is used to determine whether a specific device driver version flag is
     * enabled or not by values configured in environment.properties file.
     *
     * @return {@code boolean}
     */
    public boolean isSpecificDeviceDriverEnabled() {
        return Boolean.parseBoolean(
            System.getProperty(SPECIFIC_DEVICE_VERSION_ENABLED_FLAG));
    }

    /**
     * determines the gridHubUrl by values configured in environment.properties
     * file.
     *
     * @return String - gridHubUrl
     */
    public String getGridHubUrl() {
        return System.getProperty(GRID_HUB_URL);
    }

    /**
     * getter method to fetch the current report.
     *
     * @return the report
     */
    public ExtentReports getReport() {
        return report;
    }

    /**
     * setter method to set the report.
     *
     * @param report
     */
    private void setReport(final ExtentReports report) {
        this.report = report;
    }

    /**
     * Gets the deviceName field.
     *
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Gets the appPath field. * * @return the appPath
     */
    public String getAppPath() {
        return appPath;
    }

    /**
     * Gets the udid field. * * @return the udid
     */
    public String getUdid() {
        return udid;
    }

    /**
     * Gets the platformVersion field. * * @return the platformVersion
     */

    public String getPlatformVersion() {
        return platformVersion;
    }

    /**
     * Gets the browserDeviceType field. * * @return the browserDeviceType
     */

    public String getBrowserDeviceType() {
        return browserDeviceType;
    }


    /**
     * Gets the appiumServerUrl field.
     *
     * @return the appiumServerUrl.
     */

    public String getAppiumServerUrl() {
        return appiumServerUrl;
    }


    /**
     * Gets the isDeviceBrowserEnabled field. * * @return the isDeviceBrowserEnabled
     */

    public boolean isDeviceBrowserEnabled() {
        return isDeviceBrowserEnabled;
    }

    /**
     * Gets the environmentType field.
     *
     * @return the environmentType.
     */

    public String getEnvironmentType() {
        return environmentType;
    }

    /**
     * to report to both log and to the extent report simultaneously.
     *
     * @param log     - {@link Logger}
     * @param test    - {@link ExtentTest}
     * @param status  - {@link LogStatus}
     * @param message - String description of the message to log
     */
    public void reportLog(final Logger log, final ExtentTest test,
                          final LogStatus status, final Object message) {
        log.info(message);
        test.log(status, message.toString());
        if (LogStatus.FAIL == status || LogStatus.ERROR == status) {
            takeFullScreenShot(test);
        }

    }

    /**
     * takeScreenShot - this method takes the screenshots and saves under
     * "screenshots" folder under the current directory of test.
     *
     * @param test is the ExtentTest object
     * @author Sujit
     * @deprecated(since="6.2") use {@code takeFullScreenShot} method instead.
     */
    @Deprecated
    public void takeScreenShot(final ExtentTest test) {
        Date d = new Date();
        String fileName = d.toString().replace(" ", "_").replace(":", "_");


        String filePath = fileName + ".jpg";
        File scrFile = ((TakesScreenshot) getDriver())
            .getScreenshotAs(OutputType.FILE);
        try {
            new File(SCREENSHOTS).mkdir();
            FileUtils.copyFile(scrFile, new File(SCREENSHOTS + filePath));
        } catch (IOException e) {
            LOG.error(TEST_FAILURE_IOE);
            LOG.error(IO_EXCEPTION + PARAMETER, e);
        }
        test.log(LogStatus.INFO, test.addScreenCapture(filePath));
    }

    /**
     * takeFullScreenShot - this method takes the full page screenshots and
     * saves under "screenshots" folder under the current directory of test.
     *
     * @param test is the ExtentTest object
     * @author 
     **/
    public void takeFullScreenShot(final ExtentTest test) {
        Date d = new Date();
        String fileName = d.toString().replace(" ", "_").replace(":", "_");

        String filePath = fileName + PNG;
        try {
            Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(getDriver());
            new File(SCREENSHOTS).mkdir();
            ImageIO.write(screenshot.getImage(), "PNG", new File(SCREENSHOTS + filePath));
        } catch (IOException e) {
            LOG.error(TEST_FAILURE_IOE);
            LOG.error(IO_EXCEPTION + PARAMETER, e);
        }
        test.log(LogStatus.INFO, test.addScreenCapture(SCREENCAPTUREPATH + filePath));
    }

    /**
     * takeFullScreenShot - this method takes the full page screenshots and
     * saves under "screenshots" folder under the current directory of test.
     *
     * @param test   is the ExtentTest object
     * @param driver {@link WebDriver}
     * @author Mohd Jeeshan
     **/
    public void takeFullScreenShot(final ExtentTest test,
                                   final WebDriver driver) {
        Date d = new Date();
        String fileName = d.toString().replace(" ", "_").replace(":", "_");

        String filePath = fileName + PNG;
        try {
            Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(driver);
            new File(SCREENSHOTS).mkdir();
            ImageIO.write(screenshot.getImage(), "PNG", new File(SCREENSHOTS + filePath));
        } catch (IOException e) {
            LOG.error(TEST_FAILURE_IOE);
            LOG.error(IO_EXCEPTION + PARAMETER, e);
        }
        test.log(LogStatus.INFO, test.addScreenCapture(SCREENCAPTUREPATH + filePath));
    }

    /**
     * a helper method to read the values from the .properties file and return
     * Capabilities. For e.g. chrome.properties would return you the desired
     * capabilities specific for Chrome.
     *
     * @return {@code Capabilities}
     */
    private Capabilities getCapabilities(final String browserDeviceType) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Properties properties = null;
        List<String> dataList = null;
        // read the specific browserCapabilities.properties. For e.g.
        if ("browser".equalsIgnoreCase(browserDeviceType)) {
            properties = DataUtil.getPropertyName(SystemUtils.getUserDir()
                                                      + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                      + browserType.toLowerCase() + PROPERTIES);
            dataList = DataUtil.readPropertyFileKey(SystemUtils.getUserDir()
                                                        + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                        + browserType.toLowerCase() + PROPERTIES);
        } else if ("device".equalsIgnoreCase(browserDeviceType)) {
            if (platformName.equalsIgnoreCase(Platform.IOS.toString())
                || platformName
                .equalsIgnoreCase(Platform.ANDROID.toString())) {
                properties = DataUtil.getPropertyName(SystemUtils.getUserDir()
                                                          + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                          + platformName.toLowerCase() + PROPERTIES);
                dataList = DataUtil.readPropertyFileKey(SystemUtils.getUserDir()
                                                            + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                            + platformName.toLowerCase() + PROPERTIES);
            } else {
                LOG.error("device.properties not specified");
            }

        } else if (DESKTOP.equalsIgnoreCase(browserDeviceType)) {
            final String windows = "windows";
            properties = DataUtil.getPropertyName(SystemUtils.getUserDir()
                                                      + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                      + windows + PROPERTIES);
            dataList = DataUtil.readPropertyFileKey(SystemUtils.getUserDir()
                                                        + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                        + windows + PROPERTIES);
        }
        for (String propertyKey : dataList) {
            String propertyValue = null;
            if (properties != null) {
                propertyValue = properties.getProperty(propertyKey);
                if ("true".equalsIgnoreCase(propertyValue)
                    || "false".equalsIgnoreCase(propertyValue)) {
                    capabilities.setCapability(propertyKey,
                                               Boolean.valueOf(propertyValue));
                } else if (propertyValue.matches("[0-9]+")) {
                    capabilities.setCapability(propertyKey,
                                               Integer.parseInt(propertyValue));
                } else {
                    capabilities.setCapability(propertyKey, propertyValue);
                }
            } else {
                LOG.warn("Check : {}" + PROPERTIES
                             + " is populated or not", browserDeviceType);
            }
        }

        return capabilities;
    }

    /**
     * loads the .properties file specified by Parameter environmentType in environment.properties
     * and loads it to System properties.
     */
    private void setEnvironmentTypeData() {

        Properties properties = null;

        if (StringUtils.isNotBlank(environmentType)) {
            properties = DataUtil.getPropertyName(SystemUtils.getUserDir()
                                                      + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                      + environmentType + PROPERTIES);
            properties.forEach((k, v) -> System.setProperty(String.valueOf(k), String.valueOf(v)));
        }
    }

}
