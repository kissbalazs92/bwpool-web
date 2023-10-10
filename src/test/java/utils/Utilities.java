package utils;

import models.CustomerModel;
import models.ParentModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Map;

public class Utilities {

    private static final WebDriver driver = DriverManager.getInstance().getDriver();

    public static void click(WebElement element) {
        Wait<WebDriver> wait = Configurations.getWait();
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public static void waitForElement(WebElement element) {
        Wait<WebDriver> wait = Configurations.getWait();
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementToDisappear(WebElement element) {
        Wait<WebDriver> wait = Configurations.getWait();
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void type(WebElement element, String text) {
        waitForElement(element);
        element.clear();
        element.sendKeys(text);
    }

    public static void setProperties(ParentModel model, Map<String, String> properties) {
        System.out.println("properties: " + properties);
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
            try {
                Method method = CustomerModel.class.getMethod(methodName, value.getClass());
                method.invoke(model, value);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void waitForTextToAppear(String text) {
        Wait<WebDriver> wait = Configurations.getWait();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[contains(text(),'" + text + "')]"), text));
    }

    public static void waitForElementTextToBe(WebElement element, String text) {
        Wait<WebDriver> wait = Configurations.getWait();
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static void clickOnText(String text) {
        Wait<WebDriver> wait = Configurations.getWait();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[contains(text(),'" + text + "')]"), text));
    }

}
