package stepdefinitions;

import components.Grid;
import enums.GridType;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.CustomerModel;
import models.ToolModel;
import pages.PageWithGrid;
import utils.ScenarioContext;
import utils.Utilities;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GridSteps extends StepDefinitionBase {

    public GridSteps(ScenarioContext context) {
        super(context);
    }

    @Then("the {gridType} grid should appear")
    public void theGridShouldAppear(GridType expectedGridType) {
        Object currentPage = context.getCurrentPage();
        if (currentPage instanceof PageWithGrid) {
            Grid grid = ((PageWithGrid) currentPage).getGrid();
            Utilities.waitForElementTextToBe(grid.getTitleElement(), expectedGridType.getTitle());
            String actualGridName = grid.getTitle();
            assertEquals(actualGridName, expectedGridType.getTitle());
        } else {
            throw new RuntimeException("The current page does not have a grid");
        }
    }


    @When("I register {int} {string} data based on the API message")
    public void iRegisterDataBasedOnTheAPIMessage(int count, String modelName) {
        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
        grid.register(count, modelName, null, context);
    }
    @When("I register {int} {string} data based on the API message with the following details")
    public void iRegisterDataBasedOnTheAPIMessageWithTheFollowingDetails(int count, String modelName, DataTable valuesDifferFromApi) {
        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
        grid.register(count, modelName, valuesDifferFromApi, context);
    }

    @And("I save the form")
    public void iSaveTheForm() {
        Object currentPage = context.getCurrentPage();
        if (currentPage instanceof PageWithGrid) {
            ((PageWithGrid) currentPage).getGrid().getGridDialog().clickSaveButton();
        }
        else {
            throw new RuntimeException("The current page does not have a grid");
        } 
    }

    @Then("the {string} should appear in the grid list")
    public void theShouldAppearInTheGridList(String modelName) {
        List<List<String>> expectedValuesInGrid = new ArrayList<>();
        if (modelName.toLowerCase().contains("customer")) {
            List<CustomerModel> lastAddedCustomers = context.getLatestCustomers();
            for (CustomerModel customer : lastAddedCustomers) {
                List<String> values = new ArrayList<>();
                values.add(customer.getName());
                values.add(customer.getEmail());
                values.add(customer.getPhone());
                values.add(customer.getId());
                expectedValuesInGrid.add(values);
            }
        } else if (modelName.toLowerCase().contains("tool")) {
            List<ToolModel> lastAddedTools = context.getLatestTools();
            for (ToolModel tool : lastAddedTools) {
                List<String> values = new ArrayList<>();
                values.add(tool.getName());
                values.add(tool.getCustomerName());
                values.add(tool.getCustomer().getLocation().getFullAddress());
                values.add(tool.getPlatform());
                values.add(tool.getSerial_number());
                expectedValuesInGrid.add(values);
            }
        } else if (modelName.toLowerCase().contains("location")) {
            List<CustomerModel> lastAddedCustomers = context.getLatestCustomers();
            for (CustomerModel customer : lastAddedCustomers) {
                List<String> values = new ArrayList<>();
                values.add(customer.getName());
                values.add(customer.getCity());
                values.add(customer.getZip_code());
                values.add(customer.getStreet_name());
                values.add(customer.getHouseNumber());
                expectedValuesInGrid.add(values);
            }
        }
        List<List<String>> actualValuesInGrid = ((PageWithGrid) context.getCurrentPage()).getGrid().getGridContent();
        System.out.println("Current page: " + context.getCurrentPage());
        System.out.println("Actual values in grid: " + actualValuesInGrid);
        System.out.println("Expected values in grid: " + expectedValuesInGrid);
        assertTrue(actualValuesInGrid.containsAll(expectedValuesInGrid));
    }

    @DataTableType(replaceWithEmptyString = "[blank]")
    public String stringType(String cell) {
        return cell;
    }

    @When("I click on the {string} button")
    public void iClickOnTheButton(String buttonName) {
        Utilities.clickOnText(buttonName);
    }

    @When("I filter in the Search field with {string}")
    public void iFilterInTheSearchFieldWith(String text) {
        PageWithGrid currentPage = (PageWithGrid) context.getCurrentPage();
        if (text.toLowerCase().contains("customer")) {

        }
        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
        grid.typeInSearchBox(text);
        grid.clickSearchButton();
    }
}
