package models;

public record LocationModel(CustomerModel costumer) {

    public String getCustomerName() {
        return costumer.getName();
    }

    public String getCity() {
        return costumer.getCity();
    }

    public String getZip_code() {
        return costumer.getZip_code();
    }

    public String getStreet_name() {
        return costumer.getStreet_name();
    }

    public String getHouseNumber() {
        return costumer.getHouseNumber();
    }

    public String getFullAddress() {
        return costumer.getZip_code()
                + " " + costumer.getCity()
                + ", " + costumer.getStreet_name()
                + " " + costumer.getHouseNumber();
    }

}
