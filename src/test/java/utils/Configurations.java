package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Configurations {

    private static final Properties properties = new Properties();
    private static final String projectRootPath = System.getProperty("user.dir");
    private static final String confPath = projectRootPath + "/src/test/resources/conf.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(confPath);
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

    public static int getDownloadWaitTime() {
        return Integer.parseInt(properties.getProperty("downloadWaitTime"));
    }

    public static FluentWait<WebDriver> getWait() {
        return new FluentWait<>(DriverManager.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(getWaitTime()))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class);
    }

    public static String getMainPageName() {
        return properties.getProperty("mainPage");
    }

    public static String getDownloadFolder() {
        return projectRootPath + properties.getProperty("download.folder");
    }

    public static String[] getBrowsers() {
        return extractArray(properties.getProperty("browsers"));
    }

    public static String[] getResolutions() {
        return extractArray(properties.getProperty("resolutions"));
    }

    public static boolean isParallelExecution() {
        return Boolean.parseBoolean(properties.getProperty("parallel"));
    }

    public static String getParallelThreads() {
        return properties.getProperty("parallel.threads");
    }

    private static String[] extractArray(String property) {
        return property.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "").split(",");
    }
}
