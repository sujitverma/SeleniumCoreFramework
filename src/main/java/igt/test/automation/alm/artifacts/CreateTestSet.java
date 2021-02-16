
package igt.test.automation.alm.artifacts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import igt.test.automation.alm.functionlibrary.ALMCookiesHeaders;
import igt.test.automation.alm.functionlibrary.APIManager;
import igt.test.automation.alm.request.pojo.Field;
import igt.test.automation.alm.request.pojo.Fields;
import igt.test.automation.alm.request.pojo.Value;
import igt.test.automation.util.ApiUtil;
import io.restassured.response.Response;

/** Class to create Test set json string. */
public class CreateTestSet {

    /** The Log4j logger. */
    private static final Logger LOG = LogManager.getLogger(CreateTestSet.class);

    /**
     * Create the json request body for ALM Test Set.
     * 
     * @param parentId
     *            - Its a directory/folder Id under Test Lab.
     * @return Json String which will be use to hit alm rest request.
     * 
     * @author 
     */
    public static String createTestSetRequest(final String parentId) {
        String jsonString = null;
        Fields fieldsObj = new Fields();
        SimpleDateFormat format = new SimpleDateFormat("ddMMMyyyy_kkmmss");
        Date date = new Date();
        String dateTimeString = format.format(date);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateformat.format(date);
        try {
            fieldsObj.setType("test-set");
            String testSetname = "Automation_" + dateTimeString;
            List<Field> fieldList = new ArrayList<>();

            // **************** Description Field **************************
            Field descFieldObj = new Field();
            Value descValueObj = new Value();
            descFieldObj.setName("description");
            descValueObj.setValue("Description of test set " + testSetname);
            List<Value> descriptionValueObjList = new ArrayList<>();
            descriptionValueObjList.add(descValueObj);
            descFieldObj.setValues(descriptionValueObjList);
            fieldList.add(descFieldObj);

            // **************** Subtype Field **************************
            Field subTypeFieldObj = new Field();
            Value subTypeValueObj = new Value();
            subTypeFieldObj.setName("subtype-id");
            subTypeValueObj.setValue("hp.qc.test-set.default");
            List<Value> subTypeidValueObjList = new ArrayList<>();
            subTypeidValueObjList.add(subTypeValueObj);
            subTypeFieldObj.setValues(subTypeidValueObjList);
            fieldList.add(subTypeFieldObj);

            // **************** Open date Field **************************
            Field openDateFieldObj = new Field();
            Value openDatedescValueObj = new Value();
            openDateFieldObj.setName("open-date");
            openDatedescValueObj.setValue(dateString);
            List<Value> openDateValueObjList = new ArrayList<>();
            openDateValueObjList.add(openDatedescValueObj);
            openDateFieldObj.setValues(openDateValueObjList);
            fieldList.add(openDateFieldObj);

            // **************** Name Field **************************
            Field nameFieldObj = new Field();
            Value nameValueObj = new Value();
            nameFieldObj.setName("name");
            nameValueObj.setValue(testSetname);
            List<Value> nameValueObjList = new ArrayList<>();
            nameValueObjList.add(nameValueObj);
            nameFieldObj.setValues(nameValueObjList);
            fieldList.add(nameFieldObj);

            // **************** Parent Id Field **************************
            Field parentFieldObj = new Field();
            Value parentValueObj = new Value();
            parentFieldObj.setName("parent-id");
            parentValueObj.setValue(parentId);
            List<Value> parentValueObjList = new ArrayList<>();
            parentValueObjList.add(parentValueObj);
            parentFieldObj.setValues(parentValueObjList);
            fieldList.add(parentFieldObj);

            // **************** Parent Id Field **************************
            Field statusFieldObj = new Field();
            Value statusValueObj = new Value();
            statusFieldObj.setName("status");
            statusValueObj.setValue("Open");
            List<Value> statusValueObjList = new ArrayList<>();
            statusValueObjList.add(statusValueObj);
            statusFieldObj.setValues(statusValueObjList);
            fieldList.add(statusFieldObj);

            fieldsObj.setFields(fieldList);

            jsonString = APIManager.convertToJson(fieldsObj);
            LOG.info("Test Set Json Request:- " + jsonString);

        } catch (Exception e) {
            LOG.error("Exception while creating Test Set request:- "
                    + e);
        }
        return jsonString;
    }

    /**
     * Function hit ALM rest request to create new Test Set in given
     * <code>testSetParentFolderId</code> directory under Test Lab.
     * 
     * @param host
     *            - ALM host to access
     * @param domain
     *            - ALM project domain
     * @param project
     *            - ALM project
     * @param cookieHeaderObject
     *            - {@link ALMCookiesHeaders} ALM cookies and headers generated
     *            after login.
     * @param testSetParentFolderId
     *            - Test lab directory name in which new test set will be
     *            created.
     * @return - {@link Response} ResAssured Json response
     */
    public Response hitCreateTestSetRequest(final String host, final String domain,
            final String project, final ALMCookiesHeaders cookieHeaderObject,
            final String testSetParentFolderId) {
        Response response = null;
        try {
            String requestJson = createTestSetRequest(testSetParentFolderId);
            String url = host + "/rest/domains/" + domain + "/projects/"
                    + project + "/test-sets";
            response = ApiUtil.postRequest(url, cookieHeaderObject.getHeaders(),
                    cookieHeaderObject.getCookies(), requestJson);
        } catch (Exception e) {
            LOG.error("Exception while creating new Test Set in ALM :- "
                    + e);
        }
        return response;
    }
}
