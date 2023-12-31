package models;

import enums.ModelsGridProperties;

import java.util.List;

public abstract class BaseModel {
    public abstract List<String> extractGridValues();
    public abstract String getTextToSearch(ModelsGridProperties modelsGridProperty, BaseModel modelObj);

    public abstract void setColumnHeaderId(String columnHeaderId);
    public abstract String getColumnHeaderId();
    public abstract String getPartnerName();

}
