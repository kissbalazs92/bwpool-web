package components;

import models.CustomerModel;
import models.LocationModel;
import models.ToolModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ScenarioContext;
import utils.Utilities;

import java.util.List;

public class GridDialog {
    private final WebDriver driver;

    @FindBy(id = "Grid_dialogEdit_wrapper_title")
    private WebElement dialogTitle;

    @FindBy(xpath = "//*[@id='Grid_dialogEdit_wrapper_title']/h4")
    private WebElement titleText;

    @FindBy(xpath = "//button[text()='Save']")
    private WebElement saveButton;

    //Customer Dialog and Tool Dialog inputs
    @FindBy(id = "name")
    private WebElement name;

    //Location Dialog and Tool Dialog inputs
    @FindBy(xpath = "//input[@placeholder = 'Ügyfél']")
    private WebElement customer;

    //Customer Dialog inputs
    @FindBy(id = "email")
    private WebElement email;

    @FindBy(xpath = "//input[contains(@id, 'numeric-')]")
    private WebElement phone;

    @FindBy(id = "comment")
    private WebElement commentCustomerDialog;

    //Location Dialog inputs
    @FindBy(id = "varos")
    private WebElement city;

    @FindBy(id = "zip")
    private WebElement zip;

    @FindBy(id = "utca")
    private WebElement street;

    @FindBy(id = "szam")
    private WebElement houseNumber;

    //Tool Dialog inputs
    @FindBy(xpath = "//input[@placeholder = 'Telephely']")
    private WebElement location;

    @FindBy(id = "Desc")
    private WebElement description;

    @FindBy(id = "Comm")
    private WebElement commentToolDialog;

    @FindBy(xpath = "//*[contains(@class, 'e-dropdownbase')]/parent::div")
    private WebElement dropdownList;

    public GridDialog(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getDialogTitle() {
        return dialogTitle;
    }

    public String getDialogTitleText() {
        return dialogTitle.getText();
    }

    public void fillDialogInput(String text, WebElement element) {
        Utilities.type(element, text);
    }

    public void fillDialogDropdown(String text, WebElement element) {
        Utilities.typeInDropdown(element, text, dropdownList);
    }

    public void registerCustomer(CustomerModel customer) {
        fillDialogInput(customer.getName(), name);
        fillDialogInput(customer.getEmail(), email);
        fillDialogInput(customer.getPhone(), phone);
        fillDialogInput(customer.getId(), commentCustomerDialog);
    }

    public void registerTool(ToolModel tool, ScenarioContext context) {
        LocationModel latestLocations = context.getLatestLocations().get(0);
        tool.setCustomer(latestLocations.getCustomer());
        tool.getCustomer().addTool(tool);
        fillDialogInput(tool.getName(), name);
        fillDialogDropdown(tool.getCustomerName(), customer);
        fillDialogDropdown(tool.getFullAddress(), location);
        fillDialogInput(tool.getPlatform(), description);
        fillDialogInput(tool.getSerial_number(), commentToolDialog);
    }

    public void registerLocation(LocationModel location, ScenarioContext context) {
        fillDialogDropdown(location.getCustomerName(), customer);
        fillDialogInput(location.getCity(), city);
        fillDialogInput(location.getZip_code(), zip);
        fillDialogInput(location.getStreet_name(), street);
        fillDialogInput(location.getHouseNumber(), houseNumber);
        context.setLatestLocations(List.of(location));
    }

    public void clickSaveButton() {
        Utilities.click(saveButton);
        Utilities.waitForElementToDisappear(dialogTitle);
    }

    public WebElement getDropdownList() {
        return dropdownList;
    }
}
