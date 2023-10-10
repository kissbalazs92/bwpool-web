package stepdefinitions;

import components.Navbar;
import enums.PageType;
import io.cucumber.java.en.When;
import utils.DriverManager;
import utils.ScenarioContext;

public class NavbarSteps extends StepDefinitionBase {

    public NavbarSteps(ScenarioContext context) {
        super(context);
    }

    @When("I navigate to the {pageType} page")
    public void iNavigateToThePage(PageType pageName) {
        Navbar navbar = new Navbar(DriverManager.getInstance().getDriver());
        Object currentPage = navbar.navigateTo(pageName.name());
        context.setCurrentPage(currentPage);
    }
}
