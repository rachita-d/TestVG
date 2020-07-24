package pages;

import framework.BasePage;
import framework.OperationsBundle;
import org.testng.Assert;


public class WeatherPage extends BasePage {

    protected WeatherPage(OperationsBundle operationsBundle){
        super(operationsBundle,"locatorDefs/WeatherPage.json");
    }

    /**
     * Method to search and select city from the search box in weather page
     * @param City- City name
     * @return
     */
    public WeatherPage searchAndSelectCity(String City){
        clickElementByXpath("searchBox");
        sendKeysByXpath("searchBox",City);
        clickElementByRuntimeXpath("cityCheckbox",City);
        captureScreenshot();
        return this;
     }

    /**
     * Method to verify if the searched city exists on the map
     * @param city- City name
     * @return
     */
     public WeatherPage verfiyCityExists(String city){

        Assert.assertTrue(isElementExistsByRuntimeXpath("cityOnMap",city),"City not shown on Map");
        info(city+" Exists in the map");
         captureScreenshot();
        return this;
     }

    /**
     * Method to click on the temperature icon over the city
     * @param city- City name
     * @return
     */
     public WeatherPage clickOnTemperatureOverCity(String city){
        clickElementByRuntimeXpath("CityWeatherBar",city);
         captureScreenshot();
        return this;
     }

    /**
     * Method to verify if the Temperature info box is shown over the city
     * @param City- City Name
     * @return
     */
     public WeatherPage verifyCityTempInfoShown(String City){

        Assert.assertTrue(isElementExistsByRuntimeXpath("cityTempInfo",City));
         captureScreenshot();
        return this;
     }

    /**
     * Method to get the temperature of the city
     * @param city- City Name
     * @param tempUnit - unit of temperature, Values can be Celsius or Fahrenheit
     * @return
     */
     public String getWeatherInfo(String city ,String tempUnit){
        String temp=null;
        if (tempUnit.equalsIgnoreCase("Celsius"))
            temp=getElementTextByRuntimeInput("tempInDeg",city);
        else if(tempUnit.equalsIgnoreCase("Fahrenheit"))
            temp=getElementTextByRuntimeInput("tempInFahrenheit",city);

        info("The temperature for "+city+" is "+temp);
        return temp;
     }
}
