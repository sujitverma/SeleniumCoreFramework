
package igt.test.automation.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Class to handle the android actions.
 *
 */
public final class AndroidMobileUtil {

    /** Log4j Logger. */
    private static final Logger LOG = LogManager
            .getLogger(AndroidMobileUtil.class);

    /** NoSuchElement Exception message. */
    private static final String NO_SUCH_ELEMENT_EXCEPTION = "No Such Element Exception :";

    /** Integer 2. */
    private static final int INTEGER_0 = 0;

    /** Integer 2. */
    private static final int INTEGER_2 = 2;

    /** Integer 0.90 . */
    private static final double INTEGER_POINT_90 = 0.90;

    /** Integer 0.10 . */
    private static final double INTEGER_POINT_10 = 0.10;

    /** Integer 0.5 . */
    private static final double INTEGER_POINT_5 = 0.5;

    /** Integer 0.3 . */
    private static final double INTEGER_POINT_3 = 0.3;

    private AndroidMobileUtil() {
        throw new IllegalStateException("Android Utility class");
    }

    /**
     * 
     * Method to tap the elements by coordinates.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param xOffset
     *            is the x coordinates of the element
     * @param yOffset
     *            is the y coordinates of the element
     * @param timeInMillis
     *            is the duration of the tap in milliseconds
     */
    public static void tapElementByCoordinates(final AndroidDriver<?> driver,
            final int xOffset, final int yOffset, final long timeInMillis) {
        new TouchAction<>(driver).tap(PointOption.point(xOffset, yOffset))
                .waitAction(WaitOptions
                        .waitOptions(Duration.ofMillis(timeInMillis)))
                .perform();
    }

    /**
     * Method to Tap to an element for a given duration.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * 
     * @param mobileElement
     *            is {@link AndroidElement}
     * @param timeInMillis
     *            is the duration of the tap in milliseconds
     */
    public static void tapByElement(final AppiumDriver<?> driver,
            final MobileElement mobileElement, final long timeInMillis) {
        new TouchAction<>(driver)
                .tap(TapOptions.tapOptions()
                        .withElement(ElementOption.element(mobileElement)))
                .waitAction(WaitOptions
                        .waitOptions(Duration.ofMillis(timeInMillis)))
                .perform();
    }

    /**
     * Method to press the elements by coordinates.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param xOffset
     *            is the x coordinates of the element
     * @param yOffset
     *            is the y coordinates of the element
     * @param timeInSeconds
     *            is the duration in seconds
     */
    public static void pressElementByCoordinates(final AppiumDriver<?> driver,
            final int xOffset, final int yOffset, final long timeInSeconds) {
        new TouchAction<>(driver).press(PointOption.point(xOffset, yOffset))
                .waitAction(WaitOptions
                        .waitOptions(Duration.ofSeconds(timeInSeconds)))
                .release().perform();
    }

    /**
     * Method to perform multi-touch action.MultiTouch actions allow for
     * multiple touches to happen at the same time.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * 
     * @param androidElement
     *            is {@link AndroidElement}
     */
    public static void multiTouchByElement(final AndroidDriver<?> driver,
            final AndroidElement androidElement) {
        TouchAction<?> press = new TouchAction<>(driver)
                .press(ElementOption.element(androidElement))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .release();

        new MultiTouchAction(driver).add(press).perform();
    }

    /**
     * 
     * Method to swipe by Elements.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param startElement
     *            is the starting {@link AndroidElement}
     * @param endElement
     *            is the end {@link AndroidElement}
     * @param timeInMilliSeconds
     *            is the time in milliseconds
     */
    public static void swipeByElements(final AndroidDriver<?> driver,
            final AndroidElement startElement, final AndroidElement endElement,
            final long timeInMilliSeconds) {
        int startX = startElement.getLocation().getX()
                + (startElement.getSize().getWidth() / INTEGER_2);
        int startY = startElement.getLocation().getY()
                + (startElement.getSize().getHeight() / INTEGER_2);

        int endX = endElement.getLocation().getX()
                + (endElement.getSize().getWidth() / INTEGER_2);
        int endY = endElement.getLocation().getY()
                + (endElement.getSize().getHeight() / INTEGER_2);

        new TouchAction<>(driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions
                        .waitOptions(Duration.ofMillis(timeInMilliSeconds)))
                .moveTo(PointOption.point(endX, endY)).release().perform();
    }

