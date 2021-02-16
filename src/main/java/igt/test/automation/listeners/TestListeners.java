
package igt.test.automation.listeners;

import igt.test.automation.base.TestBase;
import igt.test.automation.report.ReportManager;
import igt.test.automation.report.ReportTestManager;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestListeners override the inbuilt testNG listeners.
 * 
 * * @author Sujit
 */
public class TestListeners implements ITestListener {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager.getLogger(TestListeners.class);

    private static String getTestMethodName(final ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    // Before starting all tests, below method runs.
    @Override public void onStart(final ITestContext iTestContext) {
        LOG.debug("I am in onStart method {}", iTestContext.getName());
    }

    // After ending all tests, below method runs.
    @Override public void onFinish(final ITestContext iTestContext) {
        LOG.debug("I am in onFinish method {}", iTestContext.getName());
        // Do tier down operations for extentreports reporting!
        ReportTestManager.endTest();
        ReportManager.getInstance().flush();
    }

    @Override public void onTestStart(final ITestResult iTestResult) {
        // Not required to override the onTestStart Method
    }

    @Override public void onTestSuccess(final ITestResult iTestResult) {
        LOG.debug("I am in onTestSuccess method "
                + getTestMethodName(iTestResult) + " succeed");
        ReportTestManager.getTest().log(LogStatus.PASS, "Test passed");
    }

    @Override public void onTestFailure(final ITestResult iTestResult) {
        LOG.error("I am in onTestFailure method "
                + getTestMethodName(iTestResult) + " failed");
        ReportTestManager.getTest().log(LogStatus.FAIL,
                "Test Failed :" + iTestResult.getThrowable());
        // Extentreports log and screenshot operations for failed tests.
        Object classInstance = iTestResult.getInstance();
        WebDriver driver = ((TestBase) classInstance).getDriver();
        if (driver != null) {
            String base64Screenshot = "data:image/png;base64,"
                    + ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BASE64);
            ReportTestManager.getTest().log(LogStatus.FAIL, ReportTestManager
                    .getTest().addBase64ScreenShot(base64Screenshot));
        }

    }

    @Override public void onTestSkipped(final ITestResult iTestResult) {
        LOG.debug("I am in onTestSkipped method "
                + getTestMethodName(iTestResult) + " skipped");
        // Extent reports log operation for skipped tests.
        ReportTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
    }

    @Override public void onTestFailedButWithinSuccessPercentage(
            final ITestResult iTestResult) {
        LOG.error("Test failed but it is in defined success ratio "
                + getTestMethodName(iTestResult));
    }

}
