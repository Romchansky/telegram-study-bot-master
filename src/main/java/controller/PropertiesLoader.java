package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final Properties properties = new Properties();

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public void loadPropertiesFile(String fileName) {
        try (InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inputStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
