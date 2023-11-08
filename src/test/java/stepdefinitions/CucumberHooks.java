package stepdefinitions;

import enums.BrowserType;
import enums.ResolutionType;
import io.cucumber.java.*;
import listeners.CustomCucumberListener;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import runner.TestRunner;
import utils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CucumberHooks {

    private String currentTestName;
    private static final List<String> failedTestCases = new ArrayList<>();
    private int counter = 1;


    @Before
    public void setup(Scenario scenario) {
        BrowserType browserType = BrowserType.valueOf(TestRunner.browser.toUpperCase());
        ResolutionType resolutionType = ResolutionType.valueOf(TestRunner.resolution.toUpperCase());
        currentTestName = "[" + browserType.getName() + "] [" + resolutionType.getResolution() + "] " + scenario.getName();
        ExtentManager.test = ExtentManager.extent.createTest(currentTestName);
        if (scenario.getSourceTagNames().contains("@" + browserType.getName().toLowerCase()) &&
                scenario.getSourceTagNames().contains("@" + resolutionType.getResolution().toLowerCase())) {

            ExtentManager.test.assignCategory(browserType.getName());
            ExtentManager.test.assignCategory(resolutionType.getResolution());

            //TODO: ellenőrzni a több taget is és azokat is attcholni a reporthoz mint címkét

            LoggerClass.infoSimple("==============================================================");
            LoggerClass.infoSimple("R U N N I N G  S C E N A R I O: " + scenario.getName());
            LoggerClass.infoSimple("==============================================================");
            LoggerClass.infoSimple("B R O W S E R  A N D  R E S O L U T I O N: [" + browserType.getName() + "] [" + resolutionType.getResolution() + "]");
            LoggerClass.infoSimple("==============================================================");
            DriverManager.getInstance().initializeDriver(browserType, resolutionType);
        } else {
            throw new SkipException("Skipping scenario due to unmatched conditions.");
        }
    }


    @AfterStep
    public void afterStep(Scenario scenario) {
        if(CustomCucumberListener.stepName != null) {
            if(counter == 1) {
                LoggerClass.infoSimple("--------------------------------------------------------------");
            }
            if (scenario.isFailed()) {
                LoggerClass.logResultFail("STEP: " + CustomCucumberListener.stepName + " [FAIL]");
                ExtentManager.test.log(com.aventstack.extentreports.Status.FAIL, CustomCucumberListener.stepName);
                Utilities.takeScreenshotAndAttachToReport();
            } else {
                LoggerClass.logResultPass("STEP: " + CustomCucumberListener.stepName + " [PASS]");
                ExtentManager.test.log(com.aventstack.extentreports.Status.PASS, CustomCucumberListener.stepName);
            }
            StepLogger.getInstance().writeMessages();
            LoggerClass.infoSimple("--------------------------------------------------------------");
            counter++;
        }
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if(scenario.getStatus().equals(Status.FAILED)) {
            LoggerClass.infoSimple("--------------------------------------------------------------");
            failedTestCases.add(currentTestName);
            LoggerClass.logResultFail("===================== Scenario: [FAILED] =====================");
        }else if(scenario.getStatus().equals(Status.PASSED)) {
            LoggerClass.logResultPass("===================== Scenario: [PASSED] =====================" );
        }
    }

    @After(order = 2)
    public void tearDown() {
        Path downloadsFolderPath = Paths.get(Configurations.getDownloadFolder() + "/Export.xlsx");
        try {
            Files.delete(downloadsFolderPath);
        } catch (IOException e) {
            LoggerClass.errorDetailed("Couldn't delete files from downloads folder", e);
        }
        DriverManager.getInstance().quitDriver();
    }

    public static List<String> getFailedTestCases() {
        return failedTestCases;
    }
}
