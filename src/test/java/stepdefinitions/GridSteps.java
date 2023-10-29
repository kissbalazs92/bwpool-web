package stepdefinitions;

import components.Grid;
import enums.DataType;
import enums.GridType;
import enums.ModelsGridProperties;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.BaseModel;
import models.LocationModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.PageWithGrid;
import utils.DriverManager;
import utils.ScenarioContext;
import utils.Utilities;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.*;

public class GridSteps extends StepDefinitionBase {

    public GridSteps(ScenarioContext context) {
        super(context);
    }

    @Then("the \"{gridType}\" should appear")
    public void theShouldAppear(GridType expectedGridType) {
        //fail();
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

    @Then("the \"{dataType}\" data should appear in the grid list")
    public void theDataShouldAppearInTheGridList(DataType dataType) {
        List<BaseModel> models = new ArrayList<>();
        switch (dataType) {
            case CUSTOMER:
                models = new ArrayList<BaseModel>(context.getLatestCustomers());
                break;
            case TOOL:
                models = new ArrayList<BaseModel>(context.getLatestTools());
                break;
            case LOCATION:
                models = new ArrayList<BaseModel>(context.getLatestLocations());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + dataType);
        }
        List<List<String>> expectedValuesInGrid = models.stream()
                .map(BaseModel::extractGridValues)
                .toList();

        List<List<String>> actualValuesInGrid = ((PageWithGrid) context.getCurrentPage()).getGrid().getGridContent();
        Set<List<String>> expectedSet = new HashSet<>(expectedValuesInGrid);
        Set<List<String>> actualSet = new HashSet<>(actualValuesInGrid);
        System.out.println("Expected set: " + expectedSet);
        System.out.println("Actual set: " + actualSet);
        boolean allExpectedValuesFound = actualSet.containsAll(expectedSet);
        softAssert.assertTrue(allExpectedValuesFound);
    }


    @DataTableType(replaceWithEmptyString = "[blank]")
    public String stringType(String cell) {
        return cell;
    }

    @When("I click on the {string} button")
    public void iClickOnTheButton(String buttonName) {
        WebElement button = DriverManager.getInstance().getDriver().findElement(By.xpath("//span[text()='" + buttonName + "']/ancestor::button[1]"));
        button.click();
    }

    @When("I filter for the \"{dataType}\" based on \"{modelsGridProperty}\" in the Search field")
    public void iFilterForTheBasedOnInTheSearchField(DataType dataType, ModelsGridProperties modelsGridProperty) {
        BaseModel model;
        switch (dataType) {
            case CUSTOMER:
                model = context.getLatestCustomers().get(0);
                System.out.println(model);
                break;
            case TOOL:
                model = context.getLatestTools().get(0);
                System.out.println(model);
                break;
            case LOCATION:
                model = context.getLatestLocations().get(0);
                System.out.println(model);
                System.out.println(((LocationModel) model).getCustomer());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + dataType);
        }
        String textToSearch = model.getTextToSearch(modelsGridProperty, model);
        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
        int gridRows = grid.getGridContent().size();
        grid.typeInSearchBox(textToSearch);
        grid.clickSearchButton();
        Utilities.waitForRowCountToDecrease(grid, gridRows);
    }


    @Then("the grid should successfully filter for the \"{dataType}\" with \"{modelsGridProperty}\"")
    public void theGridShouldSuccessfullyFilterForTheWith(DataType dataType, ModelsGridProperties modelsGridProperty) {
        List<BaseModel> models = new ArrayList<>();
        switch (dataType) {
            case CUSTOMER:
                models = new ArrayList<BaseModel>(context.getLatestCustomers());
                break;
            case TOOL:
                models = new ArrayList<BaseModel>(context.getLatestTools());
                break;
            case LOCATION:
                models = new ArrayList<BaseModel>(context.getLatestLocations());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + dataType);
        }
        List<List<String>> expectedValuesInGrid = models.stream()
                .map(BaseModel::extractGridValues)
                .toList();

        List<List<String>> actualValuesInGrid = ((PageWithGrid) context.getCurrentPage()).getGrid().getGridContent();
        Set<List<String>> expectedSet = new HashSet<>(expectedValuesInGrid);
        Set<List<String>> actualSet = new HashSet<>(actualValuesInGrid);
        boolean allExpectedValuesFound = actualSet.containsAll(expectedSet);
        softAssert.assertTrue(allExpectedValuesFound);
    }
}
