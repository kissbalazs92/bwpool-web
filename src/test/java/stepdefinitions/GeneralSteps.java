package stepdefinitions;

import io.cucumber.java.en.Then;
import utils.DriverManager;
import utils.FileHandler;
import utils.ScenarioContext;

import static org.testng.Assert.assertTrue;

public class GeneralSteps extends StepDefinitionBase {
    public GeneralSteps(ScenarioContext context) {
        super(context);
    }

    @Then("file named {string} should be downloaded")
    public void fileNamedShouldBeDownloaded(String fileName) {
        assertTrue(FileHandler.isFileDownloaded(DriverManager.getInstance().getDriver(), fileName));
    }
}
