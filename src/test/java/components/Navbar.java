package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Configurations;
import utils.Utilities;

public class Navbar {
    private final WebDriver driver;

    public Navbar(WebDriver driver) {
        this.driver = driver;
    }

    public Object navigateTo(String pageName) {
        By locator = By.xpath("//a[@href='" + pageName + "']");
        Configurations.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
        WebElement navButton = driver.findElement(By.xpath("//a[@href='" + pageName + "']"));
        Utilities.click(navButton);
        try {
            String fullClassName = "pages." + pageName;
            Class<?> clazz = Class.forName(fullClassName);
            return clazz.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to the following page: " + pageName, e);
        }
    }
}

