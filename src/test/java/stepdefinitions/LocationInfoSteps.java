package stepdefinitions;

import enums.DataType;
import enums.ModelsGridProperties;
import enums.PageType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import models.BaseModel;
import models.LocationModel;
import models.ToolModel;
import pages.BasePage;
import pages.LocationInfo;
import utils.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LocationInfoSteps extends StepDefinitionBase {
    public LocationInfoSteps(ScenarioContext context) {
        super(context);
    }

    @Then("the \"{pageType}\" should open")
    public void theShouldOpen(PageType page) {
        BasePage currentPage = (BasePage) context.getCurrentPage();
        assertEquals(currentPage.getClass().getSimpleName(), page.getName());
        assertEquals(DriverManager.getInstance().getDriver().getCurrentUrl(), currentPage.getUrl());
    }

    @And("the file named {string} should contain the \"{dataType}\" data from the LocationInfo page")
    public void theFileNamedShouldContainTheDataOnThe(String fileName, DataType dataType) {
        LocationInfo currentPage = (LocationInfo) context.getCurrentPage();
        String filePath = Configurations.getDownloadFolder() + fileName;
        boolean isXlsxContainsInfoData = false;
        switch (dataType) {
            case TOOL -> isXlsxContainsInfoData = XLSXReader.compareDataWithXLSX(currentPage.getActualToolsInfo(), filePath);
            case LOCATION -> throw new RuntimeException("Not implemented yet");
            case CUSTOMER -> throw new RuntimeException("There are no Customer data in the LocationInfo page");
        }
        assertTrue(isXlsxContainsInfoData);
    }

    @And("the \"{dataType}\" in service should not be on the list")
    public void theInServiceShouldNotBeOnTheList(DataType dataType) {
        LocationInfo currentPage = (LocationInfo) context.getCurrentPage();
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
        HashMap<String, String> expectedTools = currentPage.getActualToolsInfo();
        LocationModel location = context.getLatestLocations().get(0);
        List<ToolModel> registeredToolsToLocation = location.getCustomer().getTools();
        HashMap<String, String> actualTools = new HashMap<>();
        for (ToolModel tool : registeredToolsToLocation) {
            System.out.println(tool.getName());
            if (tool.getInService()) {
                actualTools.put(tool.getGridNumberWhenRegistered(), tool.getName());
                System.out.println("True: " + tool.getName());
                System.out.println(tool.getGridNumberWhenRegistered());
            }
        }
        System.out.println(actualTools);
        System.out.println(expectedTools);
        assertEquals(expectedTools, actualTools);
    }
}
