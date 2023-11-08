package components;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Configurations;
import utils.LoggerClass;
import utils.Utilities;

public class Navbar {
    private final WebDriver driver;

    @FindBy(xpath = "//button[@title='Navigation menu']")
    private WebElement mobileMenuButton;

    public Navbar(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Object navigateTo(String pageName) {
        By locator = By.xpath("//a[@href='" + pageName + "']");
        //Configurations.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        Utilities.click(driver.findElement(locator));
        try {
            String fullClassName = "pages." + pageName;
            Class<?> clazz = Class.forName(fullClassName);
            return clazz.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to the following page: " + pageName, e);
        }
    }

    public void clickMobileMenuButton() {
        Utilities.click(mobileMenuButton);
    }
}

