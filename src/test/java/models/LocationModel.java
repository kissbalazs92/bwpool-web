package models;

import enums.ModelsGridProperties;

import java.util.ArrayList;
import java.util.List;

public class LocationModel extends BaseModel {

    private final CustomerModel customer;

    public LocationModel(CustomerModel customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public String getCity() {
        return customer.getCity();
    }

    public String getZip_code() {
        return customer.getZip_code();
    }

    public String getStreet_name() {
        return customer.getStreet_name();
    }

    public String getHouseNumber() {
        return customer.getHouseNumber();
    }

    public String getFullAddress() {
        return (customer.getZip_code()
                + " " + customer.getCity()
                + ", " + customer.getStreet_name()
                + " " + customer.getHouseNumber()).trim();
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    @Override
    public List<String> extractGridValues() {
        List<String> values = new ArrayList<>();
        values.add(getCustomerName());
        values.add(getCity());
        values.add(getZip_code());
        values.add(getStreet_name());
        values.add(getHouseNumber());
        return values;
    }

    @Override
    public String getTextToSearch(ModelsGridProperties modelsGridProperty, BaseModel modelObj) {
        return modelsGridProperty.extractValue(modelObj);
    }

}
