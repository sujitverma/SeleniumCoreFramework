
package igt.test.automation.bdd.base;

import com.aventstack.extentreports.ExtentTest;
import gherkin.AstBuilder;
import gherkin.Parser;
import gherkin.ast.Feature;
import gherkin.ast.GherkinDocument;
import igt.test.automation.bdd.report.ExtentReportTestManager;
import igt.test.automation.customexceptions.InvalidBrowserException;
import igt.test.automation.driver.DesktopOptions;
import igt.test.automation.driver.DeviceDriverOptions;
import igt.test.automation.driver.DriverFactory;
import igt.test.automation.driver.DriverOptions;
import igt.test.automation.selenium.constants.IGlobalConstants;
import igt.test.automation.util.DataUtil;
import igt.test.automation.util.SeleniumUtil;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.ITestContext;
import org.testng.Reporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * BaseStepDefinition helps to initialise the objects required to control the idealised
 *  * browser, also to handle test report and logs, taking screenshots, etc.
 */
public class BaseStepDefinition {

    /** TestBase instance. */
    private TestBase testBase;

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
     * author instance.
     */
    private String author;

    /** Spring config for loading the env.properties. */
    private static final String ENVIRONMENT_CONTEXT_XML = "environmentContext.xml";

    /** browserType. */
    private static final String BROWSER_TYPE = "browserType";
    /** browserVersion. */
    private static final String BROWSER_VERSION = "browserVersion";
    /** platform. */
    private static final String PLATFORM = "platform";
    /** deviceName. */
    private static final String DEVICE_NAME = "deviceName";
    /** user.dir. */
    private static final String USER_DIR = "user.dir";
    /** isGridEnabled. */
    private static final String GRID_ENABLED_FLAG = "isGridEnabled";
    /** specificBrowserDriverEnabled. */
    private static final String SPECIFIC_BROWSER_VERSION_ENABLED_FLAG = "specificBrowserDriverEnabled";
    /** specificDeviceDriverEnabled. */
    private static final String SPECIFIC_DEVICE_VERSION_ENABLED_FLAG = "specificDeviceDriverEnabled";
    /** gridHubUrl. */
    private static final String GRID_HUB_URL = "gridHubUrl";
    /** appiumServerUrl. */
    private static final String APPIUM_SERVER_URL = "appiumServerUrl";
    /** isDeviceBrowserEnabled. */
    private static final String IS_DEVICE_BROWSER_ENABLED = "isDeviceBrowserEnabled";
    /** app. */
    private static final String APP_PATH = "app";
    /** udid. */
    private static final String UDID_DEVICE = "udid";
    /** platformVersion. */
    private static final String PLATFORM_VERSION = "platformVersion";
    /** browserDeviceType. */
    private static final String BROWSER_DEVICE_TYPE = "browserDeviceType";
    /** .properties. */
    private static final String PROPERTIES = ".properties";

    /** LOGGER. */
    private static final Logger LOG = LogManager
        .getLogger(BaseStepDefinition.class);

    /** Parser Object. */
    private static final Parser<GherkinDocument> GHERKIN_PARSER = new Parser<>(
        new AstBuilder());


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

