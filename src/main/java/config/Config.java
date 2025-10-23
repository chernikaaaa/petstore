package config;

import utils.BaseUtility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties props = new Properties();

    private Config() {
        BaseUtility.getException();
    }

    static {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    public static String baseUrl() {
        return System.getProperty("baseUrl", props.getProperty("baseUrl", "http://localhost:8080"));
    }

}
