package stepdefinitions;

import components.Grid;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CustomerModel;
import models.ToolModel;
import pages.PageWithGrid;
import utils.ScenarioContext;

import java.util.List;

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

    @When("I register {int} {string} based on the API message")
    public void iRegisterBasedOnTheAPIMessage(int count, String modelName) {
        switch(modelName.toLowerCase()) {
            case "customer":
                List<CustomerModel> costumers = context.getLatestCostumers();
                for (int i = 0; i < count; i++) {
                    CustomerModel customer = costumers.get(i);
                    registerCustomer(customer);
                }
                break;
            case "tool":
                List<ToolModel> tools = context.getLatestTools();
                for (int i = 0; i < count; i++) {
                    ToolModel tool = tools.get(i);
                    registerTool(tool);
                }
                break;
            default:
                throw new RuntimeException("Unsupported entity type: " + modelName);
        }
    }
}
