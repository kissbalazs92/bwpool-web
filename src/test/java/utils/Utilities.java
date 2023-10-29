package utils;

import components.Grid;
import models.CustomerModel;
import models.BaseModel;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Utilities {

    public static void click(WebElement element) {
        final int maxAttempts = 5;
        int attempts = 0;

        // Példányosítunk egy FluentWait objektumot.
        Wait<WebDriver> wait = new FluentWait<>(DriverManager.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(10))  // összes várakozási idő
                .pollingEvery(Duration.ofSeconds(1))  // próbálkozások közötti intervallum
                .ignoring(NoSuchElementException.class);

        // Próbálkozások száma a kattintásra
        while (attempts < maxAttempts) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                break; // Ha a kattintás sikeres volt, kilépünk a ciklusból.
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                // Ha kivétel történt, újra próbálkozunk az elem megtalálásával és kattintással.
                if (++attempts >= maxAttempts) {
                    throw new RuntimeException("Failed to click element after " + attempts + " attempts", e);
                }
                By locator = getLocatorFromElement(element);
                element = DriverManager.getInstance().getDriver().findElement(locator);
                // Az újrapróbálkozás logikája a FluentWait beállításai alapján történik, így nincs szükség közvetlenül a Thread.sleep használatára.
            }
        }
    }


    public static void waitForElement(WebElement element) {
        Wait<WebDriver> wait = Configurations.getWait();
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElement(WebElement element, int timeoutInSec) {
        Wait<WebDriver> wait = new FluentWait<>(DriverManager.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(timeoutInSec))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class);
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

    public static void typeInDropdown(WebElement element, String text, WebElement dropdown) {
        waitForElement(element);
        element.sendKeys(text);
        try {
            waitForElement(dropdown, 3);
            removeElement(dropdown);
        }
        catch (Exception ignored) {

        }
    }

    public static void removeElement(WebElement element) {
        ((JavascriptExecutor) DriverManager.getInstance().getDriver()).executeScript(
                "var element = arguments[0];" +
                    "element.parentNode.removeChild(element);", element
        );
    }

    public static Boolean isExpectedListContainedInActualList(List<List<String>> actualList, List<List<String>> expectedList) {
        boolean allElementsFound = true;
        for (List<String> expectedElement : expectedList) {
            if (!actualList.contains(expectedElement)) {
                allElementsFound = false;
                break;
            }
        }
        return allElementsFound;
    }

    public static void setProperties(BaseModel model, Map<String, String> properties) {
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

    public static By getLocatorFromElement(WebElement element) {
        String description = getElementDescription(element);

        String[] parts = description.split(": ", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid locator description: " + description);
        }

        String byMethod = parts[0].trim();
        String value = parts[1].trim();

        switch (byMethod) {
            case "xpath":
                return By.xpath(value);
            case "id":
                return By.id(value);
            case "className":
                return By.className(value);
            case "tagName":
                return By.tagName(value);
            case "name":
                return By.name(value);
            case "linkText":
                return By.linkText(value);
            case "partialLinkText":
                return By.partialLinkText(value);
            case "cssSelector":
                return By.cssSelector(value);
            default:
                throw new IllegalArgumentException("Unknown locator method: " + byMethod);
        }
    }
    public static String getElementDescription(WebElement element) {
        String desc = element.toString();
        //System.out.println("Description: " + desc);
        int lastArrowIndex = desc.lastIndexOf("->");
        if (lastArrowIndex != -1) {
            desc = desc.substring(lastArrowIndex + 2).trim();
            //System.out.println("Description after substring: " + desc);
            if (desc.endsWith("]]")) {
                desc = desc.substring(0, desc.length() - 1);
                //System.out.println("Description after substring2: " + desc);
            } else if (desc.endsWith("]}")) {
                desc = desc.substring(0, desc.length() - 2);
                //System.out.println("Description after substring3: " + desc);
            }
        }
        return desc.trim();
    }

    public static void waitForRowCountToDecrease(Grid grid, int initialRowCount) {
        if(initialRowCount == 1) {
            return;
        }
        if(grid.getGridContent().size() < initialRowCount) {
            return;
        }
        try {
            FluentWait<WebDriver> wait = Configurations.getWait();
            wait.until(driver -> grid.getGridContent().size() < initialRowCount);
        } catch (TimeoutException e) {
            throw new RuntimeException("Failed to wait for row count to decrease", e);
        }
    }
}
