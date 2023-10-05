package models;

public class CustomerModel {

    private final String id;
    private final String first_name;
    private final String last_name;
    private final String email;
    private final String phone;
    private final String city;
    private final String zip_code;
    private final String street_name;
    private final String houseNumber;

    public CustomerModel(String id, String first_name, String last_name, String email, String city, String zip_code, String street_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.city = city;
        this.zip_code = zip_code;
        this.street_name = street_name;
        this.phone = "";
        this.houseNumber = "";
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
        return city;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getName() {
        return first_name + " " + last_name;
    }
}
