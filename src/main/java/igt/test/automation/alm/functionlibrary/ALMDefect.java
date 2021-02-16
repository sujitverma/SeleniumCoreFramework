
package igt.test.automation.alm.functionlibrary;

/**
 * Class to store ALM defect information which will use to create defects.
 * 
 * @author 
 *
 */
public class ALMDefect {

    /**
     * It have Test Set up information which might require for defect reproduce.
     */
    private String testSetUpInfo;

    /** Project functional component for which defect raised. */
    private String functionalComponent;

    /** Project Testing phase in which defect raised. */
    private String testingPhase;

    /** Dev Team who create this application. */
    private String devTeamName;

    public String getDevTeamName() {
        return devTeamName;
    }

    public void setDevTeamName(final String devTeamName) {
        this.devTeamName = devTeamName;
    }

    public String getTestSetUpInfo() {
        return testSetUpInfo;
    }

    public void setTestSetUpInfo(final String testSetUpInfo) {
        this.testSetUpInfo = testSetUpInfo;
    }

    public String getFunctionalComponent() {
        return functionalComponent;
    }

    public void setFunctionalComponent(final String functionalComponent) {
        this.functionalComponent = functionalComponent;
    }

    public String getTestingPhase() {
        return testingPhase;
    }

    public void setTestingPhase(final String testingPhase) {
        this.testingPhase = testingPhase;
    }

}
