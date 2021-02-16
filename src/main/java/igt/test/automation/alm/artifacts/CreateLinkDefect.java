
package igt.test.automation.alm.artifacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import igt.test.automation.alm.functionlibrary.ALMCookiesHeaders;
import igt.test.automation.alm.functionlibrary.ALMLogin;
import igt.test.automation.alm.functionlibrary.APIManager;
import igt.test.automation.alm.request.pojo.Field;
import igt.test.automation.alm.request.pojo.Fields;
import igt.test.automation.alm.request.pojo.Value;
import igt.test.automation.alm.response.pojo.Entity;
import igt.test.automation.util.ApiUtil;
import io.restassured.response.Response;

/**
 * Class use to create defect link request and link the defect.
 * 
 * @author 
 *
 */
public class CreateLinkDefect {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager
            .getLogger(CreateLinkDefect.class);

    /** HTTP Status code 201. */
    private static final int HTTPSTATUSCODE_201 = 201;
    /**
     * Function use to create the defect link json request body.
     * 
     * @param testCaseId
     *            - ALM Test Case Id which need to link with a defect.
     * @param defectId
     *            - ALM Defect Id which need to link with test case.
     * @return - Defect link Json request body.
     */
    public String createLinkDefectRequest(final String testCaseId,
            final String defectId) {
        List<Field> fieldList = new ArrayList<>();
        String jsonString = null;
        Fields fieldsObj = new Fields();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss");
        String dateTimeString = format.format(date);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateformat.format(date);
        try {
            fieldsObj.setType("defect-link");

            // **************** Last Modified Field **************************
            Field lastModifiedFieldObj = new Field();
            Value lastModifiedValueObj = new Value();
            lastModifiedFieldObj.setName("last-modified");
            lastModifiedValueObj.setValue(dateString + " " + dateTimeString);
            List<Value> lastModifiedValueObjList = new ArrayList<>();
            lastModifiedValueObjList.add(lastModifiedValueObj);
            lastModifiedFieldObj.setValues(lastModifiedValueObjList);
            fieldList.add(lastModifiedFieldObj);

            // *********** second-endpoint-id Additional Field *********
            Field secondEndpointIdFieldObj = new Field();
            Value secondEndpointIdValueObj = new Value();
            secondEndpointIdFieldObj.setName("second-endpoint-id");
            secondEndpointIdValueObj.setValue(testCaseId);
            List<Value> secondEndpointIdValueObjList = new ArrayList<>();
            secondEndpointIdValueObjList.add(secondEndpointIdValueObj);
            secondEndpointIdFieldObj.setValues(secondEndpointIdValueObjList);
            fieldList.add(secondEndpointIdFieldObj);

            // ************ first-endpoint-id Additional Field *************
            Field firstEndpointIdFieldObj = new Field();
            Value firstEndpointIdValueObj = new Value();
            firstEndpointIdFieldObj.setName("first-endpoint-id");
            firstEndpointIdValueObj.setValue(defectId);
            List<Value> firstEndpointIdValueObjList = new ArrayList<>();
            firstEndpointIdValueObjList.add(firstEndpointIdValueObj);
            firstEndpointIdFieldObj.setValues(firstEndpointIdValueObjList);
            fieldList.add(firstEndpointIdFieldObj);

            // ****************id Additional Field **************************
            Field idFieldObj = new Field();
            Value idValueObj = new Value();
            idFieldObj.setName("id");
            idValueObj.setValue(defectId);
            List<Value> idValueObjList = new ArrayList<>();
            idValueObjList.add(idValueObj);
            idFieldObj.setValues(idValueObjList);
            fieldList.add(idFieldObj);

            // ************second-endpoint-type Additional Field **************
            Field secondEndpointTypeFieldObj = new Field();
            Value secondEndpointTypeValueObj = new Value();
            secondEndpointTypeFieldObj.setName("second-endpoint-type");
            secondEndpointTypeValueObj.setValue("test");
            List<Value> secondEndpointTypeValueObjList = new ArrayList<>();
            secondEndpointTypeValueObjList.add(secondEndpointTypeValueObj);
            secondEndpointTypeFieldObj
                    .setValues(secondEndpointTypeValueObjList);
            fieldList.add(secondEndpointTypeFieldObj);

            // ************creation-time Additional Field ******************
            Field creationTimeFieldObj = new Field();
            Value creationTimeValueObj = new Value();
            creationTimeFieldObj.setName("creation-time");
            creationTimeValueObj.setValue(dateString);
            List<Value> creationTimeValueObjList = new ArrayList<>();
            creationTimeValueObjList.add(creationTimeValueObj);
            creationTimeFieldObj.setValues(creationTimeValueObjList);
            fieldList.add(creationTimeFieldObj);

            fieldsObj.setFields(fieldList);

            jsonString = APIManager.convertToJson(fieldsObj);
            LOG.info(jsonString);
        } catch (Exception e) {
            LOG.error("Exception while creating Defect link Request :- "
                    + e);
        }
        return jsonString;
    }

    /**
     * Function to hit defect link api and verify that defect has successfully
     * linked or not.
     * 
     * @param almLoginDataObj
     *            - {@link ALMLogin}
     * @param cookieHeaderObj
     *            - {@link ALMCookiesHeaders}
     * @param inputJson
     *            - Json request
     * @return - owner name who linked this defect.
     * 
     * @author 
     */
    public String hitDefectLink(final ALMLogin almLoginDataObj,
            final ALMCookiesHeaders cookieHeaderObj, final String inputJson) {
        String owner = null;
        try {
            String url = almLoginDataObj.getHost() + "/rest/domains/"
                    + almLoginDataObj.getDomain() + "/projects/"
                    + almLoginDataObj.getProject() + "/defect-links";
            Response response = ApiUtil.postRequest(url,
                    cookieHeaderObj.getHeaders(), cookieHeaderObj.getCookies(),
                    inputJson);
            if (response.getStatusCode() == HTTPSTATUSCODE_201) {
                Entity responseObj = (Entity) APIManager
                        .convertFromJson(response.asString(), Entity.class);
                for (int i = 0; i < responseObj.getFields().size(); i++) {
                    igt.test.automation.alm.response.pojo.Field fieldObj = responseObj
                            .getFields().get(i);
                    String name = fieldObj.getName();
                    if ("owner".equalsIgnoreCase(name)) {
                        owner = fieldObj.getValues().get(0).getValue();
                    }
                }
            } else {
                LOG.error("Defect link unsuccessfull. Actual Response is :- "
                        + response.asString());
            }

        } catch (Exception e) {
            LOG.error("Exception while hitting defect link API:- "
                    + e);
        }
        return owner;
    }
}
