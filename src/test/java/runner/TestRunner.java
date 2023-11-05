package runner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.BeforeAll;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.Configurations;
import utils.DriverManager;
import utils.LoggerClass;

import java.lang.reflect.Method;
import java.util.Arrays;

@Test
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {"html:target/cucumber-reports", "listeners.CustomCucumberListener", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    public static String browser;
    public static String resolution;
    public static String testName;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void beforeSuite() {
        // Útvonal, ahol a jelentést szeretnéd elmenteni
        String reportPath = System.getProperty("user.dir") + "/test-output/extentReport.html";

        // A jelentés konfigurációja
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // További konfigurációk, például tesztjelentés címének megadása
        extent.setSystemInfo("Host Name", "Test Machine");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Test User");
        LoggerClass.infoSimple("--------------------------------------------------------------");
        LoggerClass.infoSimple(" T E S T S");
        LoggerClass.infoSimple("--------------------------------------------------------------");
        LoggerClass.infoRoot("Running Tests With Configurations:");
        LoggerClass.infoSimple("\t- Browsers: " + Arrays.toString(Configurations.getBrowsers()));
        LoggerClass.infoSimple("\t- Resolutions: " + Arrays.toString(Configurations.getResolutions()) + "\n");
    }

    @BeforeMethod
    @Parameters({"browser", "resolution"})
    public void logTestName(String  browser, String resolution, ITestContext context) {
        TestRunner.browser = browser;
        TestRunner.resolution = resolution;
        testName = context.getCurrentXmlTest().getName();

    }

    @AfterSuite
    public void flushExtentReports() {
        extent.flush();
    }
}
