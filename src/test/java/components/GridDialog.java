package components;

import models.CustomerModel;
import models.LocationModel;
import models.ToolModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Utilities;

public class GridDialog {
    private final WebDriver driver;

    @FindBy(id = "Grid_dialogEdit_wrapper_title")
    private WebElement dialogTitle;

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

    public GridDialog(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getDialogTitle() {
        return dialogTitle;
    }

    public void fillDialogInput(String text, WebElement element) {
        Utilities.type(element, text);
    }

    public void registerCustomer(CustomerModel customer) {
        fillDialogInput(customer.getName(), name);
        fillDialogInput(customer.getEmail(), email);
        fillDialogInput(customer.getPhone(), phone);
        fillDialogInput(customer.getId(), commentCustomerDialog);
    }

    public void registerTool(ToolModel tool) {
        fillDialogInput(tool.getName(), name);
        fillDialogInput(tool.getCustomerName(), customer);
        fillDialogInput(tool.getCustomer().getLocation().getFullAddress(), location);
        fillDialogInput(tool.getPlatform(), description);
        fillDialogInput(tool.getSerial_number(), commentToolDialog);
    }

    public void registerLocation(LocationModel location) {
        fillDialogInput(location.getCustomerName(), customer);
        fillDialogInput(location.getCity(), city);
        fillDialogInput(location.getZip_code(), zip);
        fillDialogInput(location.getStreet_name(), street);
        fillDialogInput(location.getHouseNumber(), houseNumber);
    }

    public void clickSaveButton() {
        Utilities.click(saveButton);
        Utilities.waitForElementToDisappear(dialogTitle);
    }
}
