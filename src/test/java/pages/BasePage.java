package pages;

import components.Navbar;
import utils.DriverManager;

public abstract class BasePage {

    Navbar navbar = new Navbar(DriverManager.getInstance().getDriver());

    public abstract String getUrl();
    public abstract Navbar getNavbar();

}
