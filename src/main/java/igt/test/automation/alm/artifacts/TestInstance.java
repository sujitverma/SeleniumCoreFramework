
package igt.test.automation.alm.artifacts;

/** Create Test Instance Information java object. */
public class TestInstance {

    /* Test Instance Id. */
    private String id;

    /* Test Instance Name */
    private String name;

    /* Test Instance actual test case id */
    private String testCaseId;

    /* Test set Id in which this test instance added */
    private String testSetId;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(final String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getTestSetId() {
        return testSetId;
    }

    public void setTestSetId(final String testSetId) {
        this.testSetId = testSetId;
    }

}
