
package igt.test.automation.bdd.runner;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@CucumberOptions(plugin = { "pretty",
        "html:target/cucumber-reports/cucumber-pretty",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "rerun:target/cucumber-reports/re-run.txt" }, 
        glue = {
                "aero.igt.psetest.automation.bdd.base" }, 
        monochrome = true, 
        dryRun = false, 
        strict = true)
@Listeners({igt.test.automation.bdd.listeners.BDDListeners.class})
public class Runner {
    private TestNGCucumberRunner runner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {

        runner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleEventWrapper pickleWrapper,
            CucumberFeatureWrapper featureWrapper) throws Throwable {
        // the 'featureWrapper' parameter solely exists to display the feature
        // file in a
        // test report
        runner.runScenario(pickleWrapper.getPickleEvent());
    }

    /**
     * Returns two dimensional array of PickleEventWrapper scenarios with their
     * associated CucumberFeatureWrapper feature.
     *
     * @return a two dimensional array of scenarios features.
     */
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        if (runner == null) {
            return new Object[0][0];
        }
        return runner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        if (runner == null) {
            return;
        }

        runner.finish();
    }
}
