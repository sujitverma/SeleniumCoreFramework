
package igt.test.automation.driver;

import org.openqa.selenium.Capabilities;

/**
 * Class to handle the windows desktop driver configurations.
 *
 * @author 
 */
public class DesktopOptions {
    /**
     * name of the platform.
     */
    private String platformName;

    /**
     * Desired capabilities object aggregation to pass the capabilities specific to the windows desktop type.
     */
    private Capabilities desiredCapability;

    /** device name. */
    private String deviceName;

    /** platform version. */
    private String platformVersion;

    /** windows. */
    private static final String PLATFORM = "Windows";
    /** WindowsPC. */
    private static final String DEVICE = "WindowsPC";
    /** Windows10. */
    private static final String PLATFORM_VERSION = "1.0";

    /**
     * Default constructor to set the Windows Desktop application properties.
     * @param desiredCapability - desired capability object to hold windows desktop application config.
     */
    public DesktopOptions(final Capabilities desiredCapability) {
        super();
        this.deviceName = DEVICE;
        this.platformName = PLATFORM;
        this.platformVersion = PLATFORM_VERSION;
        this.desiredCapability = desiredCapability;
    }

    /**
     * Gets the platformName field.
     *
     * @return the platformVersion
     */
    public String getPlatformVersion() {
        return platformVersion;
    }

    /**
     * Sets the platformName field.
     *
     * @param platformVersion the version to set
     */

    public void setPlatformVersion(final String platformVersion) {
        this.platformVersion = platformVersion;
    }


    /**
     * Gets the platformName field.
     *
     * @return the platformName
     */

    public String getPlatformName() {
        return platformName;
    }


    /**
     * Sets the platformName field.
     *
     * @param platformName the platformName to set
     */

    public void setPlatformName(final String platformName) {
        this.platformName = platformName;
    }


    /**
     * Gets the desiredCapability field.
     *
     * @return the desiredCapability
     */

    public Capabilities getDesiredCapability() {
        return desiredCapability;
    }


    /**
     * Sets the desiredCapability field.
     *
     * @param desiredCapability the desiredCapability to set
     */

    public void setDesiredCapability(final Capabilities desiredCapability) {
        this.desiredCapability = desiredCapability;
    }

    /**
     * Gets the deviceName field.
     *  @return String the deviceName */

    public String getDeviceName() {
        return deviceName;
    }


    /** * Sets the deviceName field.
     *
     * @param deviceName - the deviceName to set */

    public void setDeviceName(final String deviceName) {
        this.deviceName = deviceName;
    }

}
