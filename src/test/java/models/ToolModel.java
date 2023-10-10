package models;

public class ToolModel extends ParentModel {

    private String manufacturer;
    private String model;
    private String serial_number;
    private String platform;
    private Boolean isInService;
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
}
