package components;

import models.CustomerModel;
import models.ToolModel;
import org.openqa.selenium.WebDriver;

public class GridDialog {
    private final WebDriver driver;

    public GridDialog(WebDriver driver) {
        this.driver = driver;
    }

    public void registerCustomer(CustomerModel customer) {
        // Kattints az "Add" gombra
        // Töltsd ki az űrlapot a `customer` adataival
        // ...
    }

    public void registerTool(ToolModel tool) {
        // Kattints az "Add" gombra
        // Töltsd ki az űrlapot a `tool` adataival
        // ...
    }
    // További GridDialog metódusok...
}
