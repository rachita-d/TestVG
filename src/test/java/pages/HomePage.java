package pages;

import framework.BasePage;
import framework.OperationsBundle;


public class HomePage extends BasePage {

    public WeatherPage weatherPage;

    public HomePage(OperationsBundle operationsBundle){
        super(operationsBundle,"locatorDefs/HomePage.json");
    }


    public HomePage clickOnMore(){
        clickElementByXpath("moreButton");
        captureScreenshot();
        return this;
    }

    public WeatherPage clickOnWeather(){
        clickElementByXpath("weather");
        captureScreenshot();
        if(null==weatherPage)
        {
            weatherPage=new WeatherPage(getOperationsBundle());
        }
        return weatherPage;
    }
}
