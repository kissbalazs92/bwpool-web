package components;

import enums.DataType;
import io.cucumber.datatable.DataTable;
import models.CustomerModel;
import models.ToolModel;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.PageWithGrid;
import stepdefinitions.GridSteps;
import utils.Configurations;
import utils.DriverManager;
import utils.ScenarioContext;
import utils.Utilities;

import java.util.*;

public class Grid {

    private final WebDriver driver;

    private final GridDialog gridDialog;

    private PageWithGrid pageToAppearOn;

    @FindBy(xpath = "//button[@aria-label='Add']")
    private WebElement addButton;

    @FindBy(xpath = "//button[@aria-label='Excel Export']")
    private WebElement excelExport;

    @FindBy(id = "Grid_ToolbarSearchBox")
    private WebElement searchBox;

    @FindBy(xpath = "//span[@title='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//h4")
    private WebElement title;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//thead/tr/th[not(contains(@class, 'e-hide'))]")
    private List<WebElement> headers;

    @FindBy(id = "hscroll_2_nav_right")
    private WebElement mobileRightScroll;

    private By nthRowHiddenColumnLocator = By.xpath("//tbody/tr[1]/td[1]");

    public Grid(WebDriver driver, PageWithGrid page) {
        this.driver = driver;
        this.gridDialog = new GridDialog(driver);
        this.pageToAppearOn = page;
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

    public int getRowsCount() {
        return rows.size();
    }

    public String getColumnHeaderId(String rowIndex) {
        nthRowHiddenColumnLocator = By.xpath("//tbody/tr[" + rowIndex + "]/td[1]");
        return driver.findElement(nthRowHiddenColumnLocator).getAttribute("aria-label");
    }

    public String getGridNumberForTool(String rowIndex) {
        By gridNumberCell = By.xpath("//tbody/tr[" + rowIndex + "]/td[2]");
        return driver.findElement(gridNumberCell).getText();
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
            List<CustomerModel> customers = context.getLatestCustomers();
            List<CustomerModel> customersToAdd = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                CustomerModel customer = customers.get(i);
                Utilities.setProperties(customer, diffValues);
                grid.clickAddButton();
                grid.getGridDialog().registerCustomer(customer);
                if (count > 1) {
                    GridSteps gridSteps = new GridSteps(context);
                    context.setModelToBeAddToGrid(customer);
                    gridSteps.iSaveTheForm();
                }
                customersToAdd.add(customer);
                context.setModelToBeAddToGrid(customer);
            }
            context.addCustomerToAllRegisteredCustomers(customersToAdd);
        } else if (modelName.toLowerCase().contains("tool")) {
            List<ToolModel> tools = context.getLatestTools();
            for (int i = 0; i < count; i++) {
                ToolModel tool = tools.get(i);
                Utilities.setProperties(tool, diffValues);
                grid.clickAddButton();
                grid.getGridDialog().registerTool(tool, context);
                if (count > 1) {
                    GridSteps gridSteps = new GridSteps(context);
                    context.setModelToBeAddToGrid(tool);
                    gridSteps.iSaveTheForm();
                }
                context.setModelToBeAddToGrid(tool);
            }
        } else if (modelName.toLowerCase().contains("location")) {
            List<CustomerModel> costumers = context.getLatestCustomers();
            for (int i = 0; i < count; i++) {
                CustomerModel customer = costumers.get(i);
                customer.setLocation();
                grid.clickAddButton();
                grid.getGridDialog().registerLocation(customer.getLocation(), context);
                if (count > 1) {
                    GridSteps gridSteps = new GridSteps(context);
                    gridSteps.iSaveTheForm();
                }
                context.setModelToBeAddToGrid(context.getLatestLocations().get(0));
            }
        } else {
            throw new RuntimeException("Unsupported grid item type: " + modelName);
        }
    }

    public List<List<String>> getGridContent() {
        String xpathOfRowCells = "//*[not(self::tr) and not(contains(@class, 'e-hide')) and not(contains(@aria-label, 'Azonosító')) and (text() and not(normalize-space(.)='') or contains(@class, 'e-icons') or (parent::div[contains(@class, 'e-checkbox-wrapper')] and contains(@class, 'e-frame')))]";
        List<List<String>> gridContent = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> rowRecords = row.findElements(By.xpath("." + xpathOfRowCells));
            List<String> rowContent = new ArrayList<>();
            for (WebElement rowRecord : rowRecords) {
                if (rowRecord.getAttribute("class").contains("e-frame")) {
                    rowContent.add(rowRecord.getAttribute("class").contains("e-check") ? "true" : "false");
                } else {
                    String recordText = rowRecord.getText().trim();
                    rowContent.add(recordText);
                }
            }
            gridContent.add(rowContent);
        }
        return gridContent;
    }

    public boolean checkIfExpectedInGrid(List<List<String>> expectedList) {
        List<List<String>> tempList = new ArrayList<>(expectedList);
        String expectedAsString = Utilities.twoDListToPlainString(tempList);
        for (WebElement row : rows) {
            if(expectedAsString.contains(row.getText().replaceAll("\\s+", " ").replaceFirst("^\\d+\\s+", ""))) {
                return true;
            }
        }
        return tempList.isEmpty();
    }

    public void typeInSearchBox(String text) {
        Utilities.type(searchBox, text);
    }

    public void clickSearchButton() {
        Utilities.click(searchButton);
    }

    public int getColumnIndex(List<String> columnName) {
        for(int i = 0; i < headers.size(); i++) {
            for(int j = 0; j < columnName.size(); j++) {
                if(headers.get(i).getText().equals(columnName.get(j))) {
                    return i;
                }
            }
        }
        throw new RuntimeException("Column not found: " + columnName);
    }

    public WebElement getCellBasedOnColumnHeaderIdAndColumnIndex(String columnHeaderId, int columnIndex) {
        By elementLocator = By.xpath("//td[@aria-label='" + columnHeaderId + "']/../td[not(contains(@class, 'e-hide'))][" + (columnIndex + 1) + "]");
        Configurations.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementLocator));
        return driver.findElement(elementLocator);
    }

    public void clickOnRecordBasedOnColumnHeaderId(String columnHeaderId) {
        By elementLocator = By.xpath("//td[@aria-label='" + columnHeaderId + "']/../td[not(contains(@class, 'e-hide'))][1]");
        Configurations.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementLocator));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Utilities.scrollToElement(driver.findElement(elementLocator));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Utilities.scrollAndClick(driver.findElement(elementLocator));
    }

    public boolean isRecordSelected(String columnHeaderId) {
        By rowLocator = By.xpath("//td[@aria-label='" + columnHeaderId + "']/..");
        Configurations.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));
        WebElement selectedWebElement = driver.findElement(rowLocator);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Utilities.waitUntilElementAttributeEquals(selectedWebElement, "aria-selected", "true");
        String elementAriaSelected = driver.findElement(rowLocator).getAttribute("aria-selected");
        return elementAriaSelected.equals("true");
    }

    public PageWithGrid getPageToAppearOn() {
        return pageToAppearOn;
    }

    public void clickMobileRightScroll() {
        Utilities.click(mobileRightScroll);
        Utilities.waitForElement(searchBox);
    }
}
