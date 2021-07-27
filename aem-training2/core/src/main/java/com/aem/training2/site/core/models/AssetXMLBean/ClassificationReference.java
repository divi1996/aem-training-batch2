package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ClassificationReference
{
    @JacksonXmlProperty(localName = "ClassificationID",isAttribute = true)
    private String classificationID;

    public String getClassificationID() {
        return classificationID;
    }
}

