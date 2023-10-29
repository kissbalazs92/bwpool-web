package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.CustomWebDriverListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.util.HashMap;
import java.util.Map;

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

    public void initializeDriver(String browserType, String resolution) {
        String downloadPath = Configurations.getDownloadFolder();
        switch (browserType.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", downloadPath);
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.addArguments("--headless"); // Headless mód bekapcsolása
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.download.dir", downloadPath);
                profile.setPreference("browser.download.folderList", 2);
                profile.setPreference("browser.download.useDownloadDir", true);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream,application/vnd.ms-excel");
                firefoxOptions.setProfile(profile);
                //firefoxOptions.addArguments("--headless"); // Headless mód bekapcsolása
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserType);
        }
        CustomWebDriverListener webDriverListener = new CustomWebDriverListener();
        EventFiringDecorator<WebDriver> decorator = new EventFiringDecorator<>(webDriverListener);
        driver = decorator.decorate(driver);
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}