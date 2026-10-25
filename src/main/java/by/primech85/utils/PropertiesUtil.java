package by.primech85.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    static {
        loaderProperties();
    }
    private static void loaderProperties(){
        try(var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("aplication.properties")){
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }
    private  PropertiesUtil() {

    }
}
