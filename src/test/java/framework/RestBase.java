package framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class RestBase extends BaseClass{

    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;
    public static FileInputStream fileInputStream;
    public static Properties properties;
    public static String URI;
    public static String API_Key;


    protected HashMap jsonMap ;


    /**
     * method to create Request Specification. The Base URL is set from the properties file
     * @return
     * @throws IOException
     */
    public RequestSpecification createRequestSpecification()throws IOException {

        fileInputStream=new FileInputStream("./src/test/java/framework/Global.properties");
        properties=new Properties();
        properties.load(fileInputStream);
        URI=properties.getProperty("BaseURI");

        requestSpec = new RequestSpecBuilder().

                            setBaseUri(URI)
                            .build();
        return requestSpec;
    }

    /**
     * Method to get the API_key from properties file.
     * @return
     * @throws IOException
     */
    public String getAPI_Key()throws IOException{
        fileInputStream=new FileInputStream("./src/test/java/framework/Global.properties");
        properties=new Properties();
        properties.load(fileInputStream);
        API_Key=properties.getProperty("API_key");
        return API_Key;
    }

    /**
     * This will load the payload in the Post method when used in Test
     * @param jsonPath
     * @return
     */
    protected InputStream getPayload(String jsonPath){
        InputStream inputStream ;
        ClassLoader classLoader = getClass().getClassLoader();
        inputStream = classLoader.getResourceAsStream(jsonPath);
        if (inputStream!=null)
        return inputStream;
        else return null;
    }

    /**
     * Method to get object value from API response
     * @param jsonResponse - response of the API request in json format
     * @param jsonKey - The Json Key
     * @param jsonValue - Value mapped
     * @return
     * @throws IOException
     */

    public Object getLocatorId(InputStream  jsonResponse ,String jsonKey, String jsonValue) throws IOException{

        Object locatorId;
        jsonMap = new ObjectMapper().readValue(jsonResponse,HashMap.class);
        locatorId=((HashMap)jsonMap.get(jsonKey)).get(jsonValue);
        return locatorId;
    }




}
