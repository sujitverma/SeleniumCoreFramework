
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

/** Class to create test instance json string. */
public class CreateTestInstance {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager
            .getLogger(CreateTestInstance.class);

    /**
     * Create the json request body for ALM Test Instance.
     * 
     * @param testCaseId
     *            - ALM Test Case Id.
     * @param testSetId
     *            - ALM Test Set Id under Test Lab.
     * @param testerAlmId
     *            - ALM user Id.
     * @param status
     *            - ALM Status of Test Instance. example : Passed/Failed/No Run.
     * @param subTypeId
     *            - ALM test case type value. Example: VAPI-XP/Manual/etc.
     * @return Json String which will be use to hit alm rest request.
     * 
     * @author 
     */
    public String createTestInstanceRequest(final String testCaseId,
            final String testSetId, final String testerAlmId,
            final String status, final String subTypeId) {
        String jsonString = null;
        Fields fieldsObj = new Fields();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss");
        String dateTimeString = format.format(date);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateformat.format(date);
        try {
            fieldsObj.setType("test-instance");
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

            // **************** Subtype Field **************************
            Field subTypeFieldObj = new Field();
            Value subTypeValueObj = new Value();
            subTypeFieldObj.setName("subtype-id");
            subTypeValueObj.setValue("hp.qc.test-instance." + subTypeId);
            List<Value> subTypeidValueObjList = new ArrayList<>();
            subTypeidValueObjList.add(subTypeValueObj);
            subTypeFieldObj.setValues(subTypeidValueObjList);
            fieldList.add(subTypeFieldObj);

            // **************** Execution Time Field **************************
            Field execTimeFieldObj = new Field();
            Value execTimeValueObj = new Value();
            execTimeFieldObj.setName("exec-time");
            execTimeValueObj.setValue(dateTimeString);
            List<Value> execTimeValueObjList = new ArrayList<>();
            execTimeValueObjList.add(execTimeValueObj);
            execTimeFieldObj.setValues(execTimeValueObjList);
            fieldList.add(execTimeFieldObj);

            // **************** Execution Date Field **************************
            Field execDateFieldObj = new Field();
            Value execDateValueObj = new Value();
            execDateFieldObj.setName("exec-date");
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

            // **************** Cycle Id (Test Set Id) Field ********
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
            actualTesterFieldObj.setName("actual-tester");
            actualTesterValueObj.setValue(testerAlmId);
            List<Value> actualTesterValueObjList = new ArrayList<>();
            actualTesterValueObjList.add(actualTesterValueObj);
            actualTesterFieldObj.setValues(actualTesterValueObjList);
            fieldList.add(actualTesterFieldObj);

            fieldsObj.setFields(fieldList);

            jsonString = APIManager.convertToJson(fieldsObj);
            LOG.info("Test Instance Json String: " + jsonString);

        } catch (Exception e) {
            LOG.error("Exception while creating Test Instance Json request:- "
                    + e);
        }
        return jsonString;
    }
}
