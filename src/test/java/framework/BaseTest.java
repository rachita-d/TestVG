package framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.log4testng.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest extends BaseClass{

    public static WebDriver driver;
    public static FileInputStream fileInputStream;
    public static Properties properties;
    public String Browser;
    private OperationsBundle operationsBundle;
    List<WebDriver> activeDrivers = new ArrayList<>();

    /**
     * This method is to open the driver instance with the URL
     * @throws IOException
     */
    public void initDriver() throws IOException {

        fileInputStream=new FileInputStream("./src/test/java/framework/Global.properties");
        properties=new Properties();
        properties.load(fileInputStream);
        Browser=properties.getProperty("Browser");
        setDriver(newDriver(Browser));
        driver= getOperationsBundle().getDriver();
        driver.get(properties.getProperty("URL"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        activeDrivers.add(driver);
    }

    /**
     * To initialise the driver
     * @param browser
     * @return
     */
    public WebDriver newDriver(String browser){
        WebDriver driver = null;
        if(browser.equalsIgnoreCase("firefox"))
        {
            WebDriverManager.firefoxdriver().setup();
            driver=new FirefoxDriver();
            Logger.getLogger(getClass()).info("FireFox browser launched");
            //logger.info("FireFox browser launched");
        }else if(browser.equalsIgnoreCase("Chrome"))
        {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
            Logger.getLogger(getClass()).info("Chrome browser launched");
        }

        return driver;

    }

    /**
     * Test initialisation , sets driver and browser
     * @throws IOException
     */
    public void initTest()throws IOException{
        info(threadId()+":initTest:setOperationsBundle");
        setOperationsBundle(new OperationsBundle());
        info(threadId()+":initTest:initDriver");
        initDriver();
    }

    public WebDriver getDriver(){return this.driver;}

    protected void setOperationsBundle(OperationsBundle operationsBundle) {
        this.operationsBundle = operationsBundle;
    }
    protected OperationsBundle getOperationsBundle() {
        return this.operationsBundle;
    }

    /**
     * Driver quit method
     */
    public void closeBrowser(){

       try{
           for (WebDriver driver : activeDrivers){
               if(driver!=null)
                   driver.quit();
           }
       }catch (Exception e){
           System.out.println("Exception in quit driver"+e.getMessage());
       }
    }


    @AfterMethod(alwaysRun = true)
    public void screenShotOnFailure(ITestResult result) {
        if(ITestResult.SUCCESS!=result.getStatus()){
            Reporter.setCurrentTestResult(result);
            error("Test method failed : "+ result.getMethod().getMethodName());
            captureScreenshot(result.getMethod().getMethodName());
        }
    }
}
