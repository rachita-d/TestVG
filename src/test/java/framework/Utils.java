package framework;

import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    

    public static String threadId(){
        return "["+Thread.currentThread().getId()+"]";
    }


    /**
     * Generate random string.
     *
     * @return the string
     */
    public static String generateRandomString() {
        Random rand = new Random(System.nanoTime());
        return Integer.toString((rand.nextInt() & Integer.MAX_VALUE));
    }

    /**
     * Prints the info.
     *
     * @param clientClass the client class
     * @param message     the message
     */
    public static void printInfo(Class clientClass, String message) {
        Logger.getLogger(clientClass).info(threadId()+message);
    }

    /**
     * Prints the warning.
     * @param clientClass
     * @param message
     */
    public static void printWarning(Class clientClass, String message) {
        Logger.getLogger(clientClass).warn(threadId()+message);
    }

    /**
     * Error printer
     * @param clientClass
     * @param message
     */
    public static void printError(Class clientClass, String message) {
        Logger.getLogger(clientClass).error(threadId()+message);
    }

    /**
     * Debugger prints
     * @param clientClass
     * @param message
     */
    public static void printDebug(Class clientClass, String message) {
        Logger.getLogger(clientClass).debug(threadId()+message);
    }

    /**
     * To get timeStamp of execution
     * @return
     */
    public static String getTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return  sdf.format(new Date());
    }

    /**
     * Method to convert the temperature from kelvin to specified unit
     * @param tempUnit - Unit to be converted to, celsius or fahrenheit
     * @param temp - temperature value
     * @return
     */
    public static String changeTempUnitFromKelvin(String tempUnit,String temp){

        double newTemp;
        double convertedTemp=0;
       if(tempUnit.equalsIgnoreCase("Celsius")){
        newTemp= Double.parseDouble(temp);
        convertedTemp=newTemp-273;
       }else if (tempUnit.equalsIgnoreCase("Fahrenheit")){
           newTemp= Integer.parseInt(temp);
           convertedTemp=(newTemp*(9/5))-459;
       }
       return String.valueOf(Math.round(convertedTemp));
    }

    /**
     * The compartor logic to compare the temperatures based on the variance logic specified in the properties file.
     * @param uiTemp- Temperature Value from UI
     * @param apiTemp - temperature value from API
     * @return
     * @throws IOException
     */
    public static Boolean compareTemperatures(String uiTemp, String apiTemp)throws IOException {

        Boolean result=false;
        String uiTemp_v=uiTemp.substring(0,2);
        int uiTemp_int=Integer.parseInt(uiTemp_v);
        int apiTemp_int=Integer.parseInt(apiTemp);
        FileInputStream fileInputStream=new FileInputStream("./src/test/java/framework/Global.properties");
        Properties properties=new Properties();
        properties.load(fileInputStream);
        String varianceType=properties.getProperty("VarianceType");
        int varianceValue;
        if(varianceType.equalsIgnoreCase("Greater_Than")){
            varianceValue=Integer.parseInt(properties.getProperty("Greater_Than"));
            if(uiTemp_int>varianceValue && apiTemp_int>varianceValue)
                result=true;

        }else if(varianceType.equalsIgnoreCase("Less_Than")){
            varianceValue=Integer.parseInt(properties.getProperty("Less_Than"));
            if(uiTemp_int<varianceValue && apiTemp_int<varianceValue)
                result=true;

        }else if(varianceType.equalsIgnoreCase("Difference")){
            varianceValue=Integer.parseInt(properties.getProperty("Difference"));
            if(uiTemp_int-apiTemp_int==Math.abs(varianceValue))
                result=true;

        }else if (varianceType.equalsIgnoreCase("Range")){
            int lowerVarianceValue,upperVarianceValue;
            String[] varianceValue_arr;
            varianceValue_arr=properties.getProperty("Range").split(",");
            lowerVarianceValue=Integer.parseInt(varianceValue_arr[0]);
            upperVarianceValue=Integer.parseInt(varianceValue_arr[1]);
            if((lowerVarianceValue<=uiTemp_int && uiTemp_int<=upperVarianceValue) && (lowerVarianceValue<=apiTemp_int && apiTemp_int<=upperVarianceValue))
                result=true;
        }

        return result;
    }


}
