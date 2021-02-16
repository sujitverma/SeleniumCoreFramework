
package igt.test.automation.bdd.base;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class TestBase {

    public WebDriver driver;

    public ExtentTest extentFeature;

    public ExtentTest extentScenario;

    public SoftAssert softAssert;

}
