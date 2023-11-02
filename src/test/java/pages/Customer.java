package pages;

import components.Grid;
import org.openqa.selenium.WebDriver;
import utils.Configurations;

public class Customer extends BasePage implements PageWithGrid {

    private final WebDriver driver;

    private final String url = Configurations.getURL() + "Customer/";
    private final Grid grid;

    public Customer(WebDriver driver) {
        this.driver = driver;
        this.grid = new Grid(driver, this);
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
