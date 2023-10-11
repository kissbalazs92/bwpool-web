package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class DriverManager {

    private static volatile DriverManager instance = null;
    private WebDriver driver;

    private DriverManager() {

    }

    public static DriverManager getInstance() {
        if (instance == null) {
            synchronized (DriverManager.class) {
                if (instance == null) {
                    instance = new DriverManager();
                }
            }
        }
        return instance;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void initializeDriver(String browserType) {
        String downloadPath = Configurations.getDownloadFolder();
        switch (browserType.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("download.default_directory=" + downloadPath);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.download.dir", downloadPath);
                profile.setPreference("browser.download.folderList", 2);
                firefoxOptions.setProfile(profile);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserType);
        }
    }


    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
