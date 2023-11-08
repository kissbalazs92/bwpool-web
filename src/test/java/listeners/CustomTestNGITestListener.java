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
        String testMethodName = result.getName();
        if(DriverManager.getInstance().getDriver() instanceof TakesScreenshot) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverManager.getInstance().getDriver();
            File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            try {
                String screenshotPath = "./screenshots/" + testMethodName + ".png";
                FileHandler.copy(screenshotFile, new File(screenshotPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

