
package igt.test.automation.mobile;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import igt.test.automation.mobile.constants.TouchActionDirectionEnum;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

/**
 * Utility library to handle all the iOS operations.
 * 
 * @author 
 */
public final class IOSMobileUtil {
    
    /** Log4j Logger. */
    private static final Logger LOG = LogManager
            .getLogger(IOSMobileUtil.class);
    
    /** element. */
    private static final String ELEMENT = "element";

    /** duration. */
    private static final String DURATION = "duration";

    /** bundleId. */
    private static final String BUNDLE_ID = "bundleId";

    /** mobile: alert. */
    private static final String ALERT = "mobile: alert";

    /** action. */
    private static final String ACTION = "action";

    /** mobile: pressButton. */
    private static final String PRESS_BUTTON = "mobile: pressButton";

    /** scale factor 0.99. */
    private static final double SCALE_FACTOR_LESS_THAN_ONE = 0.99;

    /** scale factor 10. */
    private static final double SCALE_FACTOR_TEN = 10;

    /** velocity factor -10.00. */
    private static final double VELOCITY_FACTOR = -10.00;

    /** offset factor 0.15. */
    private static final double OFFSET_FACTOR = 0.15;
    

    private IOSMobileUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Method to perform scrolling to any direction.
     * 
     * @param driver
     *            {@link IOSDriver}
     * @param scrollDirection
     *            is the direction you want to scroll e.g. up,down
     */
    public static void scroll(final IOSDriver<MobileElement> driver,
            final String scrollDirection) {
        executeMobileGestureScript(driver, scrollDirection, "mobile: scroll");
    }

    /**
     * Helper method to execute the Mobile gestures.
     * 
     * @param driver
     *            {@link IOSDriver}
     * @param scrollDirection
     *            is the direction you want to scroll e.g. up,down
     * @param executeScriptKey
     *            key for the execute script
     */
    protected static void executeMobileGestureScript(
            final IOSDriver<MobileElement> driver, final String scrollDirection,
            final String executeScriptKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("direction", scrollDirection);
        driver.executeScript(executeScriptKey, map);
    }

    /**
     * Method to perform tap on the specified iOS element.
     * 
     * @param driver
     *            {@link IOSDriver}
     * @param element
     *            {@link MobileElement}
     * @param xCoordinates
     *            - signifies the x coordinate at which to click relative to the
     *            element parameter.
     * @param yCoordinates
     *            - signifies the y coordinate at which to click relative to the
     *            element parameter.
     */
    public static void tap(final IOSDriver<MobileElement> driver,
            final MobileElement element, final int xCoordinates,
            final int yCoordinates) {
        Map<String, Object> map = new HashMap<>();
        map.put(ELEMENT, element.getId());
        map.put("x", xCoordinates);
        map.put("y", yCoordinates);
        driver.executeScript("mobile: tap", map);
    }

    /**
     * Method to perform tap on the specified iOS element.
     * 
     * @param driver
     *            {@link IOSDriver}
     * @param element
     *            {@link MobileElement}
     */
    public static void tap(final IOSDriver<MobileElement> driver,
            final MobileElement element) {
        Map<String, Object> map = new HashMap<>();
        map.put(ELEMENT, element.getId());
        map.put("x", 0);
        map.put("y", 0);
        driver.executeScript("mobile: tap", map);
    }

    /**
     * Method to perform two finger tap on mobile screen. A two finger tap is a
     * single tap using two fingers.
     * 
     * @param driver
     *            {@link IOSDriver}
     * @param element
     *            {@link MobileElement}
     */
    public static void twoFingerTap(final IOSDriver<MobileElement> driver,
            final MobileElement element) {
        Map<String, Object> map = new HashMap<>();
        map.put(ELEMENT, element.getId());
        driver.executeScript("mobile: twoFingerTap", map);
    }

