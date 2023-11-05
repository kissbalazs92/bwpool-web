package enums;

import utils.Configurations;

public enum ResolutionType {

    DESKTOP("desktop", Configurations.getDesktopResolution()),
    TABLET("tablet", Configurations.getTabletResolution()),
    LAPTOP("laptop", Configurations.getLaptopResolution()),
    MOBILE("mobile", Configurations.getMobileResolution());

    private final String resolution;
    private final String resolutionValue;

    ResolutionType(String resolution, String resolutionValue) {
        this.resolution = resolution;
        this.resolutionValue = resolutionValue;
    }

    public String getResolution() {
        return resolution;
    }

    public String getResolutionValue() {
        return resolutionValue;
    }

}
