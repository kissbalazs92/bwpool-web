package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Grid {

    private final WebDriver driver;

    private final GridDialog gridDialog;

    @FindBy(xpath = "//h4")
    private WebElement title;

    public Grid(WebDriver driver) {
        this.driver = driver;
        this.gridDialog = new GridDialog(driver);
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return title.getText();
    }

}
