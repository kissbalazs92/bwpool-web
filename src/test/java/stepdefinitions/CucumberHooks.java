package stepdefinitions;

import io.cucumber.java.*;
import listeners.CustomCucumberListener;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import runner.TestRunner;
import utils.Configurations;
import utils.DriverManager;
import utils.LoggerClass;

import java.util.Arrays;

public class CucumberHooks {
    @Before
    public void setup(Scenario scenario) {
        String browser = TestRunner.browser;
        String resolution = TestRunner.resolution;
        if (scenario.getSourceTagNames().contains("@" + browser) && scenario.getSourceTagNames().contains("@" + resolution)) {
            LoggerClass.infoSimple("==============================================================");
            LoggerClass.infoSimple("R U N N I N G  S C E N A R I O: " + scenario.getName());
            LoggerClass.infoSimple("==============================================================");
            LoggerClass.infoSimple("B R O W S E R  A N D  R E S O L U T I O N: " + TestRunner.testName);
            LoggerClass.infoSimple("==============================================================");
            DriverManager.getInstance().initializeDriver(browser, resolution);
        } else {
            throw new SkipException("Skipping scenario due to unmatched conditions.");
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if(CustomCucumberListener.stepName != null) {
            LoggerClass.infoSimple("--------------------------------------------------------------");
            if (scenario.isFailed()) {
                LoggerClass.logResultFail("STEP: " + CustomCucumberListener.stepName + " [FAIL]");
            } else {
                LoggerClass.logResultPass("STEP: " + CustomCucumberListener.stepName + " [PASS]");
            }
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if(scenario.getStatus().equals(Status.FAILED)) {
            LoggerClass.infoSimple("--------------------------------------------------------------");
            LoggerClass.logResultFail("--------- Scenario: [FAILED] ---------" );
        }else if(scenario.getStatus().equals(Status.PASSED)) {
            LoggerClass.infoSimple("--------------------------------------------------------------");
            LoggerClass.logResultPass("--------- Scenario: [PASSED] ---------" );
        }
    }


    @After
    public void tearDown() {
        WebDriver driver = DriverManager.getInstance().getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