    /**
     * Method to perform right swipe.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param androidElement
     *            is {@link AndroidElement}
     */
    public static void swipeRight(final AndroidDriver<?> driver,
            final AndroidElement androidElement) {
        Point point = androidElement.getLocation();
        Dimension dimension = driver.manage().window().getSize();
        int screenWidth = (int) (dimension.width * INTEGER_POINT_90);

        int endY = INTEGER_0;
        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction
                .press(PointOption.point(
                        (int) (point.getX()
                                + (androidElement.getSize().getWidth()
                                        * INTEGER_POINT_10)),
                        androidElement.getCenter().getY()))
                .moveTo(PointOption.point((int) (screenWidth
                        - (point.getX() + (androidElement.getSize().getWidth()
                                * INTEGER_POINT_10))),
                        endY))
                .release().perform();

    }

    /**
     * Method to perform right left.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param androidElement
     *            is {@link AndroidElement}
     */
    public static void swipeLeft(final AndroidDriver<?> driver,
            final AndroidElement androidElement) {
        Point point = androidElement.getLocation();
        Dimension size = driver.manage().window().getSize();

        int screenWidth = (int) (size.width * INTEGER_POINT_10);
        int endY = INTEGER_0;
        TouchAction<?> action = new TouchAction<>(driver);
        action.press(PointOption.point((int) (point.getX()
                + (androidElement.getSize().getWidth() * INTEGER_POINT_90)),
                androidElement.getCenter().getY()))
                .moveTo(PointOption.point((int) (screenWidth
                        - (point.getX() + (androidElement.getSize().getWidth()
                                * INTEGER_POINT_90))),
                        endY))
                .release().perform();
    }

    /**
     * Method to perform swipe up.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param androidElement
     *            is {@link AndroidElement}
     */
    public static void swipeUp(final AndroidDriver<?> driver,
            final AndroidElement androidElement) {
        Dimension size = driver.manage().window().getSize();
        int screenHeight = (int) (size.height * INTEGER_POINT_10);
        int endX = INTEGER_0;
        TouchAction<?> action = new TouchAction<>(driver);
        action.press(PointOption.point(androidElement.getCenter().getX(),
                androidElement.getCenter().getY()))
                .moveTo(PointOption.point(endX,
                        screenHeight - androidElement.getCenter().getY()))
                .release().perform();

    }

    /**
     * Method to perform swipe down.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param androidElement
     *            is {@link AndroidElement}
     */
    public static void swipeDown(final AppiumDriver<?> driver,
            final AndroidElement androidElement) {
        Dimension size = driver.manage().window().getSize();
        int screenHeight = (int) (size.height * INTEGER_POINT_90);
        int endX = INTEGER_0;
        TouchAction<?> action = new TouchAction<>(driver);
        action.press(PointOption.point(androidElement.getCenter().getX(),
                androidElement.getCenter().getY()))
                .moveTo(PointOption.point(endX,
                        screenHeight - androidElement.getCenter().getY()))
                .release().perform();

    }

    /**
     * Method to LongPress on a {@link WebElement}.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param androidElement
     *            is {@link AndroidElement}
     */
    public static void longPress(final AppiumDriver<?> driver,
            final AndroidElement androidElement) {
        try {
            TouchAction<?> touchAction = new TouchAction<>(driver);
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withElement(ElementOption.element(androidElement));
            touchAction.longPress(longPressOptions).release().perform();
        } catch (NoSuchElementException e) {

            LOG.info(NO_SUCH_ELEMENT_EXCEPTION +"{0}", e);
        }
    }

