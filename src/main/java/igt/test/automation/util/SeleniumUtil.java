package igt.test.automation.util;

import com.aventstack.extentreports.ExtentTest;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import igt.test.automation.selenium.constants.IWaitTimeConstants;
import io.cucumber.core.api.Scenario;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * All the common Selenium utility methods are written here.
 *
 * @author Sujit
 */
public class SeleniumUtil {


    /**
     * default protected constructor.
     */
    protected SeleniumUtil() {

    }

    /**
     * Log4j Logger.
     */
    private static final Logger LOG = LogManager.getLogger(SeleniumUtil.class);

    /**
     * Constant number 97.
     */
    private static final int INTEGER_97 = 97;

    /**
     * Constant number 122.
     */
    private static final int INTEGER_122 = 122;

    /**
     * Constant number 3000.
     */
    private static final int INTEGER_3000 = 3000;

    /**
     * Constant number 1000.
     */
    private static final int INTEGER_1000 = 1000;

    /**
     * Constant number 3000.
     */
    private static final int INTEGER_1 = 1;

    /**
     * Failed to click button: .
     */
    private static final String FAILED_TO_CLICK_BUTTON = "Failed to click button:";

    /**
     * The selected status has not changed on checkbox click: .
     */
    private static final String STATUS_NOT_CHANGED_CHECKBOX = "The selected status has not changed on checkbox click: ";

    /**
     * Element is not enabled.
     */
    private static final String ELEMENT_NOT_ENABLED = "The element is not enabled: ";

    /**
     * Element not clicked.
     */
    private static final String ELEMENT_NOT_CLICKED = "Element has not been clicked";

    /**
     * Element not scrollable.
     */
    private static final String ELEMENT_NOT_SCROLLABLE = "Element could not be scrolled into view: ";

    /**
     * Invalid attribute.
     */
    private static final String INVALID_ATTRIBUTE = "Invalid Attribute for the field: ";

    /**
     * random object.
     */
    private static SecureRandom random = new SecureRandom();

    /**
     * Constant number 250.
     */
    private static final int INTEGER_250 = 250;

    /**
     * Constant number 250.
     */
    private static final int INTEGER_150 = 150;

    /**
     * IO Exception.
     **/
    private static final String IO_EXCEPTION = "IOException";

    /**
     * Test failed due to IOException.
     **/
    private static final String TEST_FAILURE_IOE = "Test failed due to IOException";

    /**
     * ./target/screenshots/.
     */
    private static final String SCREENSHOTS = "./target/screenshots/";

    /**
     * ../../target/screenshots/  complete line.
     */
    private static final String SCREENCAPTUREPATH = "../../target/screenshots/";

    /**
     * .png .
     */
    private static final String PNG = ".png";

    /**
     * .TWO_THOUSAND_MILLISECOND .
     */
    private static final int TWO_THOUSAND_MILLISECOND = 2000;

    /**
     * .FOUR_THOUSAND_MILLISECOND .
     */
    private static final int FOUR_THOUSAND_MILLISECOND = 4000;

    /**
     * getText - fetches the visible innerText for the provided WebElement.
     *
     * @param elementTextToGet WebElement to fetch the innerText
     * @return String - visible innerText value of the locator
     */
    public static String getText(final WebElement elementTextToGet) {
        if (!elementTextToGet.isEnabled()) {
            LOG.error(ELEMENT_NOT_ENABLED + "{}", elementTextToGet);
            Assert.fail(ELEMENT_NOT_ENABLED + elementTextToGet);
        }
        return elementTextToGet.getText();

    }

    /**
     * Move back a single "item" in the browser's history.
     *
     * @param driver {@link WebDriver}
     */
    public static void navigateBack(final WebDriver driver) {
        driver.navigate().back();
    }

