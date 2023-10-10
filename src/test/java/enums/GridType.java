package enums;

public enum GridType {

    CUSTOMER("Customer", "Ügyfelek"),
    LOCATION("Location", "Telephelyek"),
    TOOL("Tool", "Eszközök");

    private final String name;
    private final String title;

    GridType(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
