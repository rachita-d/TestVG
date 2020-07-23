package framework;

import org.openqa.selenium.WebDriver;

public class OperationsBundle extends BaseTest {

    private WebDriver driver;

    public WebDriver getDriver() {
        return this.driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

}
