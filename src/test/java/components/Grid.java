package components;

import io.cucumber.datatable.DataTable;
import models.CustomerModel;
import models.ToolModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.PageWithGrid;
import stepdefinitions.GridSteps;
import utils.ScenarioContext;
import utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {

    private final WebDriver driver;

    private final GridDialog gridDialog;

    @FindBy(xpath = "//button[@aria-label='Add']")
    private WebElement addButton;

    @FindBy(xpath = "//h4")
    private WebElement title;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    public Grid(WebDriver driver) {
        this.driver = driver;
        this.gridDialog = new GridDialog(driver);
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return title.getText();
    }

    public WebElement getTitleElement() {
        return title;
    }

    public GridDialog getGridDialog() {
        return gridDialog;
    }

    public void clickAddButton() {
        Utilities.click(addButton);
        Utilities.waitForElement(getGridDialog().getDialogTitle());
    }

    public void register(int count, String modelName, DataTable valuesDifferFromApi, ScenarioContext context) {
        Object currentPage = context.getCurrentPage();
        Grid grid;
        Map<String, String> diffValues = new HashMap<>();
        if (valuesDifferFromApi != null) {
            diffValues = valuesDifferFromApi.asMap(String.class, String.class);
        }
        if (currentPage instanceof PageWithGrid) {
            grid = ((PageWithGrid) currentPage).getGrid();
        } else {
            throw new RuntimeException("The current page does not have a grid");
        }

        if (modelName.toLowerCase().contains("customer")) {
            List<CustomerModel> costumers = context.getLatestCostumers();
            for (int i = 0; i < count; i++) {
                CustomerModel customer = costumers.get(i);
                Utilities.setProperties(customer, diffValues);
                grid.clickAddButton();
                grid.getGridDialog().registerCustomer(customer);
                if (count > 1) {
                    GridSteps gridSteps = new GridSteps(context);
                    gridSteps.iSaveTheForm();
                }
            }
        } else if (modelName.toLowerCase().contains("tool")) {
            List<ToolModel> tools = context.getLatestTools();
            for (int i = 0; i < count; i++) {
                ToolModel tool = tools.get(i);
                Utilities.setProperties(tool, diffValues);
                grid.clickAddButton();
                grid.getGridDialog().registerTool(tool);
                if (count > 1) {
                    GridSteps gridSteps = new GridSteps(context);
                    gridSteps.iSaveTheForm();
                }
            }
        } else if (modelName.toLowerCase().contains("location")) {
            List<CustomerModel> costumers = context.getLatestCostumers();
            for (int i = 0; i < count; i++) {
                CustomerModel customer = costumers.get(i);
                customer.setLocation();
                grid.clickAddButton();
                grid.getGridDialog().registerLocation(customer.getLocation());
                if (count > 1) {
                    GridSteps gridSteps = new GridSteps(context);
                    gridSteps.iSaveTheForm();
                }
            }
        } else {
            throw new RuntimeException("Unsupported grid item type: " + modelName);
        }
    }

    public List<List<String>> getGridContent() {
        String xpathOfRowCells = "//*[not(self::tr) and not(contains(@class, 'e-hide')) and (text() and not(normalize-space(.)='') or contains(@class, 'e-icons'))]";
        List<List<String>> gridContent = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> rowRecords = row.findElements(By.xpath("." + xpathOfRowCells));
            List<String> rowContent = new ArrayList<>();
            for (WebElement rowRecord : rowRecords) {
                String recordText = rowRecord.getText().trim();
                rowContent.add(recordText);
            }
            gridContent.add(rowContent);
        }
        return gridContent;
    }
}
