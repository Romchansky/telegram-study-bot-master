package utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesLoader {

    private static final Properties properties = new Properties();

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    @SneakyThrows
    public void loadPropertiesFile(String fileName) {
        try (InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inputStream);
        }
        catch (IOException ex) {
            log.error("file not load");

        }
    }
}
