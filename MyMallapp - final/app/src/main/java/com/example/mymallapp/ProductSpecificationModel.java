package com.example.mymallapp;

public class ProductSpecificationModel {
    public static final int SPECIFICATION_TITLE=0;
    public static final int SPECIFICATION_BODY=1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /////specification title
    private String title;

    public ProductSpecificationModel(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /////specification title


    ///// specification body

    private String FeatureName;
    private String FeatureValue;

    public ProductSpecificationModel(int type, String featureName, String featureValue) {
        this.type = type;
        FeatureName = featureName;
        FeatureValue = featureValue;
    }

    public String getFeatureName() {
        return FeatureName;
    }

    public void setFeatureName(String featureName) {
        FeatureName = featureName;
    }

    public String getFeatureValue() {
        return FeatureValue;
    }

    public void setFeatureValue(String featureValue) {
        FeatureValue = featureValue;
    }
    ///// specification body

}
