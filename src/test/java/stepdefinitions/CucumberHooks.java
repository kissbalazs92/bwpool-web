package stepdefinitions;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.*;
import listeners.CustomCucumberListener;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import runner.TestRunner;
import utils.Configurations;
import utils.DriverManager;
import utils.LoggerClass;

import java.nio.file.Path;
import java.nio.file.Paths;

import static runner.TestRunner.extent;

public class CucumberHooks {

    @Before
    public void setup(Scenario scenario) {
        String browser = TestRunner.browser;
        String resolution = TestRunner.resolution;
        TestRunner.test = extent.createTest("[" + browser + "] [" + resolution + "] " + scenario.getName());
        if (scenario.getSourceTagNames().contains("@" + browser) && scenario.getSourceTagNames().contains("@" + resolution)) {
            TestRunner.test.assignCategory(browser);
            TestRunner.test.assignCategory(resolution);
            //TODO: hozzáadni az egyéb tageket is amik a scenarional vannak getTag-el, kivéve a res és browsert (használjuk az enumokat a szűrésre)
            LoggerClass.infoSimple("==============================================================");
            LoggerClass.infoSimple("R U N N I N G  S C E N A R I O: " + scenario.getName());
            LoggerClass.infoSimple("==============================================================");
            LoggerClass.infoSimple("B R O W S E R  A N D  R E S O L U T I O N: [" + browser + "] [" + resolution + "]");
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

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if(scenario.getStatus().equals(Status.FAILED)) {
            LoggerClass.infoSimple("--------------------------------------------------------------");
            LoggerClass.logResultFail("===================== Scenario: [FAILED] =====================");
        }else if(scenario.getStatus().equals(Status.PASSED)) {
            LoggerClass.infoSimple("--------------------------------------------------------------");
            LoggerClass.logResultPass("===================== Scenario: [PASSED] =====================" );
        }
    }

    @After(order = 2)
    public void tearDown() {
        Path downloadsFolderPath = Paths.get(Configurations.getDownloadFolder() + "/Export.xlsx");
        //try {
            //Files.delete(downloadsFolderPath);
        //} catch (IOException e) {
            //LoggerClass.errorDetailed("Couldn't delete files from downloads folder", e);
        //}
        WebDriver driver = DriverManager.getInstance().getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
