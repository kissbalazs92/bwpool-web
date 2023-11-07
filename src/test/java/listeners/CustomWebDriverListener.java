package listeners;

import org.apache.logging.log4j.message.MessageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import utils.DriverManager;
import utils.LoggerClass;
import utils.StepLogger;
import utils.Utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CustomWebDriverListener implements WebDriverListener {

    @Override
    public void afterClick(WebElement element) {
        LoggerClass.infoDetailed("Clicked on element: " + Utilities.getElementDescription(element));
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        LoggerClass.infoDetailed("Typed " + Arrays.toString(keysToSend) + " in element: " + Utilities.getElementDescription(element));
    }
}
