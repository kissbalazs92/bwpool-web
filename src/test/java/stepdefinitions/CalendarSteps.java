package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
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
        WebDriver driver = DriverManager.getInstance().getDriver();
        String currentURL = driver.getCurrentUrl();
        String mainPageName = Configurations.getMainPageName();
        try {
            Class<?> clazz = Class.forName("pages." + mainPageName);
            context.setCurrentPage(clazz.getConstructor(WebDriver.class).newInstance(driver));
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to open application, expected main page " + mainPageName, e);
        }
        assertEquals(currentURL, Configurations.getURL());
    }
}
