package enums;

import models.BaseModel;
import utils.ScenarioContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public enum DataType {
    CUSTOMER("Customer", "Ügyfél", context -> new ArrayList<>(context.getLatestCustomers())),
    TOOL("Tool", "Eszköz", context -> new ArrayList<>(context.getLatestTools())),
    LOCATION("Location", "Telephely", context -> new ArrayList<>(context.getLatestLocations()));

    private final String value;
    private final String name;
    private final Function<ScenarioContext, List<BaseModel>> modelListProvider;

    DataType(String value, String name, Function<ScenarioContext, List<BaseModel>> modelListProvider) {
        this.value = value;
        this.name = name;
        this.modelListProvider = modelListProvider;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public List<BaseModel> getModelList(ScenarioContext context) {
        return modelListProvider.apply(context);
    }

    public BaseModel getFirstModel(ScenarioContext context) {
        List<BaseModel> models = getModelList(context);
        if (models == null || models.isEmpty()) {
            throw new IllegalStateException("No models available for " + this.name);
        }
        return models.get(0);
    }

}
