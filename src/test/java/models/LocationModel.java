package models;

public class LocationModel {

    private final CustomerModel costumer;

    public LocationModel(CustomerModel costumer) {
        this.costumer = costumer;
    }

    public CustomerModel getCostumer() {
        return costumer;
    }

    public String getFullAddress() {
        return costumer.getZip_code()
                + " " + costumer.getCity()
                + ", " + costumer.getStreet_name()
                + " " + costumer.getHouseNumber();
    }

}
