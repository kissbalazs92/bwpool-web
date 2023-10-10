package utils;

import models.CustomerModel;
import models.LocationModel;
import models.ToolModel;

import java.util.List;

public class ScenarioContext {
    private Object currentPage;
    private List<CustomerModel> latestCustomers;
    private List<ToolModel> latestTools;
    private List<LocationModel> latestLocations;

    public void setCurrentPage(Object page) {
        this.currentPage = page;
    }

    public Object getCurrentPage() {
        return currentPage;
    }

    public List<CustomerModel> getLatestCustomers() {
        return latestCustomers;
    }

    public List<ToolModel> getLatestTools() {
        return latestTools;
    }

    public void setLatestCustomers(List<CustomerModel> latestCustomers) {
        this.latestCustomers = latestCustomers;
    }

    public void setLatestTools(List<ToolModel> latestToolModels) {
        this.latestTools = latestToolModels;
    }

    public List<LocationModel> getLatestLocations() {
        return latestLocations;
    }

    public void setLatestLocations(List<LocationModel> latestLocations) {
        this.latestLocations = latestLocations;
    }
}

