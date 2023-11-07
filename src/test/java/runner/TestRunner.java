package runner;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import stepdefinitions.CucumberHooks;
import utils.Configurations;
import utils.DriverManager;
import utils.ExtentManager;
import utils.LoggerClass;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Test
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {"html:target/cucumber-reports", "listeners.CustomCucumberListener", "listeners.CustomTestNGListener"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
    public static String browser;
    public static String resolution;
    public static String testName;


    @BeforeSuite
    public void beforeSuite() {
        ExtentManager.setupExtendReport();
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
        List<String> failedTestCases = CucumberHooks.getFailedTestCases();
        if(!failedTestCases.isEmpty()) {
            LoggerClass.errorRoot("Failed Scenarios: " + failedTestCases);
        }
        ExtentManager.extent.flush();
    }
}
