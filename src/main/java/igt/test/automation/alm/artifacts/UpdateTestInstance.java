
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

/** Class contains functions which need to update the test instance. */
public class UpdateTestInstance {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager
            .getLogger(UpdateTestInstance.class);

    /**
     * Function to create ALM Test Instance Json request which will update the
     * test instance in Test Set.
     * 
     * @param testCaseId
     *            - ALM test case Id.
     * @param testSetId
     *            - ALM Test Set Id.
     * @param testerAlmId
     *            - ALM User Name.
     * @param status
     *            - Status of test instance.
     * @param subTypeId
     *            - test case sub type id.
     * @return json string.
     * 
     * @author 
     */
    public String updateTestInstanceRequest(final String testCaseId,
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
            LOG.info("Update Test Instance Json String:- " + jsonString);

        } catch (Exception e) {
            LOG.error("Exception while creating update test instance request:- "
                    + e);
        }
        return jsonString;
    }
}
