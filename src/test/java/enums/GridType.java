package enums;

import models.BaseModel;
import utils.ScenarioContext;
import java.util.List;
import java.util.function.Function;

public enum GridType {

    CUSTOMER("Customer", "Ügyfelek", context -> context.getLatestCustomers().get(0)),
    LOCATION("Location", "Telephelyek", context -> context.getLatestLocations().get(0)),
    TOOL("Tool", "Eszközök", context -> context.getLatestTools().get(0));

    private final String name;
    private final String title;
    private final Function<ScenarioContext, BaseModel> modelProvider;

    GridType(String name, String title, Function<ScenarioContext, BaseModel> modelProvider) {
        this.name = name;
        this.title = title;
        this.modelProvider = modelProvider;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public BaseModel getModel(ScenarioContext context) {
        return modelProvider.apply(context);
    }
}
