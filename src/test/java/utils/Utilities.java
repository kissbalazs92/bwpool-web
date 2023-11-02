package utils;

import components.Grid;
import models.CustomerModel;
import models.BaseModel;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;

public class Utilities {

    public static void click(WebElement element) {
        final int maxAttempts = 5;
        int attempts = 0;
        Wait<WebDriver> wait = Configurations.getWait();
        while (attempts < maxAttempts) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                break;
            } catch (Exception e) {
                if (++attempts >= maxAttempts) {
                    throw new RuntimeException("Failed to click element after " + attempts + " attempts", e);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                By locator = getLocatorFromElement(element);
                element = DriverManager.getInstance().getDriver().findElement(locator);
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
        System.out.println("MÉG CSAK MOST ELLENŐRIZZÜK HOGY RENDBEN VAN-E A LISTA");
        for (List<String> singleExpected : expectedList) {
            boolean found = false;
            for (List<String> actualElement : actualList) {
                if (actualElement.equals(singleExpected)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
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
        System.out.println("Desc átalakítás előtt: " + desc);
        int lastArrowIndex = desc.lastIndexOf("->");
        if (lastArrowIndex != -1) {
            desc = desc.substring(lastArrowIndex + 2, desc.length() - 1).trim();
            System.out.println("Desc: " + desc);
            desc = desc.replaceAll("]]", "]");
        }
        return desc.trim();
    }

    public static void waitForRowCountToDecrease(Grid grid, int initialRowCount) {
        if(initialRowCount == 1) {
            return;
        }
        try {
            FluentWait<WebDriver> wait = Configurations.getWait();
            wait.until(driver -> grid.getRowsCount() < initialRowCount);
        } catch (TimeoutException e) {
            throw new RuntimeException("Failed to wait for row count to decrease", e);
        }
    }

    public static boolean areAllElementsSameAtColumn(List<List<String>> listOfLists, int index) {
        if (listOfLists.isEmpty() || listOfLists.get(0).size() <= index) {
            return false;
        }
        String referenceElement = listOfLists.get(0).get(index);
        for (List<String> innerList : listOfLists) {
            System.out.println("Inner list: " + innerList);
            if (innerList.size() <= index || !innerList.get(index).equals(referenceElement)) {
                return false;
            }
        }
        return true;
    }

    public static void scrollAndClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getInstance().getDriver();
        int attempts = 0;
        while(attempts < 3) {
            try {
                js.executeScript("arguments[0].scrollIntoView();", element);
                click(element);
                break;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }


}
