
package igt.test.automation.report;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 *class which handles the ExtentTest methods like startTest(), endTest() and getTest().
 * 
 * @author Sujit
 */
public final class ReportTestManager {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager.getLogger(ReportTestManager.class);

    private ReportTestManager() {
        super();
    }

    /**
     * reportTestMap - holds the information of thread ids and ExtentTest
     * instances.
     */
    protected static Map<Integer, ExtentTest> reportTestMap = new HashMap<>();

    /** instance of extent report is created. */
    protected static ExtentReports extent = ReportManager.getInstance();

    /**
     * At getTest() method, return ExtentTest instance in reportTestMap by using
     * current thread id.
     */
    public static synchronized ExtentTest getTest() {
        LOG.debug("I am inside the getTest() method - RTM"); 
        return  reportTestMap
                .get((int) (Thread.currentThread().getId()));
    }

    /**
     * At endTest() method, test ends and ExtentTest instance got from
     * reportTestMap via current thread id.
     */
    public static synchronized void endTest() {
        LOG.debug("I am inside the endTest() method - RTM");
        extent.endTest((ExtentTest) reportTestMap
                .get((int) (Thread.currentThread().getId())));
    }

    /**
     * At startTest() method, an instance of ExtentTest created and put into
     * reportTestMap with current thread id.
     */
    public static synchronized ExtentTest startTest(final String testName,
            final String desc) {
        LOG.debug("I am inside the startTest() method - RTM");
        ExtentTest test = extent.startTest(testName, desc);
        reportTestMap.put((int) (Thread.currentThread().getId()), test);
        return test;
    }
}
