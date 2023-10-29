package enums;

public enum DataType {
    CUSTOMER("Customer"),
    TOOL("Tool"),
    LOCATION("Location");

    private final String value;

    DataType(String value) {
        this.value = value;
    }
}

