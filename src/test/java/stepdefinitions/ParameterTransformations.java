package stepdefinitions;

import enums.DataType;
import enums.GridType;
import enums.ModelsGridProperties;
import enums.PageType;
import io.cucumber.java.ParameterType;

public class ParameterTransformations {

    @ParameterType(".* page")
    public PageType pageType(String pageTypeName) {
        pageTypeName = pageTypeName.replaceAll("\"|\\bpage\\b", "").trim();
        return PageType.fromName(pageTypeName);
    }

    @ParameterType(".* grid")
    public static GridType gridType(String gridTypeName) {
        for (GridType gridType : GridType.values()) {
            if (gridType.getTitle().equalsIgnoreCase(gridTypeName.replaceAll("\"|\\bgrid\\b", "").trim())) {
                return gridType;
            }
        }
        throw new IllegalArgumentException("Unknown grid type: " + gridTypeName);
    }

    @ParameterType("\\w+")
    public static DataType dataType(String dataTypeName) {
        try {
            return DataType.valueOf(dataTypeName.replaceAll("\"", "").toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown data type: " + dataTypeName);
        }
    }

    @ParameterType("[a-z][a-zA-Z]+")
    public static ModelsGridProperties modelsGridProperty(String modelPropertyName) {
        for (ModelsGridProperties modelsGridProperty : ModelsGridProperties.values()) {
            if (modelsGridProperty.getName().equalsIgnoreCase(modelPropertyName.replaceAll("\"", ""))) {
                return modelsGridProperty;
            }
        }
        throw new IllegalArgumentException("Unknown model property: " + modelPropertyName);
    }
}
