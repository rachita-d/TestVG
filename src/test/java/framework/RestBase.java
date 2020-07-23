package framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
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


    protected ObjectMapper jsonMap =null;


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

    public void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }

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
        InputStream inputStream = null;
        ClassLoader classLoader = getClass().getClassLoader();
        inputStream = classLoader.getResourceAsStream(jsonPath);
        if (inputStream!=null)
        return inputStream;
        else return null;
    }



}
