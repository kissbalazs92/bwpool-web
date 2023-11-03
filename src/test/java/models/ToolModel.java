package models;

import enums.ModelsGridProperties;

import java.util.ArrayList;
import java.util.List;

public class ToolModel extends BaseModel {
    private String columnHeaderId;
    private String gridNumberWhenRegistered;
    private String manufacturer;
    private String model;
    private String serial_number;
    private String platform;
    private Boolean isInService = false;
    private CustomerModel customer;

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public String getPlatform() {
        return platform;
    }

    public String getName() {
        return manufacturer + " " + model;
    }

    public Boolean getInService() {
        return isInService;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public String getFullAddress() {
        return customer.getLocation().getFullAddress();
    }

    public boolean isInService() {
        return isInService;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public void setInService(Boolean isInService) {
        this.isInService = isInService;
    }

    @Override
    public String getColumnHeaderId() {
        return columnHeaderId;
    }

    @Override
    public String getPartnerName() {
        return customer.getName();
    }

    @Override
    public void setColumnHeaderId(String columnHeaderId) {
        this.columnHeaderId = columnHeaderId;
    }

    public void setGridNumberWhenRegistered(String gridNumberWhenRegistered) {
        this.gridNumberWhenRegistered = gridNumberWhenRegistered;
    }

    public String getGridNumberWhenRegistered() {
        return gridNumberWhenRegistered;
    }

    @Override
    public List<String> extractGridValues() {
        List<String> values = new ArrayList<>();
        values.add(getName());
        values.add(getCustomerName());
        values.add(getCustomer().getLocation().getFullAddress());
        values.add(getPlatform());
        values.add(getSerial_number());
        values.add(getInService() ? "true" : "false");
        return values;
    }

    @Override
    public String getTextToSearch(ModelsGridProperties modelsGridProperty, BaseModel modelObj) {
        return modelsGridProperty.extractValue(modelObj);
    }
}