    /**
     * Method to long press on specific x,y coordinates.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param xOffset
     *            is x Offset
     * @param yOffset
     *            is y Offset
     */
    public static void longPress(final AppiumDriver<?> driver,
            final int xOffset, final int yOffset) {
        TouchAction<?> touch = new TouchAction<>(driver);
        PointOption<?> pointOption = new PointOption<>();
        pointOption.withCoordinates(xOffset, yOffset);
        touch.longPress(pointOption).release().perform();

    }

    /**
     * Method to long press on element with absolute coordinates.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * @param androidElement
     *            is {@link AndroidElement}
     * @param xOffset
     *            is x Offset
     * @param yOffset
     *            is y Offset
     */
    public static void longPress(final AppiumDriver<?> driver,
            final AndroidElement androidElement, final int xOffset,
            final int yOffset) {
        try {
            TouchAction<?> touch = new TouchAction<>(driver);
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions
                    .withPosition(new PointOption<>().withCoordinates(xOffset,
                            yOffset))
                    .withElement(ElementOption.element(androidElement));
            touch.longPress(longPressOptions).release().perform();
        } catch (NoSuchElementException e) {

            LOG.info(NO_SUCH_ELEMENT_EXCEPTION +"{0}", e);
        }
    }

    /**
     * Method to press back key in android.
     * 
     * @param driver
     *            {@link AndroidDriver}
     */
    public static void back(final AndroidDriver<?> driver) {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    /**
     * Method to scroll down on screen from java-client 6.
     *
     * @param swipeTimes
     *            number of times swipe operation should work
     * @param durationForSwipe
     *            time duration of a swipe operation
     * @param driver
     *            {@link AndroidDriver}
     */
    public static void scrollDown(final int swipeTimes,
            final int durationForSwipe, final AppiumDriver<?> driver) {
        Dimension dimension = driver.manage().window().getSize();

        for (int i = 0; i <= swipeTimes; i++) {
            int start = (int) (dimension.getHeight() * INTEGER_POINT_5);
            int end = (int) (dimension.getHeight() * INTEGER_POINT_3);
            int x = (int) (dimension.getWidth() * INTEGER_POINT_5);

            new TouchAction<>(driver).press(PointOption.point(x, start))
                    .moveTo(PointOption.point(x, end))
                    .waitAction(WaitOptions
                            .waitOptions(Duration.ofMillis(durationForSwipe)))
                    .release().perform();
        }
    }

    /**
     * Method to open the notifications in android.
     * 
     * @param driver
     *            {@link AndroidDriver}
     */
    public static void openNotifications(final AppiumDriver<?> driver) {
        if (driver instanceof AndroidDriver<?>) {
            ((AndroidDriver<?>) driver).openNotifications();
        }
    }

    /**
     * Method to switch the native apps in android.
     * 
     * @param appPackage
     *            is the appPackage for android
     * @param appActivity
     *            is the appActivity for android
     * @return {@link Activity}
     */
    public static Activity switchToNativeApp(final String appPackage,
            final String appActivity) {
        Activity activity = new Activity(appPackage, appActivity);
        activity.setStopApp(false);
        return activity;
    }

    /**
     * Method to Press an element for a given duration.
     * 
     * @param driver
     *            is {@link AndroidDriver}
     * 
     * @param mobileElement
     *            is {@link AndroidElement}
     * @param timeInMillis
     *            is the duration of the tap in milliseconds
     */
    public static void pressByElement(final AppiumDriver<?> driver,
            final MobileElement mobileElement, final long timeInMillis) {
        new TouchAction<>(driver)
                .tap(TapOptions.tapOptions()
                        .withElement(ElementOption.element(mobileElement)))
                .waitAction(WaitOptions
                        .waitOptions(Duration.ofMillis(timeInMillis)))
                .perform();
    }

    /**
     * Method to press Home key in android.
     * 
     * @param driver
     *            {@link AndroidDriver}
     */
    public static void pressHomeKey(final AndroidDriver<?> driver) {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }
}
