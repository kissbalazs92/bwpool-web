package models;

public class ToolModel {

    private final String manufacturer;
    private final String model;
    private final String serial_number;
    private final String platform;
    private final String name;
    private final Boolean isInService;
    private final CustomerModel costumer;
    private final LocationModel locationModel;

    public ToolModel(String manufacturer, String model, String serial_number, String platform, CustomerModel costumer, LocationModel locationModel) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.serial_number = serial_number;
        this.platform = platform;
        this.name = manufacturer + " " + model;
        this.isInService = false;
        this.costumer = costumer;
        this.locationModel = locationModel;
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
        return name;
    }

    public Boolean getInService() {
        return isInService;
    }

    public CustomerModel getCostumer() {
        return costumer;
    }

    public LocationModel getLocation() {
        return locationModel;
    }
}
