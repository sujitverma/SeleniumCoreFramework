
package igt.test.automation.bdd.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;

import java.util.HashMap;
import java.util.Map;

public final class ExtentReportTestManager {

    private ExtentReportTestManager() {

    }

    protected static Map<Integer, ExtentTest> reportFeatureTestMap = new HashMap<>();

    protected static ExtentReports extent = ExtentReportManager
            .getReporterInstance();

    public static synchronized ExtentTest getTest() {
        return reportFeatureTestMap.get((int) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest startTest(final String testName,
            final String desc) {

        ExtentTest test = extent.createTest(Feature.class, testName, desc);
        reportFeatureTestMap.put((int) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized ExtentTest startScenario(
            final String scenarioName) {
        return ExtentReportTestManager.getTest().createNode(Scenario.class,
                scenarioName);
        
    }

    public static synchronized void endTest() {
        extent.flush();
    }
}
