package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AssetBean {
    @JacksonXmlProperty(localName = "ClassificationReference")
    @JacksonXmlElementWrapper(useWrapping = false)
    private ClassificationReference classificationReference;
    @JacksonXmlProperty(localName = "Name")
    private Name name;
    @JacksonXmlProperty(localName = "Republished", isAttribute = true)
    private String republished;
    @JacksonXmlProperty(localName = "UserTypeID", isAttribute = true)
    private String userTypeID;
    @JacksonXmlProperty(localName = "AssetContent")
    private AssetContent assetContent;
    @JacksonXmlProperty(localName = "Values")
    private Values values;
    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    private String id;
    @JacksonXmlProperty(localName = "AssetBinaryContent")
    private AssetBinaryContent assetBinaryContent;

    public ClassificationReference getClassificationReference() {
        return classificationReference;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public AssetContent getAssetContent() {
        return assetContent;
    }

    public Values getValues() {
        return values;
    }

    public String getId() {
        return id;
    }

    public AssetBinaryContent getAssetBinaryContent() {
        return assetBinaryContent;
    }

    public String getRepublished() {
        return republished;
    }

    public Name getName() {
        return name;
    }
}
