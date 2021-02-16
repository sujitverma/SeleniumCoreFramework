
package igt.test.automation.driver;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import igt.test.automation.alm.artifacts.CreateDefect;
import igt.test.automation.alm.artifacts.CreateLinkDefect;
import igt.test.automation.alm.artifacts.TestInstance;
import igt.test.automation.alm.functionlibrary.ALMCookiesHeaders;
import igt.test.automation.alm.functionlibrary.ALMDefect;
import igt.test.automation.alm.functionlibrary.ALMLogin;
import igt.test.automation.alm.functionlibrary.ALMOperationUtil;
import igt.test.automation.alm.functionlibrary.APIManager;
import igt.test.automation.alm.functionlibrary.InitiateALM;
import igt.test.automation.alm.functionlibrary.TestSet;
import igt.test.automation.alm.response.pojo.Entity;
import igt.test.automation.alm.response.pojo.Field;
import igt.test.automation.selenium.constants.IGlobalConstants;
import igt.test.automation.util.ApiUtil;
import io.restassured.response.Response;

/**
 * Class contain TestNG initial driver function which run before and after of
 * TestSuite, TestMethod, etc.
 * 
 * @author 
 *
 */
public class TestNGDriver {
    /** The Log4j logger. */
    private static final Logger LOG = LogManager.getLogger(TestNGDriver.class);

    /**
     * ALM Test Set object which have test set information like test set id,
     * name, etc.
     */
    private static TestSet testSetObj;

    /**
     * ALM Test Instance object which have test instance information like test
     * instance id, name, etc.
     */
    private static TestInstance testInstance;

    /**
     * Perform before start test NG suite.
     * 
     * @author 
     */
    @BeforeSuite(alwaysRun = true) public void beforeSuiteDriver() {
        ALMOperationUtil almUtil = new ALMOperationUtil();
        ALMLogin almLoginDataObj = new ALMLogin();
        try {
            // ********** Create Test Set ****************
            almLoginDataObj = readPropertyFile();
            if ("true".equals(almLoginDataObj.getAlmIntegrationSwitch())) {
                testSetObj = almUtil.createTestSet(almLoginDataObj);
            } else {
                testSetObj = null;
            }
        } catch (Exception e) {
            LOG.error("Exception in before suite function: - " + e);
        }
    }

    /**
     * Perform before every @test method.
     * 
     * @param method
     *            - {@link Method} - java reflection method
     * 
     * @author 
     */
    @BeforeMethod(alwaysRun = true) public void beforeTestCaseDriver(final Method method) {
        List<String> testCaseList = new ArrayList<>();
        ALMOperationUtil almUtilObj = new ALMOperationUtil();
        ALMLogin almLoginDataObj = new ALMLogin();
        InitiateALM initiateALMObj = new InitiateALM();
        ALMCookiesHeaders cookieHeaderObj = null;
        try {
            // ******** checking that new test set created or not
            // *******************
            if (testSetObj != null) {
                Test testAnnotation = method.getAnnotation(Test.class);
                String testName = testAnnotation.testName();
                if (testName.contains(",")) {
                    String[] testList = testName.split(",");
                    for (String e : testList) {
                        testCaseList.add(e);
                    }
                } else {
                    testCaseList.add(testName);
                }
                almLoginDataObj = readPropertyFile();
                cookieHeaderObj = initiateALMObj.signInALM(
                        almLoginDataObj.getUserName(),
                        almLoginDataObj.getPassword(),
                        almLoginDataObj.getHost());
                for (String testCase : testCaseList) {
                    testInstance = almUtilObj.addTestCaseInTestSet(
                            almLoginDataObj, cookieHeaderObj, testCase,
                            testSetObj.getId());
                }
                initiateALMObj.signOutALM(almLoginDataObj.getHost(),
                        cookieHeaderObj);
            }
        } catch (Exception e) {
            initiateALMObj.signOutALM(almLoginDataObj.getHost(),
                    cookieHeaderObj);
            LOG.error("Exception in before method function: - " + e);
        }
    }

