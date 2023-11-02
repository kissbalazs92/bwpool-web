package pages;

import components.Grid;
import org.openqa.selenium.WebDriver;
import utils.Configurations;

public class Location extends BasePage implements PageWithGrid {

    private final WebDriver driver;

    private final String url = Configurations.getURL() + "Location/";
    private final Grid grid;

    public Location(WebDriver driver) {
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
