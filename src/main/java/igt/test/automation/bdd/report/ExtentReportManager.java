
package igt.test.automation.bdd.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ExtentReportManager {

    /** Extent reports instance variable. */
    private static ExtentReports extent;

    private ExtentReportManager() {

    }

    public static synchronized ExtentReports getReporterInstance() {
        if (extent == null) {
            DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String fileName = sdf.format(date).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_");

            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(new File(SystemUtils.getUserDir() + File.separator
                    + "target" + File.separator + "extent-report" + File.separator + fileName + ".html"));
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setEncoding("utf-8");
            htmlReporter.config().setDocumentTitle("Test Automation results");
            htmlReporter.config().setReportName("Cucumber report");
            extent=new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Host Name", SystemUtils.getHostName());
        }
        return extent;

    }

}
