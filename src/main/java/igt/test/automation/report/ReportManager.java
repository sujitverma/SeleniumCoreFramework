
package igt.test.automation.report;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.SystemUtils;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

import igt.test.automation.selenium.constants.IGlobalConstants;

/**
 * ReportManager is to give an instance of ExtentReports. This is created as
 * Singleton such that each VM will have one instance of ExtentReport running.
 * 
 * @author 
 *
 */
public final class ReportManager {

    /** Extent reports instance variable. */
    private static ExtentReports extent;

    // to make a singleton hide the constructor
    // to ensure no other instance of ReportManager exists in a machine
    private ReportManager() {

    }

    /**
     * getInstance method returns an instance of extent report.
     * 
     * @return {@link ExtentReports}
     */
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            // dynamic report file creation
            Date d = new Date();
            String fileName = d.toString().replace(" ", "_").replace(":", "_");
            String reportFileNamePath = SystemUtils.getUserDir()
                    + IGlobalConstants.REPORT_FILE_PATH + fileName + ".html";
            // 1.creating new instance of extent report
            extent = new ExtentReports(reportFileNamePath, true,
                    DisplayOrder.NEWEST_FIRST);
            // 2. loading the config xml --> customize the html report
            extent.loadConfig(new File(SystemUtils.getUserDir()
                    + IGlobalConstants.GLOBAL_TEST_RESOURCES_FOLDER
                    + "extent-config.xml"));
            
        }
        return extent;
    }
}
