package models;

public class ToolModel extends ParentModel {

    private final String manufacturer;
    private final String model;
    private final String serial_number;
    private final String platform;
    private final Boolean isInService;
    private final CustomerModel customer;

    public ToolModel(String manufacturer, String model, String serial_number, String platform, CustomerModel customer) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.serial_number = serial_number;
        this.platform = platform;
        this.isInService = false;
        this.customer = customer;
    }

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
