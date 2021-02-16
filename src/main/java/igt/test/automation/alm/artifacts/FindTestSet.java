
package igt.test.automation.alm.artifacts;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import igt.test.automation.alm.functionlibrary.APIManager;
import igt.test.automation.alm.response.pojo.Entities;
import igt.test.automation.alm.response.pojo.Field;
import igt.test.automation.util.ApiUtil;
import io.restassured.response.Response;

/** Find the Test Set Parent Directory Id. */
public class FindTestSet {
    /** The Log4j logger. */
    private static final Logger LOG = LogManager.getLogger(FindTestSet.class);

    /**
     * Function to find out the Test Set director Id which will be use.
     * 
     * @param host
     *            - ALM Host Url. Example : https://localhost:8080/qcbin
     * @param domain
     *            - ALM Domain Name
     * @param project
     *            - ALM Project name
     * @param headers
     *            - ALM Rest API headers for Json.
     * @param cookies
     *            - ALM cookies which recived from ALM Sign in.
     * @param testLabFolderName
     *            - ALM Test Lab Directory Name whose Id need to be fetch
     * @return ALM test lab folder Id as String
     * 
     * @author 
     */
    public String findTestSetFolderParentId(final String host,
            final String domain, final String project,
            final Map<String, String> headers,
            final Map<String, String> cookies, final String testLabFolderName) {
        String testSetFolderId = null;
        try {
            String getTestSetsAPIUrl = host + "/rest/domains/" + domain
                    + "/projects/" + project + "/test-set-folders/";

            Response responseTestSets = ApiUtil.getRequest(getTestSetsAPIUrl,
                    headers, cookies);
            Entities testSetsObj = (Entities) APIManager.convertFromJson(
                    responseTestSets.asString(), Entities.class);
            Integer testSetCount = testSetsObj.getTotalResults();
            long testSetIndex = 0;
            boolean testSetFound = false;
            List<Field> fieldsObj = null;
            for (int i = 0; i < testSetCount; i++) {
                fieldsObj = testSetsObj.getEntities().get(i).getFields();
                int fieldsSize = fieldsObj.size();
                for (int j = 0; j < fieldsSize; j++) {
                    if ("name".equals(fieldsObj.get(j).getName())) {
                        String nameValue = fieldsObj.get(j).getValues().get(0)
                                .getValue();
                        if (nameValue != null) {
                            if (nameValue.equals(testLabFolderName)) {
                                LOG.info("Test Set Folder index id:- " + i);
                                testSetIndex = i;
                                testSetFound = true;
                                break;
                            }
                        }
                    }
                }
                if (testSetFound) {
                    break;
                }
            }

            LOG.info("Test Set index found:-  " + testSetIndex);
            for (int i = 0; i < testSetsObj.getEntities()
                    .get((int) testSetIndex).getFields().size(); i++) {
                String name = fieldsObj.get(i).getName();
                LOG.info(name);
                if ("id".equals(name)) {
                    testSetFolderId = fieldsObj.get(i).getValues().get(0)
                            .getValue();
                    break;
                }
            }
            LOG.info("final test set id is : - " + testSetFolderId);
        } catch (Exception e) {
            LOG.error(
                    "Exception while finding test set parent directory:- " + e);
        }
        return testSetFolderId;
    }
}
