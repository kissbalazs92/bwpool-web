package utils;

import enums.BrowserType;
import enums.ResolutionType;
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

    public void initializeDriver(BrowserType browserType, ResolutionType resolutionType) {
        String downloadPath = Configurations.getDownloadFolder();
        String[] resolutionValues = resolutionType.getResolutionValue().split("x");
        int width = Integer.parseInt(resolutionValues[0]);
        int height = Integer.parseInt(resolutionValues[1]);

        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", downloadPath);
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments(String.format("--window-size=%d,%d", width, height));
                driver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.download.dir", downloadPath);
                profile.setPreference("browser.download.folderList", 2);
                profile.setPreference("browser.download.useDownloadDir", true);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream,application/vnd.ms-excel");
                firefoxOptions.setProfile(profile);
                firefoxOptions.addArguments(String.format("--window-size=%d,%d", width, height));
                firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserType.getName());
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