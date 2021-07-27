package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Values
{
    @JacksonXmlProperty(localName = "Value")
    @JacksonXmlElementWrapper(useWrapping = false)
    private Value[] value;

    public Value[] getValue() {
        return value;
    }
}
