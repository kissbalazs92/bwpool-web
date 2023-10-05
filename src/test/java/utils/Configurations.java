package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configurations {

    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("path_to_your_config.properties");
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the configuration file", e);
        }
    }

    public static String getURL() {
        return properties.getProperty("url");
    }

    public static int getWaitTime() {
        return Integer.parseInt(properties.getProperty("waitTime"));
    }

    // További metódusok más konfigurációs beállításokhoz
}
