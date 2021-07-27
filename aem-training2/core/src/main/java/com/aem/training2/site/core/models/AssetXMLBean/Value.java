package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Value {
    @JacksonXmlProperty(localName = "UnitID")
    private String unitID;
    @JacksonXmlProperty(localName = "AttributeID", isAttribute = true)
    private String attributeID;
    @JacksonXmlProperty(localName = "Changed",isAttribute = true)
    private String changed;
    @JacksonXmlProperty(localName = "ID",isAttribute = true)
    private String id;
    @JacksonXmlText
    private String content;

    public String getUnitID() {
        return unitID;
    }
    public String getAttributeID() {
        return attributeID;
    }
    public String getContent() {
        return content;
    }
    public String getChanged() {
        return changed;
    }

    public String getId() {
        return id;
    }
}