    /**
     * Resize window to the position you want.
     *
     * @param driver {@link WebDriver}
     * @param x      co-ordinates in int
     * @param y      co-ordinates in int
     */
    public static void reSizeWindow(final WebDriver driver, final int x,
                                    final int y) {
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(x, y));
    }

    /**
     * this method enables clicking on the WebElement only if it is enabled.
     * Else it would fail.
     *
     * @param elementToClick - the WebElement you want to click
     * @param driver         {@link WebDriver}
     * @return boolean
     */
    public static boolean clickElementAssert(final WebElement elementToClick,
                                             final WebDriver driver) {

        boolean retVal;

        SynchronisationUtil
            .synchroniseUntilTheElementIsDisplayedEnabledAndClickable(
                driver, elementToClick);
        if (!elementToClick.isEnabled()) {
            LOG.error(ELEMENT_NOT_ENABLED + "{0}", elementToClick);
            Assert.fail(ELEMENT_NOT_ENABLED + elementToClick);
            retVal = false;
        } else {
            elementToClick.click();
            retVal = true;
        }
        return retVal;
    }

    /**
     * Click this element without any hard assertions.
     *
     * @param elementToClick - the WebElement you want to click
     * @return boolean
     */
    public static boolean clickElementNoAssert(
        final WebElement elementToClick) {

        boolean retVal = false;
        if (!elementToClick.isEnabled()) {
            LOG.error(ELEMENT_NOT_ENABLED + "{0}: ", elementToClick);
            retVal = false;
        } else {
            elementToClick.click();
            retVal = true;
        }
        return retVal;
    }

    /**
     * Use this method to simulate typing into an element, which may set its
     * value.
     *
     * @param elementToTypeValuesInto - the WebElement you want to type into
     * @param valueToType             value you want to enter
     * @param driver                  {@link WebDriver}
     */
    public static void typeValuesInATextBox(
        final WebElement elementToTypeValuesInto, final String valueToType,
        final WebDriver driver) {
        SynchronisationUtil.synchroniseUntilTheElementIsVisible(driver,
                                                                elementToTypeValuesInto);
        if (!elementToTypeValuesInto.isEnabled()) {
            LOG.error(ELEMENT_NOT_ENABLED + "{0}", elementToTypeValuesInto);
            Assert.fail(ELEMENT_NOT_ENABLED + elementToTypeValuesInto);
        }
        highlightElement(elementToTypeValuesInto, driver);
        elementToTypeValuesInto.sendKeys(valueToType);

    }

    /**
     * Use this method to simulate clicking the checkbox element.
     *
     * @param checkBoxWebElement - the checkbox WebElement you want to type into
     * @param shouldBeSelected   whether you want the checkbox to be selected or not
     * @param driver             {@link WebDriver}
     * @deprecated
     */
    @Deprecated public static void clickCheckBox(final WebElement checkBoxWebElement,
                                     final String shouldBeSelected, final WebDriver driver) {
        SynchronisationUtil.synchroniseUntilTheElementIsVisible(driver,
                                                                checkBoxWebElement);
        if ("OFF".equalsIgnoreCase(shouldBeSelected)) {
            if (checkBoxWebElement.isSelected()) {
                checkBoxWebElement.click();
            }
        } else if (!checkBoxWebElement.isSelected()) {
            checkBoxWebElement.click();
        }

    }

    /**
     * Use this method to simulate clicking the checkbox element.
     *
     * @param checkBoxWebElement - the checkbox WebElement you want to type into
     * @param shouldBeSelected   whether you want the checkbox to be selected true for selected false otherwise
     * @param driver             {@link WebDriver}
     */
    public static void clickCheckBox(final WebElement checkBoxWebElement, final boolean shouldBeSelected, final WebDriver driver) {
        SynchronisationUtil.synchroniseUntilTheElementIsVisible(driver, checkBoxWebElement);
        if (!shouldBeSelected) {
            if (checkBoxWebElement.isSelected()) {
                checkBoxWebElement.click();
            }
        } else if (!checkBoxWebElement.isSelected()) {
            checkBoxWebElement.click();
        }

    }

    /**
     * the method checks and returns whether the WebElement object is enabled or
     * not.
     *
     * @param elementToVerify WebElement to verify its enabled or not
     * @param driver          {@link WebDriver}
     * @return {@link boolean} returns whether the WebElement is enabled or not
     */
    public static boolean isElementEnabled(final WebElement elementToVerify,
                                           final WebDriver driver) {
        SynchronisationUtil.synchroniseUntilTheElementIsDisplayedAndEnabled(
            driver, elementToVerify, IWaitTimeConstants.GLOBAL_WAIT_TIME);
        return elementToVerify.isEnabled();
    }

    /**
     * This method is to clear the entered values in a TextBox or a TextArea.
     *
     * @param elementValueToClear text WebElement to clear the values
     * @param driver              {@link WebDriver}
     */
    public static void clearValuesInATextBox(
        final WebElement elementValueToClear, final WebDriver driver) {
        SynchronisationUtil.synchroniseUntilTheElementIsDisplayedAndEnabled(
            driver, elementValueToClear,
            IWaitTimeConstants.GLOBAL_WAIT_TIME);
        elementValueToClear.clear();
    }

    /**
     * <p>
     * isElementExists method finds whether the specified WebElement object
     * exists in a page or not without an assert.
     * </p>
     *
     * @param element is the WebElement of the object to find.
     * @return {@link boolean} return if the element exists this does not
     * produce an assert.
     */
    public static boolean isElementExists(final WebElement element) {
        try {
            element.getText();
            return true;
        } catch (NoSuchElementException e) {
            LOG.error(e);
            return false;
        }
    }

    /**
     * dropDownSelectionByValueAppearing - this method selects the option
     * specified based on the visible text appearing.
     *
     * @param dropDownList  specifies the WebElement to identify the dropdown
     * @param valueToSelect specifies the option value to be selected
     * @author Sujit
     */
    public static void selectDropDownByValueAppearing(
        final WebElement dropDownList, final String valueToSelect) {
        if (isElementExists(dropDownList)) {
            Select s = new Select(dropDownList);
            s.selectByVisibleText(valueToSelect);
        } else {
            LOG.info("Element non-selectable: {0}", dropDownList);
        }
    }

    /**
     * The below method is used to select a value which is Contained by drop
     * down list.
     *
     * @param dropdown specifies the WebElement to identify the dropdown
     * @param dropdownList specifies the list of options
     * @param valueToSelect specifies the value to select within the dropdown
     * @param driver {@link WebDriver}
     */
    public static void selectTheValueWhichIsConatinedByDropdownList(final WebElement dropdown,
                                                                    final List<WebElement> dropdownList,
                                                                    final String valueToSelect, final WebDriver driver) {

        SeleniumUtil.clickElementAssert(dropdown, driver);
        int listSize = dropdownList.size();
        for (int i = 0; i < listSize; i++) {
            if (dropdownList.get(i).getText().contains(valueToSelect)) {
                dropdownList.get(i).click();
                break;
            }
        }
    }

    /**
     * selectDropDownOfTypeMdSelectByValueAppearing - this method selects the
     * option specified based on the visible text appearing.
     *
     * @param dropDownMdSelectList specifies the WebElement to identify the Md-dropdown list
     * @param valueToSelect        specifies the option value to be selected
     * @author Sujit
     */
    public static void selectDropDownOfTypeMdSelectByValueAppearing(
        final WebElement dropDownMdSelectList, final String valueToSelect) {
        final List<WebElement> optionsToSelect = dropDownMdSelectList
            .findElements(By.tagName("md-option"));
        optionsToSelect.stream()
            .filter(element -> element.getText().equals(valueToSelect))
            .forEach(WebElement::click);

    }

    /**
     * getAttributeValue - this method fetches the attribute value of a
     * particular WebElement.
     *
     * @param elementValueToGet specifies the WebElement from which you need to get the
     *                          attribute value
     * @param attributeValue    specifies the attribute value name
     * @return String
     * @author 
     */
    public static String getAttributeValue(final WebElement elementValueToGet,
                                           final String attributeValue) {
        if (elementValueToGet.getAttribute(attributeValue) == null) {
            LOG.error(INVALID_ATTRIBUTE + "{}", attributeValue);
            Assert.fail(INVALID_ATTRIBUTE + attributeValue);
        }
        return elementValueToGet.getAttribute(attributeValue);

    }

    /**
     * testAllUpperCase method checks whether all characters in a string are in
     * Upper Case .
     *
     * @param stringToCheck string to check for Upper Case
     * @return {@link boolean}
     * @author 
     */

    public static boolean testAllUpperCase(final String stringToCheck) {
        for (int i = 0; i < stringToCheck.length(); i++) {
            char c = stringToCheck.charAt(i);
            if (c >= INTEGER_97 && c <= INTEGER_122) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>
     * isElementNotExists method finds whether the specified WebElement object
     * exists in a page or not without an assert.
     * </p>
     *
     * @param element is the WebElement of the object to find.
     * @return {@link boolean} return if the element exists this does not
     * produce an assert.
     */
    public static boolean isElementNotExists(final WebElement element) {
        try {
            element.getText();
            return false;
        } catch (NoSuchElementException e) {
            LOG.error(e);
            return true;
        }
    }

    /**
     * <p>
     * sendKeyTab method finds performs the keyboard Tab key operation.
     * </p>
     *
     * @param element is the WebElement of the object to find.
     */
    public static void sendKeyTab(final WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    /**
     * <p>
     * getSelectedOptionFromDropdown method gives the value selected from drop
     * down.
     * </p>
     *
     * @param dropDownList the WebElement of the object
     * @return String returns the selected value
     */
    public static String getSelectedOptionFromDropdown(
        final WebElement dropDownList) {
        Select select = new Select(dropDownList);
        return select.getFirstSelectedOption().getText();

    }

    /**
     * <p>
     * clickOnRandomDayInCalendar method clicks the random day in Calendar date
     * picker.
     * </p>
     *
     * @param listElement the WebElement of the calendar date picker
     */
    public static void clickOnRandomDayInCalendar(
        final List<WebElement> listElement) {
        String[] elements = new String[listElement.size()];
        for (int i = 0; i < listElement.size(); i++) {
            elements[i] = listElement.get(i).getText();
        }

        int rnd = new Random().nextInt(elements.length);

        for (WebElement webElement : listElement) {
            if (webElement.getText().equals(elements[rnd])) {
                webElement.click();
                break;
            }
        }
    }

    /**
     * Get a string representing the current URL that the browser is looking at.
     *
     * @param driver {@link WebDriver}
     * @return The URL of the page currently loaded in the browser
     */
    public static String getCurrentURL(final WebDriver driver) {
        return driver.getCurrentUrl();
    }

    /**
     * method to compare the Strings.
     *
     * @param originalString  expected value
     * @param stringToCompare actual value
     * @return {@link boolean} returns true or false
     */
    public static boolean checkStringContains(final String originalString,
                                              final String stringToCompare) {
        boolean flag = false;
        if (originalString.contains(stringToCompare)) {
            flag = true;
            return flag;
        }

        return flag;

    }

    /**
     * <p>
     * generateRandomString method generate the string of variable length.
     * </p>
     *
     * @param stringLength is the length of the string
     * @return String random string
     */
    public static String generateRandomString(final int stringLength) {

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        String randomString = stringBuilder.toString();
        return randomString.toUpperCase();
    }

    /**
     * <p>
     * verifyDisabled method verified whether webelement is disabled or not.
     * </p>
     *
     * @param element WebElement to verify element is disabled or not
     * @param driver  {@link WebDriver}
     * @return {@link boolean}
     */
    public static boolean verifyDisabled(final WebElement element,
                                         final WebDriver driver) {
        return (Boolean) ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].disabled", element);
    }

    /**
     * <p>
     * highlightElement method highlights a webElement.
     * </p>
     *
     * @param element to highlight
     * @param driver  {@link WebDriver}
     */
    public static void highlightElement(final WebElement element,
                                        final WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px dotted blue'",
                         element);
    }

    /**
     * <p>
     * scrollIntoView method scrolls the element in the view.
     * </p>
     *
     * @param element WebElement
     * @param driver  {@link WebDriver}
     */
    public static void scrollIntoView(final WebElement element,
                                      final WebDriver driver) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * switchToRecentlyOpenedWindow - method which allows the focus to switch to
     * the recently opened new window and also returns the MainWindowId from
     * where the newWindow opened.
     *
     * @param driver {@link WebDriver}
     * @return String - id of the window from where the newWindow opened
     * @author Sujit
     */
    public static String switchToRecentlyOpenedWindow(final WebDriver driver) {

        Set<String> windowIds = driver.getWindowHandles();
        Iterator<String> iterator = windowIds.iterator();

        String mainWinID = iterator.next();
        String newwinID = iterator.next();

        LOG.info("No. of windows: --> {0}", windowIds.size());
        if (windowIds.size() == 2) {
            // getting the focus into the New window
            driver.switchTo().window(newwinID);
        }

        return mainWinID;
    }

    /**
     * method which allows the focus to switch to the window with the specified
     * title passed as an argument and also returns the WindowId before making
     * the switch.
     *
     * @param windowTitle id of the window from where the newWindow opened
     * @param driver      {@link WebDriver}
     * @return String
     * @author Sujit
     */

    public String switchToSpecifiedWindow(final String windowTitle,
                                          final WebDriver driver) {
        String mainWinID = driver.getWindowHandle();
        Set<String> windowIds = driver.getWindowHandles();
        for (String windowId : windowIds) {
            LOG.info(driver.switchTo().window(windowId).getTitle());
            if (driver.switchTo().window(windowId).getTitle()
                .equals(windowTitle)) {
                LOG.info("Child Window Handle : {}", windowTitle);
                driver.switchTo().window(windowId);
            }
        }
        return mainWinID;

    }

    /**
     * this method allows the focus to switch to the passed in iFrame.
     *
     * @param iFrameElement iFrame WebElement to focus
     * @param driver        {@link WebDriver}
     * @author Sujit
     */
    public static void switchToIFrame(final WebElement iFrameElement,
                                      final WebDriver driver) {
        driver.switchTo().frame(iFrameElement);
    }

    /**
     * this method helps to double click on the specified row number of a
     * webTable.
     *
     * @param table  WebElement
     * @param rowNum - rowNumber to double click
     * @param driver {@link WebDriver}
     * @author Sujit
     */
    public static void doubleClickOnWebTable(final WebElement table,
                                             final int rowNum, final WebDriver driver) {
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<WebElement> cells = rows.get(rowNum)
            .findElements(By.tagName("td"));
        Actions action = new Actions(driver);
        action.moveToElement(cells.get(1)).doubleClick().perform();
    }

    /**
     * This method Clicks on the WebTable for the specified table's row.
     *
     * @param table     is the XPath Value to identify the webTable
     * @param rowNumber is the row you want to click the table
     * @author Sujit
     */
    public static void clickOnWebTable(final WebElement table,
                                       final int rowNumber) {

        try {
            List<WebElement> rows = table.findElements(By.xpath("//tbody/tr"));
            List<WebElement> cells = rows.get(rowNumber)
                .findElements(By.tagName("td"));
            cells.get(1).click();
        } catch (IndexOutOfBoundsException ioe) {
            LOG.info(
                "The rowNumber you have specified seems to be incorrect. Please specify the correct rowNumber."
                    + ioe);
        }
    }

    /**
     * getRowCountForWebTable - this method returns the Row count value for the
     * given webtable.
     *
     * @param table is the WebElement of the webTable.
     * @return int
     * @author Sujit
     **/
    public static int getRowCountForWebTable(final WebElement table) {
        int rowCount = 0;
        try {
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            rowCount = rows.size() - 1;
        } catch (ElementNotInteractableException e) {
            LOG.error(e);
        }
        return rowCount;

    }

    /**
     * method which searches the WebElement provided and returns a boolean.
     *
     * @param elementToSearch Webelement to search
     * @param driver          {@link WebDriver}
     * @return {@link boolean}
     */
    public static boolean isRecordExistsInAWebTable(
        final WebElement elementToSearch, final WebDriver driver) {
        boolean retVal = false;
        SynchronisationUtil
            .synchroniseUntilTheElementIsDisplayedEnabledAndClickable(
                driver, elementToSearch);
        waitForSpecificTime(INTEGER_3000);
        List<WebElement> rows = elementToSearch
            .findElements(By.xpath("//tbody/tr"));
        retVal = rows.isEmpty();

        return retVal;
    }

    /**
     * <p>
     * This method generates a random integer value between a specified range.
     * </p>
     *
     * @param min Minimum range
     * @param max Maximum range
     * @return String
     * @author 
     */

    public static String getRandomNumberBetweenRange(final int min, final int max) {
        int randomNum = random.nextInt((max - min) + INTEGER_1) + min;
        return String.valueOf(randomNum);
    }

    /**
     * This method makes the program halt for the specified amount of time.
     *
     * @param timeToWait time in milliseconds you want to wait for
     * @author 
     */

    public static void waitForSpecificTime(final long timeToWait) {
        Uninterruptibles.sleepUninterruptibly(timeToWait,
                                              TimeUnit.MILLISECONDS);
    }

    /**
     * <p>
     * Instead of forcing users to wait for a script to download before the page
     * renders. This function will execute an asynchronous piece of JavaScript
     * in the context of the currently selected window.
     * </p>
     *
     * @param driver {@link WebDriver}
     * @author 
     */
    public static void executeAsyncScript(final WebDriver driver) {

        long startTime = System.currentTimeMillis();

        ((JavascriptExecutor) driver).executeAsyncScript(
            "window.setTimeout(arguments[arguments.length - 1], 5000);");

        LOG.info("Elapsed time: {0}", (System.currentTimeMillis() - startTime));
    }

    /**
     * This methods generates today's date.
     *
     * @return String today's date
     * @author 
     */

    public static String generateTodaysDate() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMM");
        return formatter.format(date);

    }

    /**
     * <p>
     * clickElementInDropDownButton method clicks an element in a dropdown.
     * </p>
     *
     * @param eleDropDown is the element for the dropdown
     * @param eleItemList list of the WebElement type
     * @param eleToSelect dropdown element you want to select
     * @param driver      {@link WebDriver}
     * @author 
     */
    public static void clickElementInDropDownButton(
        final WebElement eleDropDown, final List<WebElement> eleItemList,
        final String eleToSelect, final WebDriver driver) {
        SynchronisationUtil
            .synchroniseUntilTheElementIsDisplayedEnabledAndClickable(
                driver, eleDropDown);
        SeleniumUtil.clickElementAssert(eleDropDown, driver);
        for (WebElement webElement : eleItemList) {
            if (webElement.getText().contains(eleToSelect)) {
                webElement.click();
                break;
            }

        }

    }

    /**
     * <p>
     * clickExpectedElementFromSmartSearch method clicks the expected item in a
     * smart search variable length.
     * </p>
     *
     * @param options     is the option list in smart search
     * @param eleToSelect to select in item list
     * @author 
     */
    public static void clickExpectedElementFromSmartSearch(
        final List<WebElement> options, final String eleToSelect) {
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(eleToSelect)) {
                option.click();
                break;
            }
        }
    }

    /**
     * <p>
     * This method refresh browser window.
     * </p>
     *
     * @param driver {@link WebDriver}
     * @author 
     */
    public static void refreshBrowserWindow(final WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(0);");
    }

    /**
     * <p>
     * This method fetch the css value from the webelement.
     * </p>
     *
     * @param webElement {@link WebElement} element to get the CSSValue
     * @param valueToGet String CSSValue to Get e.g. font-size
     * @return String
     * @author 
     */
    public static String getCSSValue(final WebElement webElement,
                                     final String valueToGet) {
        String cssValue = "";
        if (StringUtils.isNotEmpty(valueToGet)) {
            cssValue = webElement.getCssValue(valueToGet);
        }
        return cssValue;
    }

    /**
     * <p>
     * This method fetch the reference number from the URL.
     * </p>
     *
     * @param driver {@link WebDriver}
     * @return String
     * @author 
     */

    public static String storeReferenceNumberFromURL(final WebDriver driver) {
        String strCurrentURL = driver.getCurrentUrl();
        String[] arrCurrentURL = strCurrentURL.split("/");
        return arrCurrentURL[arrCurrentURL.length - 1];
    }

    /**
     * getObjectById method finds a particular object in a page according to the
     * id specified.
     *
     * @param driver   is the driver to use.
     * @param id       is the id of the object.
     * @param waitTime is the integer time to wait.
     * @return WebElement the element
     */
    public static WebElement getObjectById(final WebDriver driver, final String id, final int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        return driver.findElement(By.id(id));
    }

    /**
     * getObjectById method finds a particular object in a page according to the
     * id specified.
     *
     * @param driver is the driver to use.
     * @param id     is the id of the object.
     * @return WebElement
     */
    public static WebElement getObjectById(final WebDriver driver,
                                           final String id) {
        WebDriverWait wait = new WebDriverWait(driver,
                                               IWaitTimeConstants.GLOBAL_SYNCHRONISATION_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        return driver.findElement(By.id(id));
    }

    /**
     * getObject method finds a particular object in a page according to the
     * XPathKey specified.
     *
     * @param driver   is the driver to use.
     * @param xpathKey is the xpath of the object.
     * @return WebElement
     */
    public static WebElement getObject(final WebDriver driver,
                                       final String xpathKey) {
        WebDriverWait wait = new WebDriverWait(driver,
                                               IWaitTimeConstants.GLOBAL_SYNCHRONISATION_TIMEOUT);
        wait.until(ExpectedConditions
                       .presenceOfElementLocated(By.xpath(xpathKey)));
        return driver.findElement(By.xpath(xpathKey));
    }

    /**
     * Hover on main menu and click sub menu.
     *
     * @param driver   is webdriver
     * @param mainMenu WebElement of main menu
     * @param submenu  WebElement of sub menu
     */
    public static void hoverMainMenuAndClickOnTheSubMenu(final WebDriver driver,
                                                         final WebElement mainMenu, final WebElement submenu) {
        Actions actions = new Actions(driver);

        // Hover main menu.
        try {
            actions.moveToElement(mainMenu).perform();
        } catch (ElementNotInteractableException e) {
            LOG.info(ELEMENT_NOT_SCROLLABLE + "{0}", e);
        }

        // Click sub menu using web element click method.
        Assert.assertTrue(clickElementAssert(submenu, driver),
                          ELEMENT_NOT_CLICKED);

    }

    /**
     * Hover on main menu and click on it.
     *
     * @param driver   is webdriver
     * @param mainMenu WebElement of main menu
     */
    public static void hoverAndClickOnMainMenu(final WebDriver driver,
                                               final WebElement mainMenu) {
        Actions actions = new Actions(driver);

        // Hover main menu.
        try {
            actions.contextClick(mainMenu);
        } catch (ElementNotInteractableException e) {
            LOG.info(ELEMENT_NOT_SCROLLABLE + "{0}", e);
        }

    }

    /**
     * The method checkBoxClick - method is to click so the checkbox is selected
     * or unselected. This will click the check box as expected.
     *
     * @param checkBox WebElement reference
     * @return boolean clicked true/false and change of selected status.
     */
    public static boolean clickCheckBox(final WebElement checkBox) {
        boolean clicked = false;
        boolean changeStatus = false;
        boolean isCheckBoxSelected = false;

        if (checkBox.isEnabled()) {
            isCheckBoxSelected = checkBox.isSelected();
            checkBox.click();
            clicked = true;
            changeStatus = checkBox.isSelected() != isCheckBoxSelected;
        }

        if (!clicked) {
            LOG.warn(FAILED_TO_CLICK_BUTTON + "{0}", checkBox);
        }
        if (!changeStatus) {
            LOG.warn(STATUS_NOT_CHANGED_CHECKBOX + "{0}", checkBox);
        }

        return clicked && changeStatus;
    }

    /**
     * Select values from multi-select.
     *
     * @param driver         the WebDriver to use
     * @param element        the multiSelectListbox WebElement
     * @param valuesToSelect a List<String> for the values to Select
     */
    public static void selectValueFromMultilistBox(final WebDriver driver, final WebElement element, final List<String> valuesToSelect) {
        if (isElementExists(element)
            && CollectionUtils.isNotEmpty(valuesToSelect)) {
            for (String valueToBeSelected : valuesToSelect) {
                SynchronisationUtil.synchroniseUntilTheWebElementIsOnThePage(
                    driver, element,
                    IWaitTimeConstants.GLOBAL_WAIT_TIME_MAX);
                selectDropDownByValueAppearing(element, valueToBeSelected);
            }
        }
    }

    /**
     * clickRadioButton - method to select or deselect radio button and return
     * boolean.
     *
     * @param radio WebElement of the radio button.
     * @return boolean
     */
    public static boolean clickRadioButton(final WebElement radio) {
        boolean clicked = false;
        if (isElementExists(radio) && radio.isEnabled()) {
            try {
                radio.click();
                clicked = radio.isSelected();
            } catch (ElementNotVisibleException e) {
                LOG.error(e);
            }
        }
        return clicked;
    }

    /**
     * clickDayInCalendar -method to select day in a calendar.
     *
     * @param datePickerTable table for WebElement for date picker element
     * @param dayToClick      day to click in calendar
     */
    public static void clickDayInCalendar(final WebElement datePickerTable,
                                          final int dayToClick) {

        List<WebElement> tableData = datePickerTable
            .findElements(By.tagName("td"));

        for (WebElement element : tableData) {
            if (element.getText().equals(String.valueOf(dayToClick))) {
                element.click();
                break;
            }
        }
    }

    /**
     * switchToParentWindow-method to switch to parent window.
     *
     * @param driver webdriver reference
     */
    public static void switchToParentWindow(final WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    /**
     * Hover on a web element.
     *
     * @param driver  is webdriver
     * @param element WebElement on which mouse hover needs to be done
     */
    public static void hoverOnElement(final WebDriver driver,
                                      final WebElement element) {
        Actions actions = new Actions(driver);

        actions.moveToElement(element).build().perform();

    }

    /**
     * Get text from alert.
     *
     * @param driver is webdriver
     * @return string value from the alert
     */
    public static String getTextFromAlert(final WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        return alert.getText();

    }

    /**
     * this method will accept the alert.
     *
     * @param driver is webdriver
     */
    public static void acceptAlert(final WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    /**
     * this method will dismiss the alert.
     *
     * @param driver is webdriver
     */

    public static void dismissAlert(final WebDriver driver) {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    /**
     * To verify options in a listBox.
     *
     * @param listElement    WebElement of listBox
     * @param valuesToVerify values to verify in list
     * @return boolean
     */
    public static boolean verifyMultiValuesOfList(final WebElement listElement,
                                                  final String[] valuesToVerify) {
        boolean flag = false;
        int matchedCount = 0;
        Select select = new Select(listElement);
        List<WebElement> options = select.getOptions();
        for (WebElement ele : options) {
            for (String s : valuesToVerify) {
                if (ele.getText().equalsIgnoreCase(s)) {
                    matchedCount++;
                }
            }
        }

        if (matchedCount == valuesToVerify.length) {
            flag = true;
        }
        return flag;

    }

    /**
     * Method to check whether a value is present in the td tag of web table.
     *
     * @param tableElement  WebElement for the table
     * @param valueToVerify is value to verify in webtable
     * @return boolean as true if data is present in table otherwise return as
     * false
     */
    public static boolean verifyExpectedValueInTable(
        final WebElement tableElement, final String valueToVerify) {
        boolean isExist = false;

        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));
        for (WebElement rowsData : rows) {
            List<WebElement> coloumns = rowsData.findElements(By.tagName("td"));
            for (WebElement coloumnData : coloumns) {
                if (coloumnData.getText().equalsIgnoreCase(valueToVerify)) {
                    isExist = true;
                    break;
                }
            }
        }

        return isExist;

    }

    /**
     * Method to verify the option present is dropdown or not.
     *
     * @param selectElement is WebElement for Select dropdown
     * @param valueToVerify is String value to verify in Select dropdown
     * @return boolean as true if value is present
     */
    public static boolean checkElementExistenceInDropdown(
        final WebElement selectElement, final String valueToVerify) {
        boolean flag = false;
        Select select = new Select(selectElement);
        if (select.getOptions().stream()
            .anyMatch(item -> item.getText().equals(valueToVerify))) {
            flag = true;

        }
        return flag;
    }

    /**
     * Method to select multiple values from Dual List Box.
     *
     * @param listElement    is the WebElement for the listBox
     * @param valuesToSelect are the values to select from listBox
     */
    public static void selectItemsFromDualListBox(final WebElement listElement,
                                                  final List<String> valuesToSelect) {

        List<WebElement> listElements = listElement
            .findElements(By.tagName("li"));

        for (WebElement options : listElements) {
            for (String values : valuesToSelect) {
                if (options.getText().equals(values)) {
                    options.click();
                }
            }

        }

    }

    /**
     * Method to select multiple values from jQuery List Box.
     *
     * @param listElement    is the WebElement for the Select tag
     * @param driver         is the WebDriver
     * @param valuesToSelect are the values to select from listBox
     */
    public static void selectjQueryListBox(final WebElement listElement,
                                           final WebDriver driver, final List<String> valuesToSelect) {
        Select select = new Select(listElement);
        List<WebElement> elementOptions = select.getOptions();
        Actions actions = new Actions(driver);
        for (WebElement options : elementOptions) {
            for (String str : valuesToSelect) {
                if (options.getText().equals(str)) {

                    actions.keyDown(Keys.LEFT_CONTROL).click(options)
                        .keyUp(Keys.LEFT_CONTROL).build().perform();
                }
            }

        }

    }

    /**
     * Method to run the batch file.
     *
     * @param filePath is the location for the batch file
     * @param fileName is the name of the file
     */
    public static void runBatchFile(final String filePath,
                                    final String fileName) {
        try {
            String command = "cmd /c start " + filePath + File.separator
                + fileName;
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);
            process.waitFor();
        } catch (Exception exception) {
            LOG.error("Error executing  the file " + fileName
                          + " ,getting the exception" + exception);
        }
    }

    /**
     * Method to press enter key.
     *
     * @param element {@link WebElement}
     */
    public static void pressEnterKey(final WebElement element) {
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Method to validate the date format.
     *
     * @param dateToValidate is the date to validate e.g. "01-04-2019"
     * @param dateFormat     is the date format e.g. "dd-MM-YYYY"
     * @return {@link Boolean}
     */
    public static boolean verifyDateFormat(final String dateToValidate,
                                           final String dateFormat) {
        boolean flag = false;
        if (dateToValidate == null) {
            return flag;
        }
        SimpleDateFormat simpleDareFormat = new SimpleDateFormat(dateFormat);
        simpleDareFormat.setLenient(false);
        try {
            Date date = simpleDareFormat.parse(dateToValidate);
            LOG.info(date);
            flag = true;
        } catch (ParseException e) {
            LOG.error("Error while parsing the date : {}", e);
            return flag;
        }

        return flag;

    }

    /**
     * Method to upload the file.
     *
     * @param filePath is the path of the file
     * @param fileName is the name of the file
     */
    public static void uploadFile(final String filePath,
                                  final String fileName) {
        StringSelection stringSelection = new StringSelection(
            filePath + File.separator + fileName);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        try {
            Robot robot = new Robot();
            robot.delay(INTEGER_250);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(INTEGER_150);
            robot.keyRelease(KeyEvent.VK_ENTER);
            SeleniumUtil.waitForSpecificTime(INTEGER_3000);
        } catch (AWTException e) {
            LOG.error("Getting the exception :{}", e);
        }
    }

    /**
     * Method to verify the data in table using pagination.
     *
     * @param pagesElement     is the {@link WebElement} for page numbers
     * @param tableBodyElement is the tbody {@link WebElement}
     * @param dataToVerify     is the data {@link String} to verify in the table
     *                         <td>
     * @return {@link Boolean}
     */
    public static boolean isTableDataExistsWithPagination(
        final WebElement pagesElement, final WebElement tableBodyElement,
        final String dataToVerify) {
        boolean isExist = false;
        List<WebElement> pageList = pagesElement.findElements(By.tagName("a"));
        for (int i = 1; i < pageList.size(); i++) {
            List<WebElement> tableRowList = tableBodyElement
                .findElements(By.tagName("tr"));
            for (WebElement rowData : tableRowList) {
                List<WebElement> tableDataList = rowData
                    .findElements(By.tagName("td"));
                for (WebElement tableData : tableDataList) {
                    if (tableData.getText().equals(dataToVerify)) {
                        isExist = true;
                        break;
                    }
                }
            }
            if (pageList.get(i).isDisplayed() && pageList.get(i).isEnabled()) {
                pageList.get(i).click();
            }
        }
        return isExist;
    }

    /**
     * Method to verify the value in dropdown list.
     *
     * @param element       is {@link WebElement}
     * @param valueToVerify is {@link String} value to verify
     * @return {@link Boolean}
     */
    public static boolean checkElementExistenceInDropDownList(
        final WebElement element, final String valueToVerify) {
        boolean isExist = false;
        Select select = new Select(element);
        List<WebElement> list = select.getOptions();
        for (WebElement listElement : list) {
            if (listElement.getText().equals(valueToVerify)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    /**
     * Method to perform drag and drop.
     *
     * @param driver        {@link WebDriver}
     * @param sourceElement - the source webElement
     * @param targetElement - the target webElement
     */
    public static void dragAndDropElement(final WebDriver driver,
                                          final WebElement sourceElement, final WebElement targetElement) {
        Actions action = new Actions(driver);
        action.dragAndDrop(sourceElement, targetElement).build().perform();
    }

    /**
     * Method to perform right click. Performs a context-click at middle of the
     * given element.
     *
     * @param driver        {@link WebDriver}
     * @param targetElement - the target webElement
     */
    public static void rightClickOnElement(final WebDriver driver,
                                           final WebElement targetElement) {
        Actions action = new Actions(driver);
        action.contextClick(targetElement).perform();
    }

    /**
     * Method that loads the ColumnHeader and the value of a return row against
     * that columnHeaders. <br>
     * Assumption is that the return row contains only one row.
     *
     * @param rowElement      is the WebElement Value to identify the interested row data.
     * @param tableHeaderElem is the WebElement value to identify the webTable header.
     * @return Map<String, String>
     * @author Sujit
     **/
    public static Map<String, String> webTableColumnHeadersRowMapper(
        final WebElement rowElement, final WebElement tableHeaderElem) {
        String colHeader;

        // get the column Headers
        List<WebElement> headerCols = tableHeaderElem
            .findElements(By.tagName("th"));
        List<String> columnHeaderSet = new ArrayList<>();
        for (WebElement webElement : headerCols) {
            colHeader = webElement.getText();
            columnHeaderSet.add(colHeader);
        }

        // get the column values
        List<WebElement> dataTableValue = rowElement
            .findElements(By.tagName("td"));

        List<String> values = new ArrayList<>();
        // get the required webElement
        for (WebElement webElement : dataTableValue) {
            values.add(webElement.getText());
        }

        // map the columnHeaders with columnValues
        Map<String, String> webTableMap = new HashMap<>();
        for (int i = 0; i < columnHeaderSet.size(); i++) {
            webTableMap.put(columnHeaderSet.get(i), values.get(i));
        }

        return webTableMap;
    }

    /**
     * Method to read the details from QR code or Bar code.
     *
     * @param qrCodeImage is the src attribute of the image. It may be http or base64
     *                    String
     * @return {@link String}
     * @author 
     */
    public static String decodeQRCode(final String qrCodeImage) {
        BufferedImage bufferedImage = null;
        Result result = null;

        try {
            if (qrCodeImage.contains("http")) {
                bufferedImage = ImageIO.read((new URL(qrCodeImage)));
            } else {
                byte[] decoded = Base64.decodeBase64(qrCodeImage);
                bufferedImage = ImageIO.read(new ByteArrayInputStream(decoded));
            }
            LuminanceSource source = new BufferedImageLuminanceSource(
                bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            result = new MultiFormatReader().decode(bitmap);
        } catch (IOException e) {
            LOG.info(
                "Thrown to indicate that a malformed URL has occurred. Either no legal protocol could be found in a specification string or the string could not be parsed"
                    + e);
        } catch (com.google.zxing.NotFoundException e) {
            LOG.info("File not found: {} ", e);
        }
        if (result != null) {
            return result.getText();
        } else {
            LOG.error("NO Data found in QRCode");
            return null;
        }

    }

    /**
     * Method to the embed the screenshots in thee cucumber reports.
     *
     * @param driver   is {@link WebDriver}
     * @param scenario is {@link Scenario}
     */
    public static void makeScreenshot(final WebDriver driver,
                                      final Scenario scenario) {
        if (driver instanceof TakesScreenshot) {
            TakesScreenshot screenshotableDriver = (TakesScreenshot) driver;
            try {
                byte[] screenshot = screenshotableDriver
                    .getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException e) {
                LOG.error(e);
            }
        } else {
            LOG.warn(
                "This web driver implementation {} cannot create screenshots",
                driver.getClass().getName());
        }
    }

    /**
     * takeFullScreenShot - this method takes the full page screenshots and
     * saves under "screenshots" folder under the target directory of test. This
     * util uses the ExtentReport 4.0.
     *
     * @param driver WebDriver instance
     * @param test   ExtentTest instance
     **/
    public static void takeFullScreenShot(final WebDriver driver,
                                          final ExtentTest test) {
        Date d = new Date();
        String fileName = d.toString().replace(" ", "_").replace(":", "_");
        String filePath = fileName + PNG;

        try {
            Screenshot screenshot = new AShot()
                .shootingStrategy(
                    ShootingStrategies.viewportPasting(INTEGER_1000))
                .takeScreenshot(driver);
            new File(SCREENSHOTS).mkdir();
            ImageIO.write(screenshot.getImage(), "PNG",
                          new File(SCREENSHOTS + filePath));
            test.addScreenCaptureFromPath(
                SCREENCAPTUREPATH + filePath);
        } catch (IOException e) {
            LOG.error(TEST_FAILURE_IOE);
            LOG.error(IO_EXCEPTION + "{}", e);
        }
    }

    /**
     * This method is used to navigate to required location(either button or
     * link or any action item) in the application using keyboard tab
     * functionality and performs enter operation.
     *
     * @param numberOfTABClicks is the number of times of tab clicks through keyboard.
     * @param driver {@link WebDriver}
     */
    public void performTABOperationAndClickOnEnter(final int numberOfTABClicks, final WebDriver driver) {
        Actions action = new Actions(driver);
        for (int i = 0; i < numberOfTABClicks; i++) {
            action.sendKeys(Keys.TAB).perform();
            SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
        }
        action.sendKeys(Keys.ENTER).perform();
        SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
    }

    /**
     * This method is used to enter value in text box using actions class.
     *
     * @param moveToString is the text value to be entered.
     * @param driver {@link WebDriver}
     */
    public void performSendingString(final String moveToString, final WebDriver driver) {
        Actions action = new Actions(driver);
        action.sendKeys(moveToString).perform();
        SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
    }

    /**
     * This method is used to navigate to required location(either button or
     * link or any action item) in the application using keyboard tab
     * functionality.
     *
     * @param numberOfTABClicks is the number of times of tab clicks through keyboard.
     * @param driver {@link WebDriver}
     */
    public void performOnlyTABOperation(final int numberOfTABClicks, final WebDriver driver) {
        Actions action = new Actions(driver);
        for (int i = 0; i < numberOfTABClicks; i++) {
            action.sendKeys(Keys.TAB).perform();
            SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
        }
    }

    /**
     * This method is used to performing space operation through key board
     * actions.
     * <p>
     * This is for checking the check boxes through keyboard funtionality.
     *
     * @param driver {@link WebDriver}
     */
    public void performSpaceOperation(final WebDriver driver) {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.SPACE).perform();
        SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
    }

    /**
     * This method is used to perform shift tab (Back navigation among the
     * action items in application) operation and click on enter button only
     * through key board actions.
     *
     * @param numberOfTABClicks is the number of times of tab clicks through keyboard.
     * @param driver {@link WebDriver}
     */
    public static void performShiftTabOperationAndClickOnEnter(final int numberOfTABClicks, final WebDriver driver) {
        Actions action = new Actions(driver);
        for (int i = 0; i < numberOfTABClicks; i++) {
            action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).perform();
            action.keyUp(Keys.SHIFT).perform();
            SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
        }
        action.sendKeys(Keys.ENTER).perform();
        SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
    }

    /**
     * This method is used to perform shift tab (Back navigation among the
     * action items in application) operation through key board actions.
     *
     * @param numberOfTABClicks no. times you want to click
     * @param driver {@link WebDriver}
     */
    public static void performOnlyShiftTabOperation(final int numberOfTABClicks, final WebDriver driver) {
        Actions action = new Actions(driver);
        for (int i = 0; i < numberOfTABClicks; i++) {
            action.keyDown(Keys.SHIFT).sendKeys(Keys.TAB).perform();
            action.keyUp(Keys.SHIFT).perform();
            SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
        }
    }

    /**
     * Performing arrow operations to select a value from drop down only with
     * key board actions.
     *
     * @param numberOfClicks no.of clicks
     * @param arrowType left, right up or down
     * @param driver {@link WebDriver}

     */
    public void performArrowOperation(final int numberOfClicks, final String arrowType, final WebDriver driver) {
        Actions action = new Actions(driver);
        if ("DOWN_ARROW".equalsIgnoreCase(arrowType)) {
            for (int i = 0; i < numberOfClicks; i++) {
                action.sendKeys(Keys.ARROW_DOWN);
                SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
            }
            action.sendKeys(Keys.ENTER).perform();
        }
        if ("UP_ARROW".equalsIgnoreCase(arrowType)) {
            for (int i = 0; i < numberOfClicks; i++) {
                action.sendKeys(Keys.ARROW_UP);
                SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
            }
        }
        if ("RIGHT_ARROW".equalsIgnoreCase(arrowType)) {
            for (int i = 0; i < numberOfClicks; i++) {
                action.sendKeys(Keys.ARROW_RIGHT);
                SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
            }
            action.sendKeys(Keys.ENTER).perform();
        }
        if ("LEFT_ARROW".equalsIgnoreCase(arrowType)) {
            for (int i = 0; i < numberOfClicks; i++) {
                action.sendKeys(Keys.ARROW_LEFT);
                SeleniumUtil.waitForSpecificTime(TWO_THOUSAND_MILLISECOND);
            }
            action.sendKeys(Keys.ENTER).perform();
        }
    }

    /**
     * Navigate from one action item to another action item in the application.
     * <p>
     * Note: the WebElement id attribute should not be present in either div tag
     * or a(Anchor) tag.
     *
     * @param webElement - element you want to click
     */
    public void clickBackMenuUsingTab(final WebElement webElement) {
        SeleniumUtil.sendKeyTab(webElement);
    }

    /**
     * The below method is used to zoom in the browser screen using robot class
     * to verify whether application works properly at any zoom percentage.
     *
     * @param number is number of times to perform zoom in operation.
     */
    public void performZoomInOperation(final int number) {
        SeleniumUtil.waitForSpecificTime(FOUR_THOUSAND_MILLISECOND);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            LOG.error(e);
        }
        for (int i = 0; i < number; i++) {
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_ADD);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_ADD);
            }
        }
        SeleniumUtil.waitForSpecificTime(FOUR_THOUSAND_MILLISECOND);

    }

    /**
     * The below method is used to perform zoom out operation of the browser
     * screen using robot class.
     *
     * @param number is number of times to perform zoom out operation.
     */
    public void performZoomOutOperation(final int number) {
        SeleniumUtil.waitForSpecificTime(FOUR_THOUSAND_MILLISECOND);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            LOG.error(e);
        }
        for (int i = 0; i < number; i++) {
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_SUBTRACT);
            }
        }
        SeleniumUtil.waitForSpecificTime(FOUR_THOUSAND_MILLISECOND);
    }
    
    /**
     * Close current tab and switch to parent
     *
     * @param drivr
     */
    public static void closeCurrentTabSwitchToParent(WebDriver driver) {
    	Set <String> st= driver.getWindowHandles();
		Iterator<String> it = st.iterator();
		String parent =  it.next();
		driver.close();
		driver.switchTo().window(parent);
    }

}
