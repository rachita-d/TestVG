package framework;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.io.File;

public class BaseClass {


    private OperationsBundle operationsBundle;

    /**
     * Instantiates a new base class.
     */
    protected BaseClass() {

        DOMConfigurator.configure("log4j.xml");
    }

    /**
     * Info.
     *
     * @param msg the msg
     */
    protected void info(String msg) {
        Utils.printInfo(this.getClass(), msg);
        Reporter.log(Utils.getTimeStamp()+" INFO  ["+ this.getClass().getSimpleName() + "] " + msg + "<br>");
    }

    protected void warning(String msg) {
        Utils.printWarning(this.getClass(), msg);
        Reporter.log(Utils.getTimeStamp()+" WARN  ["+ this.getClass().getSimpleName() + "] " + msg + "<br>");
    }

    protected void error(String msg) {
        Utils.printError(this.getClass(), msg);
        Reporter.log(Utils.getTimeStamp()+" ERROR ["+ this.getClass().getSimpleName() + "] " + msg + "<br>");
    }

    protected void debug(String msg) {
        Utils.printDebug(this.getClass(), msg);
        Reporter.log(Utils.getTimeStamp()+" DEBUG ["+ this.getClass().getSimpleName() + "] " + msg + "<br>");
    }

    protected String threadId(){
        return Utils.threadId();
    }


    protected WebDriver getDriver() {
        if(getOperationsBundle() !=null){
            return getOperationsBundle().getDriver();
        }else{
            return null;
        }
    }

    /**
     * Sets the driver.
     *
     * @param driver the new driver
     */
    protected void setDriver(WebDriver driver) {
        info(threadId()+":setDriver:operationsBundle");
        getOperationsBundle().setDriver(driver);
    }

    protected OperationsBundle getOperationsBundle() {
        return this.operationsBundle;
    }

    protected void setOperationsBundle(OperationsBundle operationsBundle) {
        this.operationsBundle = operationsBundle;
    }

    protected void captureScreenshot() {
        captureScreenshot(getClass().getSimpleName());
    }

    protected void captureScreenshot(String className) {
        File scrFile;
        if (getDriver() != null) {

            try{
                scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            } catch(Exception e){
                getDriver().switchTo().defaultContent(); //Source - http://www.easyselenium.com/typeerror-cant-access-dead-object-geckodriver/
                scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
                info("Screenshot taken after switching to default content");
            }
            String destination = "target/screenshots/" + className+"_"+Utils.generateRandomString()+ ".png";
            File screenshotName = new File(destination);
            try {
                FileUtils.copyFile(scrFile, screenshotName);
            }catch (Exception e){
                Utils.printError(this.getClass(),"Failed to copy screenshot");
            }
            //add screenshot to results by copying the file
            info(getDriver().getCurrentUrl());
            Reporter.log("<a href='../../../../artifact/" + destination + "'>  <img src='../../../../artifact/" + destination + "' style='max-height:100px;' /></a><br>");
        }
    }
}
