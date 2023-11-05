package pages;

import components.Navbar;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Configurations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationInfo extends BasePage {

    private final WebDriver driver;
    private final String url;
    @FindBy(xpath = "//h6[text()='Eszközök']/..//td")
    private List<WebElement> allToolInfoCells;
    private String columnHeaderId;

    public LocationInfo(WebDriver driver, String columnHeaderId) {
        this.driver = driver;
        this.columnHeaderId = columnHeaderId;
        String urlPart = Configurations.getURL() + "LocationInfo/";
        this.url = urlPart + columnHeaderId;
        PageFactory.initElements(driver, this);
    }

    public HashMap<String, String> getActualToolsInfo() {
        HashMap<String, String> actualToolsInfo = new HashMap<>();
        for (int i = 0; i < allToolInfoCells.size(); i += 2) {
            actualToolsInfo.put(allToolInfoCells.get(i).getText(), allToolInfoCells.get(i + 1).getText());
        }
        return actualToolsInfo;
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
