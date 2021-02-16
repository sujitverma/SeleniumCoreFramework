
package igt.test.automation.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry Analyzer is to analyze the failed tests and a chance to retry a failed
 * test.
 * 
 * @author 
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    /** Declaring integer value 0 to COUNTER. */
    private int counter = 0;

    /*
     * (non-Javadoc)
     * 
     * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
     * 
     * This method decides how many times a test needs to be rerun. TestNg will
     * call this method every time a test fails. So we can put some code in here
     * to decide when to rerun the test.
     * 
     * Note: This method will return true if a tests needs to be retried and
     * false it not.
     *
     */
    @Override public boolean retry(final ITestResult result) {
        int retryCount = Integer.parseInt(System.getProperty("retryCount"));

        if (counter < retryCount) {
            counter++;
            return true;
        }
        return false;
    }

    /**
     * getting the testng execution station to confirm whether a test is failed
     * or not.
     * 
     * @param result
     *            ITestResult object
     * @return int
     */
    public int getTestStatus(final ITestResult result) {
        return result.getStatus();
    }

}
