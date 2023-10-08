package pages;

import components.Grid;
import org.openqa.selenium.WebDriver;

public class Tool implements PageWithGrid {

    private final WebDriver driver;
    private final Grid grid;

    public Tool(WebDriver driver) {
        this.driver = driver;
        this.grid = new Grid(driver);
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

}
