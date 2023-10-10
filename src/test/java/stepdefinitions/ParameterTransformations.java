package stepdefinitions;

import enums.DataType;
import enums.GridType;
import enums.PageType;
import io.cucumber.java.ParameterType;

public class ParameterTransformations {

    @ParameterType(".*")
    public PageType pageType(String pageTypeName) {
        return PageType.fromName(pageTypeName);
    }

    @ParameterType(".*")
    public static GridType gridType(String gridTypeName) {
        for (GridType gridType : GridType.values()) {
            if (gridType.getTitle().equalsIgnoreCase(gridTypeName.replaceAll("\"", ""))) {
                return gridType;
            }
        }
        throw new IllegalArgumentException("Unknown grid type: " + gridTypeName);
    }

    @ParameterType(".*")
    public static DataType dataType(String dataTypeName) {
        try {
            return DataType.valueOf(dataTypeName.replaceAll("\"", "").toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown data type: " + dataTypeName);
        }
    }
}
