package stepdefinitions;

import components.Grid;
import components.GridDialog;
import enums.DataType;
import enums.GridType;
import enums.ModelsGridProperties;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.BaseModel;
import models.LocationModel;
import models.ToolModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.LocationInfo;
import pages.PageWithGrid;
import utils.Configurations;
import utils.DriverManager;
import utils.ScenarioContext;
import utils.Utilities;

import java.util.*;

import static org.testng.Assert.*;

public class GridSteps extends StepDefinitionBase {

    public GridSteps(ScenarioContext context) {
        super(context);
    }

    @Then("the \"{gridType}\" should appear")
    public void theShouldAppear(GridType expectedGridType) {
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
        Grid grid;
        if (currentPage instanceof PageWithGrid) {
            grid = ((PageWithGrid) currentPage).getGrid();
            grid.getGridDialog().clickSaveButton();
            BaseModel model = context.getModelToBeAddToGrid();
            Utilities.waitForTextToAppear(model.getPartnerName());
        }
        else {
            throw new RuntimeException("The current page does not have a grid");
        }
    }

    @Then("the \"{dataType}\" data should appear in the grid list")
    public void theDataShouldAppearInTheGridList(DataType dataType) {
        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
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
        
        softAssert.assertTrue(grid.checkIfExpectedInGrid(expectedValuesInGrid));
        for (int i = 0; i < models.size(); i++) {
            models.get(i).setColumnHeaderId(grid.getColumnHeaderId(String.valueOf(i+1)));
            if(models.get(i) instanceof ToolModel) {
                ((ToolModel) models.get(i)).setGridNumberWhenRegistered(grid.getGridNumberForTool(String.valueOf(i+1)));
            }
        }
    }


    @DataTableType(replaceWithEmptyString = "[blank]")
    public String stringType(String cell) {
        return cell;
    }

    @When("I click on the {string} button")
    public void iClickOnTheButton(String buttonName) {
        WebElement button = DriverManager.getInstance().getDriver().findElement(By.xpath("//span[text()='" + buttonName + "']/ancestor::button[1]"));
        Utilities.click(button);
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
        int gridRows = grid.getRowsCount();
        grid.typeInSearchBox(textToSearch);
        grid.clickSearchButton();
        Utilities.waitForRowCountToDecrease(grid, gridRows);
    }


    @Then("the grid should successfully filter for the \"{dataType}\" with \"{modelsGridProperty}\"")
    public void theGridShouldSuccessfullyFilterForTheWith(DataType dataType, ModelsGridProperties modelsGridProperty) {
        List<BaseModel> models;
        switch (dataType) {
            case CUSTOMER:
                models = new ArrayList<>(context.getLatestCustomers());
                break;
            case TOOL:
                models = new ArrayList<>(context.getLatestTools());
                break;
            case LOCATION:
                models = new ArrayList<>(context.getLatestLocations());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + dataType);
        }
        List<List<String>> expectedValuesInGrid = models.stream()
                .map(BaseModel::extractGridValues)
                .toList();

        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
        //List<List<String>> actualValuesInGrid = grid.getGridContent();
        softAssert.assertTrue(grid.checkIfExpectedInGrid(expectedValuesInGrid));
        List<String> columnName = modelsGridProperty.getColumnName();


        //softAssert.assertTrue(Utilities.areAllElementsSameAtColumn(actualValuesInGrid, grid.getColumnIndex(columnName)));
    }

    @When("I click on the URL in \"{gridType}\" in the {string} field")
    public void iClickOnTheURLInInTheField(GridType gridType, String fieldName) {
        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
        BaseModel model;
        switch (gridType) {
            case CUSTOMER:
                model = context.getLatestCustomers().get(0);
                break;
            case TOOL:
                model = context.getLatestTools().get(0);
                break;
            case LOCATION:
                model = context.getLatestLocations().get(0);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported grid type: " + gridType);
        }
        model.getColumnHeaderId();
        System.out.println("Column Header Id: " + model.getColumnHeaderId());
        WebElement urlCell = grid.getCellBasedOnColumnHeaderIdAndColumnIndex(model.getColumnHeaderId(), grid.getColumnIndex(new ArrayList<>(Collections.singleton(fieldName))));
        Utilities.scrollAndClick(urlCell);
        context.setCurrentPage(new LocationInfo(DriverManager.getInstance().getDriver(), model.getColumnHeaderId().replaceAll("[^0-9]", "")));
    }

    @And("I select a previously recorded \"{dataType}\"")
    public void iSelectAPreviouslyRecorded(DataType dataType) {
        BaseModel model;
        switch (dataType) {
            case CUSTOMER:
                model = context.getLatestCustomers().get(0);
                break;
            case TOOL:
                model = context.getLatestTools().get(0);
                break;
            case LOCATION:
                model = context.getLatestLocations().get(0);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + dataType);
        }
        ((PageWithGrid) context.getCurrentPage()).getGrid().clickOnRecordBasedOnColumnHeaderId(model.getColumnHeaderId());
    }

    @Then("the \"{dataType}\" should be selected")
    public void theShouldBeSelected(DataType dataType) {
        BaseModel model;
        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
        switch (dataType) {
            case CUSTOMER:
                model = context.getLatestCustomers().get(0);
                break;
            case TOOL:
                model = context.getLatestTools().get(0);
                break;
            case LOCATION:
                model = context.getLatestLocations().get(0);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + dataType);
        }
        softAssert.assertTrue(grid.isRecordSelected(model.getColumnHeaderId()));
    }

    @Then("the \"{dataType}\" data dialog should open")
    public void theDataDialogShouldOpen(DataType dataType) {
        GridDialog gridDialog = ((PageWithGrid) context.getCurrentPage()).getGrid().getGridDialog();
        Configurations.getWait().until(ExpectedConditions.visibilityOf(gridDialog.getDialogTitle()));
        assertEquals(gridDialog.getDialogTitleText(), dataType.getName());
    }

    @And("I check the {string} checkbox")
    public void iCheckTheCheckbox(String checkBoxLabel) {
        WebElement labelElement = DriverManager.getInstance().getDriver().findElement(By.xpath("//label/span[text()='" + checkBoxLabel + "']"));
        Utilities.click(labelElement);
    }

    @Then("the in service should be {string} for the \"{dataType}\" in the grid")
    public void theInServiceShouldBeForTheInTheGrid(String condition, DataType dataType) {
        Grid grid = ((PageWithGrid) context.getCurrentPage()).getGrid();
        BaseModel model;
        switch (dataType) {
            case CUSTOMER:
                model = context.getLatestCustomers().get(0);
                break;
            case TOOL:
                model = context.getLatestTools().get(0);
                break;
            case LOCATION:
                model = context.getLatestLocations().get(0);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported data type: " + dataType);
        }
        String actualValue = ModelsGridProperties.TOOL_SERVICE.extractValueFromGrid(grid, model.getColumnHeaderId());
        if(model instanceof ToolModel) {
            ((ToolModel) model).setInService(Boolean.parseBoolean(condition));
            System.out.println("In Service: " + ((ToolModel) model).getName());
            System.out.println("In Service: " + ((ToolModel) model).getGridNumberWhenRegistered());
        }
        assertEquals(actualValue, condition);

    }
}
