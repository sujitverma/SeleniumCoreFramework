
package igt.test.automation.selenium.constants;

import java.io.File;

/**
 * interface which provides the global constants to be used in the pse test
 * automation framework.
 * 
 * @author 
 *
 */
public interface IGlobalConstants {

    /** Constant Windows. */
    String WINDOWS_OS = "Windows";

    /** Constant Linux. */
    String LINUX_OS = "Linux";

    /** represents //src//main//resources// directory.*/
    String GLOBAL_MAIN_RESOURCES_FOLDER = File.separator + "src"
            + File.separator + "main" + File.separator + "resources"
            + File.separator;

    /** represents //src//test//resources// directory. */
    String GLOBAL_TEST_RESOURCES_FOLDER = File.separator + "src"
            + File.separator + "test" + File.separator + "resources"
            + File.separator;

    /** represents //src//test//resources//data// directory. */
    String GLOBAL_DATA_FOLDER = GLOBAL_TEST_RESOURCES_FOLDER + File.separator
            + "data" + File.separator;

    /** represents //src//test//resources//screenshots// directory. */
    String GLOBAL_SCREENSHOTS_FOLDER = GLOBAL_TEST_RESOURCES_FOLDER
            + File.separator + "screenshots" + File.separator;
    
    /** represents //target//screenshots directory. */
    String SCREENSHOTS_FOLDER = File.separator + "target" + File.separator + "screenshots"+ File.separator;

    /** represents //target//extent-report directory. */
    String REPORT_FILE_PATH = File.separator + "target" + File.separator + "extent-report"+ File.separator;

    /** represents //src//test//resources//data//environment.properties. */
    String ENVIRONMENT_PROPERTIES_PATH = GLOBAL_TEST_RESOURCES_FOLDER + "data"
            + File.separator + "environment.properties";

    /** represents //src//test//resources//data//jdbc.properties. */
    String JDBC_CONFIG_PROPERTIES_PATH = GLOBAL_TEST_RESOURCES_FOLDER +"data"
            + File.separator +"jdbc.properties";

    /** represents //src//main//resources//applicationContext.xml. */
    String APPLICATION_CONTEXT_XML_PATH = GLOBAL_MAIN_RESOURCES_FOLDER +"applicationContext.xml";
    
    /** represents //src//test//resources//data//ios_capabilities.properties . */
    String IOS_CAPABILITY_PROPERTIES_PATH = GLOBAL_DATA_FOLDER + File.separator
            + "ios_capabilities.properties";

    /** //src//test//resources//data//android_capabilities.properties. */
    String ANDROID_CAPABILITY_PROPERTIES_PATH = GLOBAL_DATA_FOLDER
            + File.separator + "android_capabilities.properties";

    /** //src//test//resources//data//ipad_capabilities.properties. */
    String IPAD_CAPABILITY_PROPERTIES_PATH = GLOBAL_DATA_FOLDER + File.separator
            + "ipad_capabilities.properties";

    /** //src//test//resources//drivers//. */
    String GLOBAL_DRIVERS_FOLDER = GLOBAL_TEST_RESOURCES_FOLDER + "drivers"
            + File.separator;

    /** //src//test//resources//drivers//geckodriver.exe. */
    String GECKO_DRIVER_PATH = GLOBAL_DRIVERS_FOLDER + File.separator
            + "geckodriver.exe";

    /** //src//test//resources//drivers//linux//geckodriver. */
    String GECKO_DRIVER_LINUX_PATH = GLOBAL_DRIVERS_FOLDER + File.separator
            + LINUX_OS + File.separator + "geckodriver";

    /** //src//test//resources//drivers//chromedriver.exe. */
    String CHROME_DRIVER_PATH = GLOBAL_DRIVERS_FOLDER + File.separator
            + "chromedriver.exe";

    /** //src//test//resources//drivers//linux//chromedriver. */
    String CHROME_LINUX_DRIVER_PATH = GLOBAL_DRIVERS_FOLDER + File.separator
            + LINUX_OS + File.separator + "chromedriver";

    /** //src//test//resources//drivers//IEDriverServer.exe. */
    String IE_DRIVER_PATH = GLOBAL_DRIVERS_FOLDER + File.separator
            + "IEDriverServer.exe";

    /** //src//test//resources//drivers//MicrosoftWebDriver.exe. */
    String EDGE_DRIVER_PATH = GLOBAL_DRIVERS_FOLDER + File.separator
            + "MicrosoftWebDriver.exe";

    /** //src//test//resources//drivers//operadriver.exe. */
    String OPERA_DRIVER_PATH = GLOBAL_DRIVERS_FOLDER + File.separator
            + "operadriver.exe";

    /** //src//test//resources//drivers//linux//opera driver. */
    String OPERA_LINUX_DRIVER_PATH = GLOBAL_DRIVERS_FOLDER + File.separator
            + LINUX_OS + File.separator + "operadriver";

}
