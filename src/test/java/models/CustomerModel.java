package models;

public class CustomerModel extends ParentModel {

    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private final String houseNumber = "";
    private LocationModel location;
    private Address address;

    public static class Address {
        private String city;
        private String street_name;
        private String street_address;
        private String zip_code;
        private String state;
        private String country;
    }


    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return address.city;
    }

    public String getZip_code() {
        address.zip_code = address.zip_code.replace("-", "");
        return address.zip_code;
    }

    public String getStreet_name() {
        return address.street_name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getName() {
        return first_name + " " + last_name;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation() {
        this.location = new LocationModel(this);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
