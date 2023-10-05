package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Utilities;

public class Navbar {
    private final WebDriver driver;

    public Navbar(WebDriver driver) {
        this.driver = driver;
    }

    public Object navigateTo(String pageName) {
        WebElement navButton = driver.findElement(By.linkText(pageName));
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

