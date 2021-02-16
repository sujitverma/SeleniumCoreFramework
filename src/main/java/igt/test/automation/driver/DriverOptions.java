
package igt.test.automation.driver;

import org.openqa.selenium.Capabilities;

/** 
 * Class to handle the driver configurations.
 * 
 * @author  */
public class DriverOptions {
    /** browser type. */
    private String browserType;

    /** browser version. */
    private String browserVersion;

    /** name of the platform. */
    private String platformName;

    /** flag to describe grid setup is enabled or not. */
    private boolean isGridEnabled;

    /** if grid enabled the config url for grid hub. */
    private String gridHubUrl;

    /** Desired capabilities object aggregation to pass the capabilities specific to a browser type. */
    private Capabilities desiredCapability;

    /** flag to indicate specific browser properties enabled or not. */
    private Boolean isSpecificBrowserDriverEnabled;
    
    /** browser type or device type choice.*/
    private String browserDeviceType;
    
    /** device browser enabled or not.*/
    private boolean isDeviceBrowserEnabled;
    
    /**
     * default constructor.
     * 
     * @param browserType
     *            - type of browser.
     * @param browserVersion
     *            - browser version.
     * @param platformName
     *            - platform details. {@code Platform}
     * @param isGridEnabled
     *            - flag to indicate Grid is enabled or not.
     * @param gridHubUrl
     *            - if grid is enabled, the the Hub config url.
     * @param desiredCapability
     *            - Capabilities that you want to pass to the browser.
     * @param isSpecificBrowserDriverEnabled
     *            - whether to configure browser specific capabilities or not.
     * @param browserDeviceType
     *            - whether to handle device or browser.
     * @param isDeviceBrowserEnabled
     *            - flag indicating if the device execution to happen on a
     *            native browser or not.
     */
    public DriverOptions(final String browserType, final String browserVersion,
            final String platformName, final boolean isGridEnabled,
            final String gridHubUrl, final Capabilities desiredCapability,
            final Boolean isSpecificBrowserDriverEnabled,
            final String browserDeviceType,
            final Boolean isDeviceBrowserEnabled) {
        super();
        this.browserType = browserType;
        this.browserVersion = browserVersion;
        this.platformName = platformName;
        this.isGridEnabled = isGridEnabled;
        this.gridHubUrl = gridHubUrl;
        this.desiredCapability = desiredCapability;
        this.isSpecificBrowserDriverEnabled = isSpecificBrowserDriverEnabled;
        this.browserDeviceType = browserDeviceType;
        this.isDeviceBrowserEnabled = isDeviceBrowserEnabled;
    }


    /** 
     * Gets the browserType field. 
     * 
     * @return String - the browserType */
    
    public String getBrowserType() {
        return browserType;
    }

    
    /** 
     * Sets the browserType field. 
     * 
     * @param browserType the browserType to set */
    
    public void setBrowserType(final String browserType) {
        this.browserType = browserType;
    }

    
    /** 
     * Gets the browserVersion field. 
     * 
     * @return String - the browserVersion */
    
    public String getBrowserVersion() {
        return browserVersion;
    }

    
    /** 
     * Sets the browserVersion field. 
     * 
     * @param browserVersion the browserVersion to set */
    
    public void setBrowserVersion(final String browserVersion) {
        this.browserVersion = browserVersion;
    }

    
    /** 
     * Gets the platformName field. 
     * 
     * @return the platformName */
    
    public String getPlatformName() {
        return platformName;
    }

    
    /** 
     * Sets the platformName field. 
     * 
     * @param platformName the platformName to set */
    
    public void setPlatformName(final String platformName) {
        this.platformName = platformName;
    }

    
    /** Gets the isGridEnabled field. 
     * 
     * @return boolean - the isGridEnabled */
    
    public boolean isGridEnabled() {
        return isGridEnabled;
    }

    
    /** 
     * Sets the isGridEnabled field. 
     * 
     * @param isGridEnabled - the isGridEnabled to set */
    
    public void setGridEnabled(final boolean isGridEnabled) {
        this.isGridEnabled = isGridEnabled;
    }

    
    /** 
     * Gets the gridHubUrl field. 
     * 
     * @return String - the gridHubUrl */
    
    public String getGridHubUrl() {
        return gridHubUrl;
    }

    
    /** 
     * Sets the gridHubUrl field. 
     * 
     * @param gridHubUrl the gridHubUrl to set */
    
    public void setGridHubUrl(final String gridHubUrl) {
        this.gridHubUrl = gridHubUrl;
    }


    
    /** 
     * Gets the desiredCapability field. 
     * 
     * @return the desiredCapability */
    
    public Capabilities getDesiredCapability() {
        return desiredCapability;
    }

    
    /** 
     * Sets the desiredCapability field. 
     * 
     * @param desiredCapability the desiredCapability to set */
    
    public void setDesiredCapability(final Capabilities desiredCapability) {
        this.desiredCapability = desiredCapability;
    }

    
    /** 
     * Gets the isSpecificBrowserDriverEnabled field. 
     * 
     * @return the isSpecificBrowserDriverEnabled */
    
    public Boolean getIsSpecificBrowserDriverEnabled() {
        return isSpecificBrowserDriverEnabled;
    }

    
    /** 
     * Sets the isSpecificBrowserDriverEnabled field. 
     * 
     * @param isSpecificBrowserDriverEnabled the isSpecificBrowserDriverEnabled to set */
    
    public void setIsSpecificBrowserDriverEnabled(
            final Boolean isSpecificBrowserDriverEnabled) {
        this.isSpecificBrowserDriverEnabled = isSpecificBrowserDriverEnabled;
    }


    
    /** 
     * Gets the browserDeviceType field. 
     * 
     * @return the browserDeviceType */
    
    public String getBrowserDeviceType() {
        return browserDeviceType;
    }


    
    /**
     * Sets the browserDeviceType field.
     * 
     * @param browserDeviceType the browserDeviceType to set */
    
    public void setBrowserDeviceType(final String browserDeviceType) {
        this.browserDeviceType = browserDeviceType;
    }


    
    /** 
     * Gets the isDeviceBrowserEnabled field. 
     * 
     * @return the isDeviceBrowserEnabled 
     * */
    
    public boolean isDeviceBrowserEnabled() {
        return isDeviceBrowserEnabled;
    }


    
    /** 
     * Sets the isDeviceBrowserEnabled field. 
     * 
     * @param isDeviceBrowserEnabled the isDeviceBrowserEnabled to set 
     * */
    
    public void setDeviceBrowserEnabled(final boolean isDeviceBrowserEnabled) {
        this.isDeviceBrowserEnabled = isDeviceBrowserEnabled;
    }

    
    
 
    
}
