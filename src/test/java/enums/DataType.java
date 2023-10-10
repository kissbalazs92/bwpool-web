package enums;

public enum DataType {
    CUSTOMER("Customer"),
    TOOL("Tool");

    private final String value;

    DataType(String value) {
        this.value = value;
    }
}

