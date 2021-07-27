package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Name {
    @JacksonXmlProperty(localName = "Changed", isAttribute = true)
    private String changed;
    @JacksonXmlText
    private String content;

    public String getChanged() {
        return changed;
    }

    public String getContent() {
        return content;
    }
}