    /**
     * This is a default swipe which defaults to the entire application.
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param swipeDirection
     *            the direction you want to swipe up,down,left,right
     */
    public static void swipe(final IOSDriver<MobileElement> driver,
            final String swipeDirection) {
        executeMobileGestureScript(driver, swipeDirection, "mobile: swipe");
    }

    /**
     * Performs a scroll down to scroll view contents down. This method does the
     * scroll to the bottom most content that is visible in the given screen.
     * 
     * @param driver
     *            {@link IOSDriver}
     */
    public static void swipeDown(final IOSDriver<MobileElement> driver) {
        swipe(driver, TouchActionDirectionEnum.UP.name());
    }

    /**
     * Performs a scroll up to scroll view contents up. This method does the
     * scroll to the top most content that is visible in the given screen.
     * 
     * @param driver
     *            {@link IOSDriver}
     */
    public static void swipeUp(final IOSDriver<MobileElement> driver) {
        swipe(driver, TouchActionDirectionEnum.DOWN.name());
    }

    /**
     * pinchClose or ZoomOut.
     * 
     * @param driver
     *            {@link IOSDriver}
     * @deprecated as currently this operation is unsupported
     */
    @Deprecated public static void pinch(final IOSDriver<MobileElement> driver) {
        Map<String, Object> args = new HashMap<>();
        args.put("scale", SCALE_FACTOR_LESS_THAN_ONE);
        args.put("velocity", VELOCITY_FACTOR);
        driver.executeScript("mobile: pinch", args);
    }

    /**
     * pinchOpen or ZoomIn.
     * 
     * @param driver
     *            {@link IOSDriver}
     */
    public static void zoom(final IOSDriver<MobileElement> driver) {
        Map<String, Object> args = new HashMap<>();
        args.put("scale", SCALE_FACTOR_TEN);
        args.put("velocity", 2);
        driver.executeScript("mobile: pinch", args);
    }

    /**
     * allows to tap on an element and holding the finger for the specified
     * duration.
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param element
     *            {@code MobileElement}
     * @param timeToHoldTheTouch
     *            how many seconds you want the touch to be held.
     */
    public static void touchAndHold(final IOSDriver<MobileElement> driver,
            final MobileElement element, final long timeToHoldTheTouch) {
        Map<String, Object> args = new HashMap<>();
        args.put(ELEMENT, element.getId());
        args.put(DURATION, Duration.ofSeconds(timeToHoldTheTouch));
        driver.executeScript("mobile: touchAndHold", args);
    }

    /**
     * allows to tap on an element and holding the finger for the specified
     * duration with the Coordinates.
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param element
     *            {@code MobileElement}
     * @param timeToHoldTheTouch
     *            how many seconds you want the touch to be held.
     * @param xCoordinates
     *            - signifies the x coordinate at which to click relative to the
     *            element parameter.
     * @param yCoordinates
     *            - signifies the y coordinate at which to click relative to the
     *            element parameter.
     */
    public static void touchAndHold(final IOSDriver<MobileElement> driver,
            final MobileElement element, final long timeToHoldTheTouch,
            final int xCoordinates, final int yCoordinates) {
        Map<String, Object> args = new HashMap<>();
        args.put(ELEMENT, element.getId());
        args.put("x", xCoordinates);
        args.put("y", yCoordinates);
        args.put(DURATION, timeToHoldTheTouch);
        driver.executeScript("mobile: touchAndHold", args);
    }

    /**
     * performs drag-and-drop gesture for the specified duration with the
     * Coordinates.
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param element
     *            {@code MobileElement}
     * @param timeToHoldTheTouch
     *            how many seconds you want the touch to be held.
     * @param fromX
     *            - signifies the x coordinate of the start position.
     * @param fromY
     *            - signifies the x coordinate of the start position.
     * @param toX
     *            - signifies the x coordinate of the end position.
     * @param toY
     *            - signifies the y coordinate of the end position.
     */
    public static void dragAndDrop(final IOSDriver<MobileElement> driver,
            final MobileElement element, final long timeToHoldTheTouch,
            final int fromX, final int fromY, final int toX, final int toY) {
        Map<String, Object> args = new HashMap<>();
        args.put(ELEMENT, element.getId());
        args.put(DURATION, timeToHoldTheTouch);
        args.put("fromX", fromX);
        args.put("fromY", fromY);
        args.put("toX", toX);
        args.put("toY", toY);
        driver.executeScript("mobile: dragFromToForDuration", args);
    }

