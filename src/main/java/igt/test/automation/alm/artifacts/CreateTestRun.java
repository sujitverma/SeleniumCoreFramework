
package igt.test.automation.alm.artifacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import igt.test.automation.alm.functionlibrary.APIManager;
import igt.test.automation.alm.request.pojo.Field;
import igt.test.automation.alm.request.pojo.Fields;
import igt.test.automation.alm.request.pojo.Value;

/** Class to create Test Run Json string. */
public class CreateTestRun {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager
            .getLogger(CreateTestRun.class);

    /**
     * Create the json request body for ALM Test Run.
     * 
     * @param testCaseId
     *            - ALM Test Case Id.
     * @param testSetId
     *            - ALM Test Set Id under Test Lab.
     * @param testerAlmId
     *            - ALM user Id.
     * @param testCycleId
     *            - ALM Test Instance Id.
     * @param status
     *            - ALM Status of Test Instance. example : Passed/Failed/No Run.
     * @param subTypeId
     *            - ALM test case type value. Example: VAPI-XP/Manual/etc.
     * @return Json String which will be use to hit alm rest request.
     * 
     * @author 
     */
    public String createTestRunRequest(final String testCaseId,
            final String testSetId, final String testerAlmId,
            final String testCycleId, final String status,
            final String subTypeId) {
        String jsonString = null;
        Fields fieldsObj = new Fields();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss");
        String dateTimeString = format.format(date);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateformat.format(date);
        try {
            fieldsObj.setType("run");
            List<Field> fieldList = new ArrayList<>();

            // **************** Test Id Field **************************
            Field testIdFieldObj = new Field();
            Value testIdValueObj = new Value();
            testIdFieldObj.setName("test-id");
            testIdValueObj.setValue(testCaseId);
            List<Value> testIdValueObjList = new ArrayList<>();
            testIdValueObjList.add(testIdValueObj);
            testIdFieldObj.setValues(testIdValueObjList);
            fieldList.add(testIdFieldObj);

            // **************** Run Name Field **************************
            Field nameFieldObj = new Field();
            Value nameValueObj = new Value();
            nameFieldObj.setName("name");
            nameValueObj.setValue("Fast_Run_" + dateString);
            List<Value> nameValueObjList = new ArrayList<>();
            nameValueObjList.add(nameValueObj);
            nameFieldObj.setValues(nameValueObjList);
            fieldList.add(nameFieldObj);

            // **************** Test Cycle Id Name Field
            // **************************
            Field testCycleIdFieldObj = new Field();
            Value testCycleIdValueObj = new Value();
            testCycleIdFieldObj.setName("testcycl-id");
            testCycleIdValueObj.setValue(testCycleId);
            List<Value> testCycleIdValueObjList = new ArrayList<>();
            testCycleIdValueObjList.add(testCycleIdValueObj);
            testCycleIdFieldObj.setValues(testCycleIdValueObjList);
            fieldList.add(testCycleIdFieldObj);

            // **************** Subtype Field **************************
            Field subTypeFieldObj = new Field();
            Value subTypeValueObj = new Value();
            subTypeFieldObj.setName("subtype-id");
            subTypeValueObj.setValue("hp.qc.run." + subTypeId);
            List<Value> subTypeidValueObjList = new ArrayList<>();
            subTypeidValueObjList.add(subTypeValueObj);
            subTypeFieldObj.setValues(subTypeidValueObjList);
            fieldList.add(subTypeFieldObj);

            // **************** Execution Time Field **************************
            Field execTimeFieldObj = new Field();
            Value execTimeValueObj = new Value();
            execTimeFieldObj.setName("execution-time");
            execTimeValueObj.setValue(dateTimeString);
            List<Value> execTimeValueObjList = new ArrayList<>();
            execTimeValueObjList.add(execTimeValueObj);
            execTimeFieldObj.setValues(execTimeValueObjList);
            fieldList.add(execTimeFieldObj);

            // **************** Execution Date Field **************************
            Field execDateFieldObj = new Field();
            Value execDateValueObj = new Value();
            execDateFieldObj.setName("execution-date");
            execDateValueObj.setValue(dateString);
            List<Value> execDateValueObjList = new ArrayList<>();
            execDateValueObjList.add(execDateValueObj);
            execDateFieldObj.setValues(execDateValueObjList);
            fieldList.add(execDateFieldObj);

            // **************** Last Modified Field **************************
            Field lastModifiedFieldObj = new Field();
            Value lastModifiedValueObj = new Value();
            lastModifiedFieldObj.setName("last-modified");
            lastModifiedValueObj.setValue(dateString + " " + dateTimeString);
            List<Value> lastModifiedValueObjList = new ArrayList<>();
            lastModifiedValueObjList.add(lastModifiedValueObj);
            lastModifiedFieldObj.setValues(lastModifiedValueObjList);
            fieldList.add(lastModifiedFieldObj);

            // **************** Cycle Id (Test Set Id) Field
            // **************************
            Field cycleIDFieldObj = new Field();
            Value cycleIDValueObj = new Value();
            cycleIDFieldObj.setName("cycle-id");
            cycleIDValueObj.setValue(testSetId);
            List<Value> cycleIDValueObjList = new ArrayList<>();
            cycleIDValueObjList.add(cycleIDValueObj);
            cycleIDFieldObj.setValues(cycleIDValueObjList);
            fieldList.add(cycleIDFieldObj);

            // **************** Status Field **************************
            Field statusFieldObj = new Field();
            Value statusValueObj = new Value();
            statusFieldObj.setName("status");
            statusValueObj.setValue(status);
            List<Value> statusValueObjList = new ArrayList<>();
            statusValueObjList.add(statusValueObj);
            statusFieldObj.setValues(statusValueObjList);
            fieldList.add(statusFieldObj);

            // **************** Actual Tester Field **************************
            Field actualTesterFieldObj = new Field();
            Value actualTesterValueObj = new Value();
            actualTesterFieldObj.setName("owner");
            actualTesterValueObj.setValue(testerAlmId);
            List<Value> actualTesterValueObjList = new ArrayList<>();
            actualTesterValueObjList.add(actualTesterValueObj);
            actualTesterFieldObj.setValues(actualTesterValueObjList);
            fieldList.add(actualTesterFieldObj);

            // **************** User 04 field Field **************************
            Field user04FieldObj = new Field();
            Value user04ValueObj = new Value();
            user04FieldObj.setName("user-04");
            user04ValueObj.setValue("Automation");
            List<Value> user04ValueObjList = new ArrayList<>();
            user04ValueObjList.add(user04ValueObj);
            user04FieldObj.setValues(user04ValueObjList);
            fieldList.add(user04FieldObj);

            fieldsObj.setFields(fieldList);

            jsonString = APIManager.convertToJson(fieldsObj);
            LOG.info("Test Run json string:- " + jsonString);
        } catch (Exception e) {
            LOG.error("Exception while creating Test Run request:- "
                    + e);
        }
        return jsonString;
    }

}
