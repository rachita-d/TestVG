package framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class BasePage extends BaseClass{

    protected HashMap<String, String> jsonMap =null;


    protected BasePage(OperationsBundle operationsBundle, String jsonPath)  {
        setOperationsBundle(operationsBundle);
        info("Repo path: " + jsonPath);
        loadJson(jsonPath);
        captureScreenshot();
    }

    /**
     * Load json
     * @param jsonPath
     */
    protected void loadJson(String jsonPath){
        InputStream inputStream = null;
        ClassLoader classLoader = getClass().getClassLoader();
        inputStream = classLoader.getResourceAsStream(jsonPath);
        if (inputStream == null) {
           info("Sorry, unable to find " + jsonPath);
            return;
        }
        try {
            this.jsonMap = new ObjectMapper().readValue(inputStream,HashMap.class);
        } catch (IOException e) {
            error("File load failed");
        }

    }

    protected HashMap getJsonMap() {
        return this.jsonMap;
    }

    /**
     * Method to get the locator ID from the json
     * @param locatorKey - locator key in Json
     * @param idType - id type, here xpath
     * @return
     */
    protected String getLocatorId(String locatorKey, String idType) {
        Object locatorObject = ((HashMap) getJsonMap().get(locatorKey)).get(idType);
        String locatorId = null;
        if (locatorObject instanceof ArrayList) {
            ArrayList<String> idArray = (ArrayList) locatorObject;
            for(String id: idArray) {
                    locatorId = id;
                    break;
            }
        } else if (locatorObject instanceof String) {
            locatorId = locatorObject.toString();
        }
        return locatorId;
    }

    /**
     * Get element
     * @param locator
     * @return
     */
    protected WebElement getElement(String locator){
        return getOperationsBundle().getDriver().findElement(By.xpath(locator));
    }

    /**
     * Method to click on Element
     * @param element
     */
    protected void clickElement(WebElement element) {
        if (element.isDisplayed()) {
            element.click();
        }
    }

    /**
     * Get xpath for an element from the corresponding JSON file
     *
     * @param locatorKey name of webelement
     * @return element's xpath available in JSON file
     */
    protected String getXpath(String locatorKey) {
        return getLocatorId(locatorKey, "xpath");
    }
    /**
     * Click Element based on xpath
     * @param locatorKey-- Place xpath of an element in json and pass its key here
     */
    protected void clickElementByXpath(String locatorKey) {
        String locatorId =  getLocatorId(locatorKey, "xpath");
        clickElement(getElement(locatorId));
        info("Element " + locatorKey + " with xpath " + " : " + locatorId + " is clicked");
    }
    protected void sendKeys(WebElement element, String text) {
        if (element.isDisplayed()) {
            element.sendKeys(text);
        }
    }

    /**
     * Send keys.
     *
     * @param locatorKey: key of xpath from json file
     * @param text the text
     */
    protected void sendKeysByXpath(String locatorKey, String text) {
        String locatorId = getLocatorId(locatorKey, "xpath");
        getElement(locatorId).clear();
        sendKeys(getElement(locatorId), text);
        info("Element with locator " + locatorId + " is set : " + text);
    }

    /**
     * Click element via xpath with variables
     * @param xpathKey--Key of an element in json
     * @param runtimeValue--Inputs need to place in runtime xpath
     * @usage In json provide runtime xpaths by replacing input parameters with syntax and exact sequence as in runtime xpath like {xpathInput1}, {xpathInput2} and so.
     *        Runtime xpath is "//a[contains(@id,'randomID1_somethingInBetween_randomID2')]".
     *        In this "randomID1" and "randomID2" are dynamic values/inputs.
     *        In json replace these inputs params with {xpathInput1} and {xpathInput2}. So in json it will become "//a[contains(@id,'{xpathInput1}_somethingInBetween_{xpathInput2}')]".
     *        While parameters to pass this method would be xpathKey,"randomID1" and "randomID2"
     */
    protected void clickElementByRuntimeXpath(String xpathKey, String ...runtimeValue){
        int index=1;
        String xpathTemplate = getXpath(xpathKey);
        info("Xpath from json is:"+xpathTemplate);
        for(String value:runtimeValue){
            xpathTemplate=xpathTemplate.replace("{xpathInput"+index+"}", value);
            index++;
        }
        info("xpath to click is: " + xpathTemplate);
        getDriver().findElement(By.xpath(xpathTemplate)).click();
        info("Element with run time xpath " + " : " + xpathTemplate + " is clicked");
    }

    /**
     * Check if element exists via xpath
     * @param xpathKey--Key of an element in json
     * @param runtimeValue--Inputs need to place in runtime xpath
     * @return
     */
    protected Boolean isElementExistsByRuntimeXpath(String xpathKey, String ...runtimeValue){
        int index=1;
        String xpathTemplate = getXpath(xpathKey);
        debug("Xpath from json is:"+xpathTemplate);
        for(String value:runtimeValue){
            xpathTemplate=xpathTemplate.replace("{xpathInput"+index+"}", value);
            index++;
        }
        int count=getDriver().findElements(By.xpath(xpathTemplate)).size();
        debug("Found total DOM elements for xpath : " + xpathTemplate + " ="+count);
        Boolean result= count > 0;
        return result;
    }

    /**
     * Method to get the Element text with variable xpath
     * @param xpathKey
     * @param runtimeValue
     * @return
     */
    protected String getElementTextByRuntimeInput(String xpathKey, String ...runtimeValue){

        int index=1;
        String xpathTemplate = getXpath(xpathKey);
        info("Xpath from json is:"+xpathTemplate);
        for(String value:runtimeValue){
            xpathTemplate=xpathTemplate.replace("{xpathInput"+index+"}", value);
            index++;
        }
        info("xpath to click is: " + xpathTemplate);
        return getDriver().findElement(By.xpath(xpathTemplate)).getText();
    }

}