    /**
     * Launches an existing application on the device under test. If the
     * application is already running then it will be brought to the foreground.
     * 
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param bundleIdentifier
     *            The bundle identifier of the application, which is going to be
     *            executed.
     */
    public static void launchApp(final IOSDriver<MobileElement> driver,
            final String bundleIdentifier) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(BUNDLE_ID, bundleIdentifier);
        driver.executeScript("mobile: launchApp", args);
    }

    /**
     * Activates an existing application on the device under test and moves it
     * to the foreground. The application should be already running in order to
     * activate it. The call is ignored if the application is already in
     * foreground.
     * 
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param bundleIdentifier
     *            The bundle identifier of the application, which is going to be
     *            executed.
     */
    public static void activateApp(final IOSDriver<MobileElement> driver,
            final String bundleIdentifier) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(BUNDLE_ID, bundleIdentifier);
        driver.executeScript("mobile: activateApp", args);
    }

    /**
     * Installs given application to the device under test. If the same
     * application is already installed then it's going to be installed over the
     * existing one, which allows you to test upgrades. Be careful while
     * reinstalling the main application under test: make sure that terminateApp
     * has been called first, otherwise WebDriverAgent will detect the state as
     * a potential crash of the application.
     * 
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param appPath
     *            The path to an existing .ipa/.app file on the server file
     *            system, zipped .app file or an URL pointing to a remote
     *            .ipa/.zip file. For e.g. {@code http://example.com/myapp.ipa}
     *            or {@code https://example.com/myapp.zip}
     */
    public static void installApp(final IOSDriver<MobileElement> driver,
            final String appPath) {
        Map<String, Object> params = new HashMap<>();
        params.put("app", appPath);
        driver.executeScript("mobile: installApp", params);

    }

    /**
     * Terminates an existing application on the device. If the application is
     * not running then the returned result will be {@code false}, otherwise
     * {@code true}
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param bundleIdentifier
     *            The bundle identifier of the application, which is going to be
     *            executed.
     * @return {@link Boolean}
     */
    public static boolean terminateApp(final IOSDriver<MobileElement> driver,
            final String bundleIdentifier) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(BUNDLE_ID, bundleIdentifier);
        return (Boolean) driver.executeScript("mobile: terminateApp", args);
    }

    /**
     * Verifies whether the application with given bundle identifier is
     * installed on the device. Returns true or false.
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param bundleIdentifier
     *            The bundle identifier of the application, which is going to be
     *            executed.
     * @return {@link Boolean}
     */
    public static boolean isAppInstalled(final IOSDriver<MobileElement> driver,
            final String bundleIdentifier) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(BUNDLE_ID, bundleIdentifier);
        return (Boolean) driver.executeScript("mobile: isAppInstalled", args);
    }

    /**
     * Queries the state of an existing application on the device. There are
     * five possible application states. 0: The current application state cannot
     * be determined/is unknown 1: The application is not running 2: The
     * application is running in the background and is suspended 3: The
     * application is running in the background and is not suspended 4: The
     * application is running in the foreground
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param bundleIdentifier
     *            The bundle identifier of the application, which is going to be
     *            executed.
     * @return long
     */
    public static long queryAppState(final IOSDriver<MobileElement> driver,
            final String bundleIdentifier) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(BUNDLE_ID, bundleIdentifier);
        return (Long) driver.executeScript("mobile: queryAppState", args);
    }

    /**
     * Performs selection of the next or previous value in the given picker
     * wheel element.
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param pickerElement
     *            - the pickerWheel WebElement that you want to operate on.
     * @param orderOfSelection
     *            - next or previous
     */
    public static void selectValueInTheDatePickerWheel(
            final IOSDriver<MobileElement> driver,
            final WebElement pickerElement, final String orderOfSelection) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("order", orderOfSelection);
        params.put("offset", OFFSET_FACTOR);
        params.put(ELEMENT, ((RemoteWebElement) pickerElement).getId());
        driver.executeScript("mobile: selectPickerWheelValue", params);
    }

    /**
     * to switch to an alert appearing and accept it.
     * 
     * @param driver
     *            {@code IOSDriver}
     */
    public static void acceptAlert(final IOSDriver<MobileElement> driver) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(ACTION, "accept");
        driver.executeScript(ALERT, args);
    }

    /**
     * to switch to an alert appearing and dimiss it.
     * 
     * @param driver
     *            {@code WebDriver}
     */
    public static void dismissAlert(final IOSDriver<MobileElement> driver) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(ACTION, "dismiss");
        driver.executeScript(ALERT, args);
    }

    /**
     * to switch to an alert appearing and dismiss it.
     * 
     * @param driver
     *            {@code WebDriver}
     * @return String
     */
    public static String getTextFromAlert(final WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    /**
     * method to set the iOS permissions.
     * 
     * @param appBundleId the app bundle id
     * @param permissionName the permission you want to allow
     * @param permissionValue the permission value you want to set
     * @return {@code Capabilities}
     */
    public static Capabilities setPermissions(final String appBundleId,
            final String permissionName, final String permissionValue) {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        String permissionValueTemplate = "{\"%bundle_id%\": {\"%permission_name%\": \"%permission_value%\"}}";

        permissionValueTemplate = permissionValueTemplate
                .replaceAll("%bundle_id%", appBundleId);
        permissionValueTemplate = permissionValueTemplate
                .replaceAll("%permission_name%", permissionName);
        permissionValueTemplate = permissionValueTemplate
                .replaceAll("%permission_value%", permissionValue);
        capabilities.setCapability("permissions", permissionValueTemplate);

        return capabilities;

    }

    /**
     * method to set the iOS permissions.
     * 
     * @param bundleId the app bundle id
     * @param aMap the Map<String, String>
     * @return String
     */
    public static String setPermissions(final String bundleId,
            final Map<String, String> aMap) {

        StringBuilder sb = new StringBuilder();
        Gson gson = new Gson();
        String jsonString = gson.toJson(aMap);
        return sb.append("{\"").append(bundleId).append("\":")
                .append(jsonString).append("}").toString();

    }

    /**
     * Method to perform pressing the physical Home Button.
     * 
     * @param driver
     *            {@code IOSDriver}
     */
    public static void pressHomeButton(final IOSDriver<MobileElement> driver) {
        driver.executeScript(PRESS_BUTTON, ImmutableMap.of("name", "home"));

    }

    /**
     * Method to perform pressing the physical VolumeUp Button.
     * 
     * @param driver
     *            {@code IOSDriver}
     */
    public static void pressVolumeUpButton(
            final IOSDriver<MobileElement> driver) {
        driver.executeScript(PRESS_BUTTON, ImmutableMap.of("name", "volumeup"));

    }

    /**
     * Method to perform pressing the physical VolumeDown Button.
     * 
     * @param driver
     *            {@code IOSDriver}
     */
    public static void pressVolumeDownButton(
            final IOSDriver<MobileElement> driver) {
        driver.executeScript(PRESS_BUTTON,
                ImmutableMap.of("name", "volumedown"));

    }

    /**
     * Method to perform on clicking the Alert with the provided alert label.
     * 
     * @param driver
     *            {@code IOSDriver}
     * @param alertLabel
     *            alert's label
     */
    public static void clickAlert(final IOSDriver<MobileElement> driver,
            final String alertLabel) {
        HashMap<String, String> args = new HashMap<>();
        args.put(ACTION, "accept");
        args.put("buttonLabel", alertLabel);
        driver.executeScript(ALERT, args);

    }

}
