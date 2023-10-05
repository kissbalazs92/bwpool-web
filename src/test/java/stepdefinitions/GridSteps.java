package stepdefinitions;

import components.Grid;
import io.cucumber.java.en.Then;
import pages.Customer;
import pages.PageWithGrid;
import utils.ScenarioContext;

import static org.testng.Assert.assertEquals;

public class GridSteps extends StepDefinitionBase {

    public GridSteps(ScenarioContext context) {
        super(context);
    }

    @Then("the {string} grid should appear")
    public void theGridShouldAppear(String expectedGridName) {
        Object currentPage = context.getCurrentPage();
        if (currentPage instanceof PageWithGrid) {
            Grid grid = ((PageWithGrid) currentPage).getGrid();
            String actualGridName = grid.getTitle();
            assertEquals(actualGridName, expectedGridName);
        } else {
            throw new RuntimeException("The current page does not have a grid");
        }
    }

}