    /**
     * Perform after every @test method, it will update ALM Test Instance with
     * the Test final result.
     * 
     * @param method
     *            - {@link ITestResult} - TestNG Test Result listener to get
     *            final test result.
     * 
     * @author 
     */
    @AfterMethod(alwaysRun = true) public void afterTestCaseDriver(final Method method,
            final ITestResult result) {
        List<String> testCaseList = new ArrayList<>();
        ALMOperationUtil almUtilObj = new ALMOperationUtil();
        ALMLogin almLoginDataObj = new ALMLogin();
        almLoginDataObj = readPropertyFile();
        String status = "No Run";
        InitiateALM initiateALMObj = new InitiateALM();
        ALMCookiesHeaders cookieHeaderObj = null;
        CreateDefect createDefectObj = new CreateDefect();
        String defectId = null;
        ALMDefect almDefectInfo = readALMDefectPropertyFile();
        CreateLinkDefect linkDefectObj = new CreateLinkDefect();
        String newDefectId = null;
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                status = "Failed";
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                status = "Passed";
            }
            // ************ checking test set object has created or not ******
            if (testSetObj != null) {
                almUtilObj.updateTestCaseInTestSet(almLoginDataObj,
                        testInstance.getTestCaseId(),
                        testInstance.getTestSetId(), testInstance.getId(),
                        status);

                // ********** Create Defect if test status is failure
                // ***************
                Test testAnnotation = method.getAnnotation(Test.class);
                String testName = testAnnotation.testName();
                if (testName.contains(",")) {
                    String[] testList = testName.split(",");
                    for (String e : testList) {
                        testCaseList.add(e);
                    }
                } else {
                    testCaseList.add(testName);
                }

                String description = testAnnotation.description();
                String methodName = method.getName();
                String defectSummary = "Automation - Test Case - " + methodName
                        + " failed.";
                if ("Failed".equalsIgnoreCase(status)) {
                    cookieHeaderObj = initiateALMObj.signInALM(
                            almLoginDataObj.getUserName(),
                            almLoginDataObj.getPassword(),
                            almLoginDataObj.getHost());
                    defectId = almUtilObj.checkIsDefectExistsForTestCase(
                            almLoginDataObj, cookieHeaderObj,
                            testCaseList.get(0));
                    if ("0".equals(defectId)) {
                        String userName = almLoginDataObj.getUserName();
                        String almDefectRequestJsonString = createDefectObj
                                .createDefectRequest(userName,
                                        almDefectInfo.getTestSetUpInfo(),
                                        defectSummary, "Follow Test Case Steps",
                                        "Open", description, userName,
                                        almDefectInfo.getFunctionalComponent(),
                                        almDefectInfo.getDevTeamName(),
                                        almDefectInfo.getTestingPhase());
                        String url = almLoginDataObj.getHost()
                                + "/rest/domains/" + almLoginDataObj.getDomain()
                                + "/projects/" + almLoginDataObj.getProject()
                                + "/defects";
                        Response response = ApiUtil.postRequest(url,
                                cookieHeaderObj.getHeaders(),
                                cookieHeaderObj.getCookies(),
                                almDefectRequestJsonString);
                        LOG.info("Defect creation Response:- "
                                + response.asString());
                        if (response.getStatusCode() == 201) {
                            Entity responseObj = (Entity) APIManager
                                    .convertFromJson(response.asString(),
                                            Entity.class);
                            for (int i = 0; i < responseObj.getFields()
                                    .size(); i++) {
                                Field fieldObj = responseObj.getFields().get(i);
                                String name = fieldObj.getName();
                                if ("id".equalsIgnoreCase(name)) {
                                    newDefectId = fieldObj.getValues().get(0)
                                            .getValue();
                                }
                            }
                        } else {
                            LOG.error(
                                    "Defect creation unsuccessfull. Actual Response is :- "
                                            + response.asString());
                        }

                        String linkDefectRequest = linkDefectObj
                                .createLinkDefectRequest(
                                        testCaseList.get(0),
                                        newDefectId);
                        String ownerName = linkDefectObj.hitDefectLink(
                                almLoginDataObj, cookieHeaderObj,
                                linkDefectRequest);
                        if (ownerName != null) {
                            LOG.info(
                                    "Defect linked successfully. Defect owner is:- "
                                            + ownerName);
                        } else {
                            LOG.info("Defect linked Unsuccessfully.");
                        }

                    } else {
                        LOG.info("Already existing defect. Defect id:- "
                                + defectId);
                    }
                }

            }

        } catch (Exception e) {
            LOG.error("Exception in After Method function: - " + e);
        }
    }

    /**
     * Function to read the ALMConfig.properties from location
     * src/main/resources. It will fail if file did not found
     * 
     * @return ALM Login java object which contain following information: ALM
     *         Host url ALM username ALM password ALM Domain name ALM Project
     *         name ALM Test Lab Parent folder id.
     * 
     * @author 
     */
    public static ALMLogin readPropertyFile() {
        ALMLogin newDataObj = new ALMLogin();
        Properties prop = new Properties();
        try {
            FileReader reader = new FileReader(SystemUtils.getUserDir()
                    + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER+"ALMConfig.properties");
            prop.load(reader);
            newDataObj.setHost(prop.getProperty("ALMHost"));
            newDataObj.setUserName(prop.getProperty("ALMUserName"));
            newDataObj.setPassword(prop.getProperty("ALMPassword"));
            newDataObj.setDomain(prop.getProperty("ALMDomain"));
            newDataObj.setProject(prop.getProperty("ALMProject"));
            newDataObj.setTestSetParentFolder(
                    prop.getProperty("ALMTestSetParentFolder"));
            newDataObj.setAlmIntegrationSwitch(
                    prop.getProperty("ALMIntegrationSwitch"));
        } catch (Exception e) {
            LOG.error(
                    "Exception in reading the ALM data config property file: - "
                            + e);
        }
        return newDataObj;
    }

    /**
     * Function to read the LMDefectUserFields.properties from location
     * src/main/resources. It will fail if file did not found
     * 
     * @return {@link ALMDefect} Return ALM defect related information like
     *         Functional component, Dev team name, etc.
     */
    public static ALMDefect readALMDefectPropertyFile() {
        ALMDefect newDataObj = new ALMDefect();
        Properties prop = new Properties();
        try {
            FileReader reader = new FileReader(SystemUtils.getUserDir()
                    + IGlobalConstants.GLOBAL_MAIN_RESOURCES_FOLDER+"ALMConfig.properties");
            prop.load(reader);
            newDataObj.setTestSetUpInfo(prop.getProperty("SetUpInformation"));
            newDataObj.setFunctionalComponent(
                    prop.getProperty("FunctionalComponent"));
            newDataObj.setFunctionalComponent(prop.getProperty("TestingPhase"));
            newDataObj.setDevTeamName(prop.getProperty("DevTeamName"));
        } catch (Exception e) {
            LOG.error("Exception in reading the ALM Defect property file: - "
                    + e);
        }
        return newDataObj;
    }
}
