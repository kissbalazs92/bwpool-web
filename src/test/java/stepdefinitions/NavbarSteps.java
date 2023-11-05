package stepdefinitions;

import components.Navbar;
import enums.PageType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.BasePage;
import utils.DriverManager;
import utils.ScenarioContext;

import static org.testng.Assert.assertEquals;

public class NavbarSteps extends StepDefinitionBase {

    public NavbarSteps(ScenarioContext context) {
        super(context);
    }

    @When("I navigate to the \"{pageType}\"")
    public void iNavigateToThe(PageType pageName) {
        Object currentPage = ((BasePage) context.getCurrentPage()).getNavbar().navigateTo(pageName.getName());
        context.setCurrentPage(currentPage);
    }

    @Given("I am on the {string} page")
    public void iAmOnThePage(String expectedPageName) {
        String actualCurrentPageName = context.getCurrentPage().getClass().getSimpleName();
        assertEquals(actualCurrentPageName, expectedPageName);
    }
}
