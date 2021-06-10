package ua.goit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final String PROPERTIES_FILE_NAME = "application.properties";

    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        try (InputStream inputStream = getInputStream(PROPERTIES_FILE_NAME)) {
            PROPERTIES.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static String getProperty(String name) {
        return PROPERTIES.getProperty(name);
    }

    public static InputStream getInputStream(String propertiesFileName) {
        return PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesFileName);
    }

}
