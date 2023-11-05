package pages;

import components.Grid;
import components.Navbar;
import org.openqa.selenium.WebDriver;
import utils.Configurations;

public class Tool extends BasePage implements PageWithGrid {

    private final WebDriver driver;
    private final String url = Configurations.getURL() + "Tool/";
    private final Grid grid;

    public Tool(WebDriver driver) {
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

    @Override
    public Navbar getNavbar() {
        return navbar;
    }
}
