package igt.test.automation.bdd.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import igt.test.automation.bdd.report.ExtentReportTestManager;

/**
 * TestListeners override the inbuilt testNG listeners.
 *
 */
public class BDDListeners implements ITestListener {

    /** Log4j Logger. */
    private static final Logger LOG = LogManager.getLogger(BDDListeners.class);

    @Override public void onTestStart(final ITestResult result) {

        LOG.info("I am in onTestStart method");
    }

    @Override public void onTestSuccess(final ITestResult result) {
        LOG.info("I am in onTestSuccess method ");
        ExtentReportTestManager.getTest().pass("Feature passed");
    }

    @Override public void onTestFailure(final ITestResult result) {
        LOG.info("I am in onTestFailure method");
        ExtentReportTestManager.getTest().fail("Feature failed");
    }

    @Override public void onTestSkipped(final ITestResult result) {
        //Do nothing
    }

    @Override public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {
        //Do nothing
    }

    @Override public void onStart(final ITestContext context) {
        //Do nothing
    }

    @Override public void onFinish(final ITestContext context) {
        LOG.info("I am in onFinish method");
        ExtentReportTestManager.endTest();
    }
}
