package listeners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import utils.DriverManager;
import utils.LoggerClass;
import utils.Utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CustomWebDriverListener implements WebDriverListener {

    private int count = 0;

    @Override
    public void afterClick(WebElement element) {
        LoggerClass.infoDetailed("Clicked on element: " + Utilities.getElementDescription(element));
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        LoggerClass.infoDetailed("Typed " + Arrays.toString(keysToSend) + " in element: " + Utilities.getElementDescription(element));
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        System.out.println("Target: " + target);
        System.out.println("Method: " +method);
        if(count == 0) {
            System.out.println(DriverManager.getInstance().getDriver().getPageSource());
            count++;
        }

    }
}
