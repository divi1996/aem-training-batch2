package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MultiValue {
    @JacksonXmlProperty(localName = "AttributeID",isAttribute = true)
    private String attributeId;
    @JacksonXmlProperty(localName = "Value")
    private String[] value;

    public String getAttributeId() {
        return attributeId;
    }

    public String[] getValue() {
        return value;
    }
}
