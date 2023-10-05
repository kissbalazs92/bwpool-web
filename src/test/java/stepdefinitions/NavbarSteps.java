package stepdefinitions;

import components.Navbar;
import io.cucumber.java.en.When;
import utils.DriverManager;

public class NavbarSteps {
    @When("I navigate to the {string} page")
    public void iNavigateToThePage(String pageName) {
        Navbar navbar = new Navbar(DriverManager.getInstance().getDriver());
        Object currentPage = navbar.navigateTo(pageName);
    }
}
