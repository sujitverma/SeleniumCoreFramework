package igt.test.automation.util;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import igt.test.automation.alm.infrastructure.AlmConnector;
import igt.test.automation.alm.infrastructure.Response;
import igt.test.automation.alm.infrastructure.RestConnector;

/**
 * this utility class is meant for Updating the ALM test result.
 *
 * @author Sujit
 *
 */

public class UpdateALMTestResult {

    /**
     * method to update the test result in HP ALM. 
     * 
     * @param almHost
     * @param almDomain
     * @param almProject
     * @param almUser
     * @param almPassword
     * @param id
     * @param result
     * @return {@link Response}
     * @throws Exception
     */
    public Response updatetestresult(final String almHost, final String almDomain, final String almProject, final String almUser,
            final String almPassword, final String id, final String result) throws Exception {

        AlmConnector alm = new AlmConnector();
        RestConnector conn = RestConnector.getInstance();
        conn.init(new HashMap<String, String>(), almHost, almDomain, almProject);
        alm.login(almUser, almPassword);
        conn.getQCSession();
        String entityUrl = conn.buildEntityUrl("test-instance", id);
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        String updatedField = "status";
        String updatedFieldInitialUpdateValue = result;
        String updatedEntityXml = generatetestinstanceUpdateXml(updatedField, updatedFieldInitialUpdateValue);
        Response putResponse = conn.httpPut(entityUrl, updatedEntityXml.getBytes(), requestHeaders);
        if (putResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
            throw new Exception(putResponse.toString());
        }

        return putResponse;
    }

    /**
     * method to generate the test instance.
     * 
     * @param field
     *            the field name to update
     * @param value
     *            the new value to use
     * @return an xml that can be used to update an entity's single given field
     *         to given value
     */
    public String generatetestinstanceUpdateXml(final String field, final String value) {
        return "<Entity Type=\"test-instance\"><Fields><Field Name=\"" + field + "\"><Value>" + value
                + "</Value></Field></Fields></Entity>";
    }

}