    public BaseStepDefinition(final TestBase tBase) {
        testBase = tBase;
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
     * Gets the deviceName field.
     *
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Gets the appPath field. * * @return the appPath
     *
     * @return String
     */
    public String getAppPath() {
        return appPath;
    }

    /**
     * Gets the udid field.
     *
     * @return the udid
     */
    public String getUdid() {
        return udid;
    }

    /**
     * Gets the platformVersion field.
     *
     * @return the platformVersion
     */

    public String getPlatformVersion() {
        return platformVersion;
    }

    /**
     * Gets the browserDeviceType field.
     *
     * @return the browserDeviceType
     */

    public String getBrowserDeviceType() {
        return browserDeviceType;
    }

    /**
     * Gets the appiumServerUrl field.
     *
     * @return appiumServerUrl.
     */

    public String getAppiumServerUrl() {
        return appiumServerUrl;
    }

    /** * Gets the author field.
     *
     * @return AUTHOR
     * */

    public String getAuthor() {
        return author;
    }

    /**
     * Gets the isDeviceBrowserEnabled field.
     * @return isDeviceBrowserEnabled
     */

    public boolean isDeviceBrowserEnabled() {
        return isDeviceBrowserEnabled;
    }


    @Before(order = 0)
    public void setUpParams() throws InvalidBrowserException {
        // 1. Read the environment.properties

        // 2. Read the env parameters from testng.xml
        Map<String, String> testParameters = readTestParameters(
                Reporter.getCurrentTestResult().getTestContext());

        // 3. Return a environmentMap
        // loading the environment.properties by loading the
        // environmentContext.xml through Spring
        new ClassPathXmlApplicationContext(ENVIRONMENT_CONTEXT_XML);
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

        browserDeviceType = testParameters.get(BROWSER_DEVICE_TYPE) != null
                ? testParameters.get(BROWSER_DEVICE_TYPE)
                : System.getProperty(BROWSER_DEVICE_TYPE);

        appiumServerUrl = testParameters.get(APPIUM_SERVER_URL) != null
                ? testParameters.get(APPIUM_SERVER_URL)
                : System.getProperty(APPIUM_SERVER_URL);

        isDeviceBrowserEnabled = Boolean
                .parseBoolean(System.getProperty(IS_DEVICE_BROWSER_ENABLED));

        author = testParameters.get(getAuthor()) != null
                ? testParameters.get(getAuthor())
                : System.getProperty(getAuthor());

        // get the driver instance from the aero.igt.driver. DriverFactory if
        // its not Null.
        if (StringUtils.isNotEmpty(browserType)) {
            DriverOptions browserOptions = new DriverOptions(getBrowserType(),
                    getBrowserVersion(), getPlatformName(), isGridEnabled(),
                    getGridHubUrl(), getCapabilities(getBrowserDeviceType()),
                    isSpecificBrowserDriverEnabled(), getBrowserDeviceType(),
                    isDeviceBrowserEnabled());

            DeviceDriverOptions deviceOptions = new DeviceDriverOptions(
                    getDeviceName(), getAppPath(), getUdid(),
                    getPlatformVersion(),
                    getCapabilities(getBrowserDeviceType()),
                    isSpecificDeviceDriverEnabled(), getAppiumServerUrl());

            //create desktop capabilities object
            DesktopOptions desktopOptions = new DesktopOptions(getCapabilities(getBrowserDeviceType()));

            testBase.driver = DriverFactory.getDriver(browserOptions,
                    deviceOptions, desktopOptions);
            // don't maximise if its a IOS or Android
            if (!(platformName.equalsIgnoreCase(Platform.IOS.toString())
                    || platformName
                            .equalsIgnoreCase(Platform.ANDROID.toString()))) {
                testBase.driver.manage().window().maximize();
            }

        }

    }

    private Map<String, String> readTestParameters(
            final ITestContext testContext) {
        return testContext.getCurrentXmlTest().getLocalParameters();
    }

    /**
     * a helper method to read the values from the .properties file and return
     * Capabilities. For e.g. chrome.properties would return you the desired
     * capabilities.
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

        } else if ("desktop".equalsIgnoreCase(browserDeviceType)) {
            final String windows = "windows";
            properties = DataUtil.getPropertyName(SystemUtils.getUserDir()
                                                      + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                      + windows + PROPERTIES);
            dataList = DataUtil.readPropertyFileKey(SystemUtils.getUserDir()
                                                        + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER
                                                        + windows + PROPERTIES);
        }
        assert dataList != null;
        for (String propertyKey : dataList) {
            String propertyValue = properties.getProperty(propertyKey);
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
        }

        return capabilities;
    }

    /** threadLocal instance. */
    private static ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<>();

    @Before(order = 1)
    public void setUpBDD(final Scenario scenario) {

        String tagsName = String.join(",", scenario.getSourceTagNames());
        Feature feature = null;
        try {
            String[] str = scenario.getId().split(":");

            feature = GHERKIN_PARSER
                    .parse(new FileReader(
                            SystemUtils.getUserDir() + File.separator + str[1]))
                    .getFeature();

        } catch (FileNotFoundException e) {
            LOG.error("File {0} not found ", e);
        }
        Optional<Feature> optional = Optional.ofNullable(feature);
        String[] strArray = scenario.getId().split("/");
        String featureName = strArray[strArray.length - 1];
        String[] fArray = featureName.split(":");
        if (optional.isPresent()) {
            testBase.extentFeature = ExtentReportTestManager
                    .startTest(fArray[0], optional.get().getDescription());
        } else {
            testBase.extentFeature = ExtentReportTestManager
                    .startTest(fArray[0], "Feature file execution started");
        }

        testBase.extentFeature.assignCategory(tagsName);
        testBase.extentFeature.assignAuthor(author);
        testBase.extentScenario = ExtentReportTestManager
                .startScenario(scenario.getName());
        threadLocal.set(testBase.extentScenario);

    }
    
    /**
     *
     *Method to tear up the the scenario. 
     *
     * @param scenario is {@link Scenario}
     */
    @After public void tearDown(final Scenario scenario) {
        
        if (scenario.isFailed()) {
            // Enbeded the screenshot to the cucumber-pretty reports which are
            // generated by
            // making configs to the Runner file
            SeleniumUtil.makeScreenshot(testBase.driver, scenario);
            threadLocal.get().fail("Scenario failed");

        }

        if (this.testBase.driver != null) {
            this.testBase.driver.quit();
        }
    }
}
