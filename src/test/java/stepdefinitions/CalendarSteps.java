package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utils.Configurations;
import utils.DriverManager;
import utils.ScenarioContext;

import static org.testng.Assert.assertEquals;

public class CalendarSteps extends StepDefinitionBase {

    public CalendarSteps(ScenarioContext context) {
        super(context);
    }

    @Given("I open the application in a browser")
    public void iOpenTheApplicationInABrowserAt() {
        String url = Configurations.getURL();
        DriverManager.getInstance().getDriver().get(url);
    }

    @Then("the application should open")
    public void theApplicationShouldOpen() {
        String currentURL = DriverManager.getInstance().getDriver().getCurrentUrl();
        assertEquals(currentURL, Configurations.getURL());
    }
}
