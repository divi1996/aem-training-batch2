package com.aem.training2.site.core.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AssetPropertyXML {

    @JacksonXmlProperty(isAttribute = true, localName = "ID")
    private String assetId;

    @JacksonXmlProperty(isAttribute = true, localName = "UserTypeID")
    private String userTypeID;

    @JacksonXmlProperty(localName = "AssetBinaryContent")
    private String imageString;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
