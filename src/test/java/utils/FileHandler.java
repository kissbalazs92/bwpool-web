package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class FileHandler {

    public static boolean isFileDownloaded(WebDriver driver, String fileName) {
        String downloadPath = Configurations.getDownloadFolder();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Configurations.getDownloadWaitTime()));
        ExpectedCondition<Boolean> fileExists = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return Files.exists(Paths.get(downloadPath, fileName));
            }
        };
        return wait.until(fileExists);
    }

}
