package tests;

import framework.BaseTest;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.HomePage;
import framework.RestBase;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

public class Test1 extends BaseTest {

    public String city="Hubli";
    public String district="Dharwad";

   // @Test(priority = 1)
    public void GetWeatherFromNDTV() throws IOException {

        super.initTest();
        HomePage hp=new HomePage(getOperationsBundle());

        //WebDriverTest
        hp.clickOnMore()
                .clickOnWeather()
                    .searchAndSelectCity(city)
                    .verfiyCityExists(city)
                    .clickOnTemperatureOverCity(city)
                    .verifyCityTempInfoShown(district);


    }

    @Test
    public void getWeatherFromAPI()throws IOException{

        RestBase restBase=new RestBase();
        String apiKey=restBase.getAPI_Key();
        Response response=

        given()
                .log().all()
                .spec(restBase.createRequestSpecification())
                .queryParam("q",city).queryParam("appid",apiKey).
        when()
                .get()
        ;

        System.out.println("Response is:"+response.asString());
        Assert.assertEquals(response.getStatusCode(),200);


    }


    @AfterClass(alwaysRun = true)
    public void closeBrowsw(){
        closeBrowser();
    }
}
