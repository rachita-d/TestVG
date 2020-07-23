package framework;

import org.apache.log4j.Logger;

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

    public static void printWarning(Class clientClass, String message) {
        Logger.getLogger(clientClass).warn(threadId()+message);
    }

    public static void printError(Class clientClass, String message) {
        Logger.getLogger(clientClass).error(threadId()+message);
    }

    public static void printDebug(Class clientClass, String message) {
        Logger.getLogger(clientClass).debug(threadId()+message);
    }

    public static String getTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return  sdf.format(new Date());
    }

}
