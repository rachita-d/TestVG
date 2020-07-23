package tests;

import framework.BaseTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;

public class Test1 extends BaseTest {

    public String city="Hubli";
    public String district="Dharwad";
    @Test
    public void test() throws IOException {

        super.initTest();
        HomePage hp=new HomePage(getOperationsBundle());

        hp.clickOnMore()
                .clickOnWeather()
                    .searchAndSelectCity(city)
                    .verfiyCityExists(city)
                    .clickOnTemperatureOverCity(city)
                    .verifyCityTempInfoShown(district);


    }

    @AfterClass(alwaysRun = true)
    public void closeBrowsw(){
        closeBrowser();
    }
}
