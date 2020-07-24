package tests;


import framework.BaseTest;
import framework.Utils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.HomePage;
import framework.RestBase;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.io.InputStream;

public class WeatherComparison extends BaseTest {

    public String city="Hubli";
    public String district="Dharwad";

    public String UItemp;
    public String apiTemp;
    public String convertedTempForAPI;
    public String tempUnit="celsius";


    @Test
    public void compareWeather() throws IOException {

        super.initTest();
        HomePage hp=new HomePage(getOperationsBundle());

        //UI Test
        UItemp=hp.clickOnMore()
                .clickOnWeather()
                    .searchAndSelectCity(city)
                    .verfiyCityExists(city)
                    .clickOnTemperatureOverCity(city)
                    .verifyCityTempInfoShown(district)
                    .getWeatherInfo(city,tempUnit);

        //API test
        RestBase restBase=new RestBase();
        String apiKey=restBase.getAPI_Key();
        Response response=

        given()
                .spec(restBase.createRequestSpecification())
                .queryParam("q",city).queryParam("appid",apiKey).
        when()
                .get().
        then()
                .extract()
                .response()
        ;
        Assert.assertEquals(response.getStatusCode(),200);

        InputStream jsonAsInputStream=response.asInputStream();
        apiTemp=restBase.getLocatorId(jsonAsInputStream,"main","temp").toString();

        convertedTempForAPI=Utils.changeTempUnitFromKelvin(tempUnit,apiTemp);

        info("Temperature of "+city+" from API is "+convertedTempForAPI+"degree "+tempUnit);
        info("Temperature of "+city+" from WebUI is "+UItemp+"degree "+tempUnit);

        Assert.assertTrue(Utils.compareTemperatures(UItemp,convertedTempForAPI),"The Temperatures from UI and API dont match the variance logic");
    }


    @AfterClass(alwaysRun = true)
    public void closeBrowswr(){
        closeBrowser();
    }
}
