package pages;

import framework.BasePage;
import framework.OperationsBundle;
import org.testng.Assert;


public class WeatherPage extends BasePage {

    protected WeatherPage(OperationsBundle operationsBundle){
        super(operationsBundle,"locatorDefs/WeatherPage.json");
    }

    public WeatherPage searchAndSelectCity(String City){
        clickElementByXpath("searchBox");
        sendKeysByXpath("searchBox",City);
        clickElementByRuntimeXpath("cityCheckbox",City);
        captureScreenshot();
        return this;
     }


     public WeatherPage verfiyCityExists(String city){

        Assert.assertTrue(isElementExistsByRuntimeXpath("cityOnMap",city),"City not shown on Map");
        info(city+" Exists in the map");
         captureScreenshot();
        return this;
     }

     public WeatherPage clickOnTemperatureOverCity(String city){
        clickElementByRuntimeXpath("CityWeatherBar",city);
         captureScreenshot();
        return this;
     }

     public WeatherPage verifyCityTempInfoShown(String City){

        Assert.assertTrue(isElementExistsByRuntimeXpath("cityTempInfo",City));
         captureScreenshot();
        return this;
     }
}
