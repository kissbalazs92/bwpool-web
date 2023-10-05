package stepdefinitions;

import io.cucumber.java.en.Given;
import utils.DriverManager;

public class CalendarSteps {
    @Given("I open the application in a browser at {string}")
    public void iOpenTheApplicationInABrowserAt(String url) {
        DriverManager.getInstance().getDriver().get(url);
    }
}
