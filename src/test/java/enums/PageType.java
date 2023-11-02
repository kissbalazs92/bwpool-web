package enums;

import java.util.Arrays;

public enum PageType {
    CUSTOMER("Customer"),
    LOCATION("Location"),
    TOOL("Tool"),
    CALENDAR("Calendar"),
    LOCATION_INFO("LocationInfo");

    private final String name;

    PageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PageType fromName(String name) {
        for (PageType pageType : values()) {
            if (pageType.getName().equalsIgnoreCase(name.replaceAll("\"", ""))) {
                return pageType;
            }
        }
        throw new IllegalArgumentException("Unknown page type: " + name);
    }
}

