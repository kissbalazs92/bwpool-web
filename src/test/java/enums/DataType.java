package enums;

public enum DataType {
    CUSTOMER("Customer", "Ügyfél"),
    TOOL("Tool", "Eszköz"),
    LOCATION("Location", "Telephely");

    private final String value;

    private final String name;

    DataType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

