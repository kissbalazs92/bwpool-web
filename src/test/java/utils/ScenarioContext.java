package utils;

import models.CustomerModel;
import models.ToolModel;

import java.util.List;

public class ScenarioContext {
    private Object currentPage;
    private List<CustomerModel> latestCostumers;
    private List<ToolModel> latestTools;

    public void setCurrentPage(Object page) {
        this.currentPage = page;
    }

    public Object getCurrentPage() {
        return currentPage;
    }

    public List<CustomerModel> getLatestCostumers() {
        return latestCostumers;
    }

    public List<ToolModel> getLatestTools() {
        return latestTools;
    }

    public void setLatestCostumers(List<CustomerModel> latestCostumers) {
        this.latestCostumers = latestCostumers;
    }

    public void setLatestTools(List<ToolModel> latestToolModels) {
        this.latestTools = latestToolModels;
    }
}

