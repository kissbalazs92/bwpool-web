package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.DriverManager;

@Test
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    @Parameters({"browser"})
    public void setup(String browser) {
        DriverManager.getInstance().initializeDriver(browser);
    }

    @AfterSuite
    public void tearDown() {
        DriverManager.getInstance().quitDriver();
    }
}
