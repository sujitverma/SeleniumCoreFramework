package igt.test.automation.alm.functionlibrary;

/**
 * Class to keep the ALM Login related information.
 * 
 * @author 
 *
 */
public class ALMLogin {

    /** ALM Host url. */
    private String host;

    /** User Name require to login to ALM. */
    private String userName;

    /** Password require to login to ALM. */
    private String password;

    /** ALM domain which need to access. */
    private String domain;

    /** ALM Project which need to be access. */
    private String project;

    /** ALM Test Lab folder name in which new Test Set will be created. */
    private String testSetParentFolder;

    /** ALM Integration switch to on and off the ALM integration feature. */
    private String almIntegrationSwitch;

    public String getAlmIntegrationSwitch() {
        return almIntegrationSwitch;
    }

    public void setAlmIntegrationSwitch(final String almIntegrationSwitch) {
        this.almIntegrationSwitch = almIntegrationSwitch;
    }

    public String getTestSetParentFolder() {
        return testSetParentFolder;
    }

    public void setTestSetParentFolder(final String testSetParentFolder) {
        this.testSetParentFolder = testSetParentFolder;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public String getProject() {
        return project;
    }

    public void setProject(final String project) {
        this.project = project;
    }
}
