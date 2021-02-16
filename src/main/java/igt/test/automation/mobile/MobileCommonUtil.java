package igt.test.automation.mobile;

import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

/**
 * Utility library to handle common Android & iOS operations.
 * 
 * @author 
 */
public final class MobileCommonUtil {

    /** Log4j Logger. */
    private static final Logger LOG = LogManager
            .getLogger(MobileCommonUtil.class);

    /** Element is not enabled. */
    private static final String ELEMENT_NOT_ENABLED = "The element is not enabled: ";

    private MobileCommonUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * getText - fetches the visible innerText for the provided MobileElement.
     * 
     * @param elementTextToGet
     *            MobileElement to fetch the innerText
     * @return String - visible innerText value of the locator
     */
    public static String getText(final MobileElement elementTextToGet) {
        if (!elementTextToGet.isEnabled()) {
            LOG.error(ELEMENT_NOT_ENABLED +"{0}", elementTextToGet);
            Assert.fail(ELEMENT_NOT_ENABLED + elementTextToGet);
        }
        return elementTextToGet.getText();

    }

    /**
     * Use this method to simulate typing into an element, which may set its
     * value.
     * 
     * @param elementToTypeValuesInto
     *            - the WebElement you want to type into
     * @param valueToType
     *            value you want to enter
     * 
     */
    public static void typeValuesInATextBox(
            final MobileElement elementToTypeValuesInto,
            final String valueToType) {
        if (!elementToTypeValuesInto.isEnabled()) {
            LOG.error(ELEMENT_NOT_ENABLED +"{0}", elementToTypeValuesInto);
            Assert.fail(ELEMENT_NOT_ENABLED + elementToTypeValuesInto);
        }
        elementToTypeValuesInto.sendKeys(valueToType);

    }

}
