package pages;

import components.Grid;
import org.openqa.selenium.WebDriver;
import utils.Configurations;

public class Calendar extends BasePage {

    private final WebDriver driver;
    private final String url = Configurations.getURL();

    public Calendar(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
