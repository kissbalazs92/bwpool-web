package stepdefinitions;

import components.Navbar;
import io.cucumber.java.en.When;
import utils.DriverManager;
import utils.ScenarioContext;

public class NavbarSteps extends StepDefinitionBase {

    public NavbarSteps(ScenarioContext context) {
        super(context);
    }

    @When("I navigate to the {string} page")
    public void iNavigateToThePage(String pageName) {
        Navbar navbar = new Navbar(DriverManager.getInstance().getDriver());
        Object currentPage = navbar.navigateTo(pageName);
        context.setCurrentPage(currentPage);
    }
}
