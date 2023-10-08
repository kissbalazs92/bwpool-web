package models;

public class WorkModel extends ParentModel {

    private final CustomerModel costumer;

    public WorkModel(CustomerModel costumer) {
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
