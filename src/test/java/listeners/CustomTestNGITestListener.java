package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;
import utils.DriverManager;

import java.io.File;

public class CustomTestNGITestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // A hibás teszt nevének lekérése
        String testMethodName = result.getName();

        if(DriverManager.getInstance().getDriver() instanceof TakesScreenshot) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverManager.getInstance().getDriver();
            File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            try {
                // A képernyőkép fájlba mentése
                String screenshotPath = "./screenshots/" + testMethodName + ".png";
                FileHandler.copy(screenshotFile, new File(screenshotPath));
                System.out.println("Screenshot captured for test case: " + testMethodName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Implementáld a többi ITestListener metódust, ha szükséges...
}

