
package igt.test.automation.driver;

import org.openqa.selenium.Capabilities;

/** 
 * Class to handle the device related driver configurations.
 * 
 * * @author 
 */
public class DeviceDriverOptions {
    /** device name. */
    private String deviceName;

    /** path for the mobile app. */
    private String appPath;

    /** udid of device. */
    private String udid;
    
    /** mobile OS version. */
    private String platformVersion;
    
    /** Desired capabilities object aggregation to pass the capabilities specific to a device type. */
    private Capabilities desiredCapability;

    /** flag to indicate specific browser properties enabled or not. */
    private Boolean isSpecificDeviceDriverEnabled;
    
    /** appium server url. */
    private String appiumServerUrl;

    /**
     * default constructor.
     * 
     * @param deviceName - name of the device.
     * @param appPath - device path. 
     * @param udid  - udid of the device.
     * @param platformVersion - the platform in which you want to run.
     * @param desiredCapability - specific desired capabilities for Device.
     * @param isSpecificDeviceDriverEnabled - flag to determine specific desired 
     * capabilities are enabled or not.
     * @param appiumServerUrl - the appium server url.
     * */
    public DeviceDriverOptions(final String deviceName, final String appPath,
            final String udid, final String platformVersion, final Capabilities desiredCapability,
            final Boolean isSpecificDeviceDriverEnabled, final String appiumServerUrl) {
        super();
        this.deviceName = deviceName;
        this.appPath = appPath;
        this.udid = udid;
        this.platformVersion=platformVersion;
        this.desiredCapability = desiredCapability;
        this.isSpecificDeviceDriverEnabled = isSpecificDeviceDriverEnabled;
        this.appiumServerUrl = appiumServerUrl;
    }

    /** 
     * Gets the desiredCapability field. 
     * @return the desiredCapability 
     * */
    
    public Capabilities getDesiredCapability() {
        return desiredCapability;
    }

    
    /**
     * 
     * Sets the desiredCapability field. 
     * 
     * @param desiredCapability the desiredCapability to set */
    
    public void setDesiredCapability(final Capabilities desiredCapability) {
        this.desiredCapability = desiredCapability;
    }

    
    /**
     * Gets the isSpecificDeviceDriverEnabled field. 
     * 
     * @return isSpecificDeviceDriverEnabled 
     * */
    
    public Boolean getIsSpecificDeviceDriverEnabled() {
        return isSpecificDeviceDriverEnabled;
    }

    
    /**
     * Sets the isSpecificDeviceDriverEnabled field. 
     * 
     * @param isSpecificDeviceDriverEnabled the isSpecificDeviceDriverEnabled to set 
     * */
    
    public void setIsSpecificDeviceDriverEnabled(
            final Boolean isSpecificDeviceDriverEnabled) {
        this.isSpecificDeviceDriverEnabled = isSpecificDeviceDriverEnabled;
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

    
    /** * Gets the appPath field.
     * 
     * @return String - the appPath */
    
    public String getAppPath() {
        return appPath;
    }

    
    /** * Sets the appPath field. 
     * 
     * @param appPath the appPath to set */
    
    public void setAppPath(final String appPath) {
        this.appPath = appPath;
    }

    
    /** * Gets the udid field. 
     * 
     * @return String the udid */
    
    public String getUdid() {
        return udid;
    }

    
    /**
     * Sets the udid field. 
     * 
     * @param udid - the udid to set */
    
    public void setUdid(final String udid) {
        this.udid = udid;
    }
    
    /** * Gets the platformVersion field. 
     * 
     * @return String the platformVersion */
    
    public String getPlatformVersion() {
        return platformVersion;
    }
    
    /**
     * Sets the platformVersion field. 
     * 
     * @param platformVersion -  platformVersion field to set */
    
    public void setPlatformVersion(final String platformVersion) {
        this.platformVersion = platformVersion;
    }

    
    /** 
     * 
     * Gets the appiumServerUrl field. 
     * 
     * @return the appiumServerUrl 
     * */
    
    public String getAppiumServerUrl() {
        return appiumServerUrl;
    }

    
    /** 
     * Sets the appiumServerUrl field.
     * 
     * @param appiumServerUrl the appiumServerUrl to set. 
     * */
    
    public void setAppiumServerUrl(final String appiumServerUrl) {
        this.appiumServerUrl = appiumServerUrl;
    }



}

