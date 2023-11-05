package stepdefinitions;

import enums.BrowserType;
import enums.ResolutionType;
import io.cucumber.java.*;
import listeners.CustomCucumberListener;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import runner.TestRunner;
import utils.Configurations;
import utils.DriverManager;
import utils.ExtentManager;
import utils.LoggerClass;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CucumberHooks {

    private String currentTestName;
    private static final List<String> failedTestCases = new ArrayList<>();


    @Before
    public void setup(Scenario scenario) {
        // A böngésző és felbontás beállításához szükséges ENUM értékek meghatározása
        BrowserType browserType = BrowserType.valueOf(TestRunner.browser.toUpperCase());
        ResolutionType resolutionType = ResolutionType.valueOf(TestRunner.resolution.toUpperCase());

        // A teszt nevének előállítása
        currentTestName = "[" + browserType.getName() + "] [" + resolutionType.getResolution() + "] " + scenario.getName();

        // ExtentReport inicializálása
        ExtentManager.test = ExtentManager.extent.createTest(currentTestName);

        // Megvizsgálja, hogy a scenario tagjai tartalmazzák-e a böngészőt és felbontást
        if (scenario.getSourceTagNames().contains("@" + browserType.getName().toLowerCase()) &&
                scenario.getSourceTagNames().contains("@" + resolutionType.getResolution().toLowerCase())) {

            ExtentManager.test.assignCategory(browserType.getName());
            ExtentManager.test.assignCategory(resolutionType.getResolution());

            // Itt szűrhetjük a tag-eket az enumok alapján
            // ...

            LoggerClass.infoSimple("==============================================================");
            LoggerClass.infoSimple("R U N N I N G  S C E N A R I O: " + scenario.getName());
            LoggerClass.infoSimple("==============================================================");
            LoggerClass.infoSimple("B R O W S E R  A N D  R E S O L U T I O N: [" + browserType.getName() + "] [" + resolutionType.getResolution() + "]");
            LoggerClass.infoSimple("==============================================================");

            // Driver inicializálása az ENUM értékekkel
            DriverManager.getInstance().initializeDriver(browserType, resolutionType);
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
                ExtentManager.test.log(com.aventstack.extentreports.Status.FAIL, CustomCucumberListener.stepName);
            } else {
                LoggerClass.logResultPass("STEP: " + CustomCucumberListener.stepName + " [PASS]");
                ExtentManager.test.log(com.aventstack.extentreports.Status.PASS, CustomCucumberListener.stepName);
            }
        }
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if(scenario.getStatus().equals(Status.FAILED)) {
            LoggerClass.infoSimple("--------------------------------------------------------------");
            failedTestCases.add(currentTestName);
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

    @AfterAll
    public static void afterAllScenarios() {
        if(!failedTestCases.isEmpty()) {
            LoggerClass.errorRoot("Failed Scenarios: " + failedTestCases);
        }
    }
}
