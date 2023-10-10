package enums;

import java.util.Arrays;

public enum PageType {
    CUSTOMER("Customer"),
    LOCATION("Location"),
    TOOL("Tool"),
    CALENDAR("Calendar");

    private final String name;

    PageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PageType fromName(String name) {
        PageType[] asd = values();
        //System.out.println((Arrays.stream(asd).toArray()[0].getClass().getName()));
        for (PageType pageType : values()) {
            if (pageType.getName().equalsIgnoreCase(name.replaceAll("\"", ""))) {
                return pageType;
            }
        }
        throw new IllegalArgumentException("Unknown page type: " + name);
    }
}

