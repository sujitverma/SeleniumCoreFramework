
package igt.test.automation.alm.functionlibrary;

/**
 * Class to store ALM run information.
 * 
 * @author 
 *
 */
public class ALMRun {

    /** ALM run id. */
    private String runId;

    /** ALM run name. */
    private String runName;

    /** ALM Test case id for which this run created. */
    private String runTestId;

    /** ALM Test instance id for which this run created. */
    private String runTestInstanceId;

    public String getRunId() {
        return runId;
    }

    public void setRunId(final String runId) {
        this.runId = runId;
    }

    public String getRunName() {
        return runName;
    }

    public void setRunName(final String runName) {
        this.runName = runName;
    }

    public String getRunTestId() {
        return runTestId;
    }

    public void setRunTestId(final String runTestId) {
        this.runTestId = runTestId;
    }

    public String getRunTestInstanceId() {
        return runTestInstanceId;
    }

    public void setRunTestInstanceId(final String runTestInstanceId) {
        this.runTestInstanceId = runTestInstanceId;
    }
}
