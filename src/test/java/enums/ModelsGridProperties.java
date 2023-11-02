package enums;

import components.Grid;
import pages.Customer;
import pages.Location;
import pages.PageWithGrid;
import pages.Tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum ModelsGridProperties {

    CUSTOMER_NAME("customerName", List.of("Neve", "Ügyfél", "Partner")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof models.CustomerModel) {
                System.out.println("CUSTOMER branch: " + obj);
                return ((models.CustomerModel) obj).getName();
            } else if (obj instanceof models.LocationModel) {
                System.out.println("LOCATION branch: " + obj);
                return ((models.LocationModel) obj).getCustomerName();
            } else if (obj instanceof models.ToolModel) {
                System.out.println("TOOL branch: " + obj);
                return ((models.ToolModel) obj).getCustomerName();
            }
            System.out.println("UNKNOWN branch: " + obj);
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            if (grid.getPageToAppearOn() instanceof Customer) {
                int columnIndex = grid.getColumnIndex(Collections.singletonList(this.getColumnName().get(0)));
                System.out.println("columnIndex: " + columnIndex);
                return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
            } else if (grid.getPageToAppearOn() instanceof Location) {
                int columnIndex = grid.getColumnIndex(Collections.singletonList(this.getColumnName().get(1)));
                System.out.println("columnIndex: " + columnIndex);
                return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
            } else if (grid.getPageToAppearOn() instanceof Tool) {
                int columnIndex = grid.getColumnIndex(Collections.singletonList(this.getColumnName().get(2)));
                System.out.println("columnIndex: " + columnIndex);
                return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
            }
            return null;
        }
    },
    CUSTOMER_EMAIL("customerEmail", List.of("Email")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof CustomerModel) {
                return ((CustomerModel) obj).getEmail();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    CUSTOMER_PHONE("customerPhone", List.of("Telefon")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof CustomerModel) {
                return ((CustomerModel) obj).getPhone();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    CUSTOMER_COMMENT("customerComment", List.of("Megjegyzés")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof CustomerModel) {
                return ((CustomerModel) obj).getComment();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    LOCATION_CITY("locationCity", List.of("Város")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof LocationModel) {
                return ((LocationModel) obj).getCity();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    LOCATION_ZIP_CODE("locationZipCode", List.of("Irányító")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof LocationModel) {
                return ((LocationModel) obj).getZip_code();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    LOCATION_STREET_NAME("locationStreetName", List.of("Utca")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof LocationModel) {
                return ((LocationModel) obj).getStreet_name();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    LOCATION_HOUSE_NUMBER("locationHouseNumber", List.of("Házszám")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof LocationModel) {
                return ((LocationModel) obj).getHouseNumber();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    LOCATION_FULL_ADDRESS("locationFullAddress", List.of("location")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof LocationModel) {
                return ((LocationModel) obj).getFullAddress();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    TOOL_NAME("toolName", List.of("Neve")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof ToolModel) {
                return ((ToolModel) obj).getName();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    TOOL_DESCRIPTION("toolDescription", List.of("Leírás")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof ToolModel) {
                return ((ToolModel) obj).getDescription();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    TOOL_COMMENT("toolComment", List.of("Megjegyzés")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof ToolModel) {
                return ((ToolModel) obj).getComment();
            }
            return null;
        }

        @Override
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getText();
        }
    },
    TOOL_SERVICE("toolService", List.of("Szervíz")) {
        @Override
        public String extractValue(Object obj) {
            if(obj instanceof ToolModel) {
                return ((ToolModel) obj).getInService() ? "true" : "false";
            }
            return null;
        }
        public String extractValueFromGrid(Grid grid, String columnHeaderId) {
            int columnIndex = grid.getColumnIndex(this.getColumnName());
            System.out.println("columnIndex: " + columnIndex);
            return grid.getCellBasedOnColumnHeaderIdAndColumnIndex(columnHeaderId, columnIndex).getAttribute("aria-label").split(" ")[0];
        }
    };

    private final String name;
    private final List<String> columnName;

    ModelsGridProperties(String name, List<String> columnName) {
        this.name = name;
        this.columnName = columnName;
    }

    public abstract String extractValue(Object obj);
    public abstract String extractValueFromGrid(Grid grid, String columnHeaderId);

    public String getName() {
        return name;
    }

    public List<String> getColumnName() {
        return columnName;
    }
}

interface CustomerModel {
    String getName();
    String getEmail();
    String getPhone();
    String getComment();
}

interface LocationModel {
    String getCustomerName();
    String getCity();
    String getZip_code();
    String getStreet_name();
    String getHouseNumber();
    String getFullAddress();
}

interface ToolModel {
    String getCustomerName();
    String getName();
    String getDescription();
    String getComment();
    boolean getInService();
}
