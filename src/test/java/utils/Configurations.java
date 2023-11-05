package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.*;
import java.time.Duration;
import java.util.Properties;

public class Configurations {

    private static final Properties configProperties = new Properties();
    private static final String projectRootPath = System.getProperty("user.dir");
    private static final String confPath = projectRootPath + "/src/test/resources/conf.properties";
    private static final String extentConfPath = projectRootPath + "/src/test/resources/extent.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(confPath);
            configProperties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the configuration file", e);
        }
    }

    public static void setupExtentProperties() {
        Properties extentProperties = new Properties();
        try (InputStream input = new FileInputStream(extentConfPath)) {
            extentProperties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extentProperties.setProperty("basefolder.name", configProperties.getProperty("report.folder").replaceAll("\\\\", "/").replaceFirst("/", "") + configProperties.getProperty("report.name"));
        extentProperties.setProperty("systeminfo.os", System.getProperty("os.name"));
        try (OutputStream extentOutput = new FileOutputStream(extentConfPath)) {
            extentProperties.store(extentOutput, null);
        } catch (IOException e) {
            e.printStackTrace();
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
        return projectRootPath + configProperties.getProperty("download.folder");
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
        return configProperties.getProperty("desktop.resolution");
    }

    public static String getMobileResolution() {
        return configProperties.getProperty("mobile.resolution");
    }

    public static String getLaptopResolution() {
        return configProperties.getProperty("laptop.resolution");
    }

    public static String getTabletResolution() {
        return configProperties.getProperty("tablet.resolution");
    }
}
