package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.*;
import java.time.Duration;
import java.util.Properties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Configurations {

    private static final Properties configProperties = new Properties();
    private static final String projectRootPath = System.getProperty("user.dir");
    private static final String confPath = projectRootPath + "/src/test/resources/conf.properties";
    private static final String screenshotsPath = projectRootPath + "/src/test/resources/screenshots";
    private static final String separator = System.getProperty("file.separator");

    static {
        try {
            FileInputStream fis = new FileInputStream(confPath);
            configProperties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the configuration file", e);
        }
    }

    public static String getURL() {
        return configProperties.getProperty("url");
    }

    public static int getWaitTime() {
        return Integer.parseInt(configProperties.getProperty("waitTime"));
    }

    public static int getDownloadWaitTime() {
        return Integer.parseInt(configProperties.getProperty("downloadWaitTime"));
    }

    public static FluentWait<WebDriver> getWait() {
        return new FluentWait<>(DriverManager.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(getWaitTime()))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class);
    }

    public static String getMainPageName() {
        return configProperties.getProperty("mainPage");
    }

    public static String getDownloadFolder() {
        return projectRootPath + configProperties.getProperty("download.folder").replace("\\", separator);
    }

    public static String[] getBrowsers() {
        return extractArray(configProperties.getProperty("browsers"));
    }

    public static String[] getResolutions() {
        return extractArray(configProperties.getProperty("resolutions"));
    }

    public static boolean isParallelExecution() {
        return Boolean.parseBoolean(configProperties.getProperty("parallel"));
    }

    public static String getParallelThreads() {
        return configProperties.getProperty("parallel.threads");
    }

    private static String[] extractArray(String property) {
        return property.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "").split(",");
    }

    public static String getConfPath() {
        return confPath;
    }

    public static String getDesktopResolution() {
        return configProperties.getProperty("resolution.desktop");
    }

    public static String getMobileResolution() {
        return configProperties.getProperty("resolution.mobile");
    }

    public static String getLaptopResolution() {
        return configProperties.getProperty("resolution.laptop");
    }

    public static String getTabletResolution() {
        return configProperties.getProperty("resolution.tablet");
    }

    public static String getReportPath() {
        return projectRootPath + configProperties.getProperty("report.folder").replace("\\", separator) + configProperties.getProperty("report.name") + getTimeStamp() + ".html";
    }

    public static String getTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
        return now.format(formatter);
    }

    public static String getScreenshotPath() {
        return screenshotsPath.replace("\\", separator);
    }
}
