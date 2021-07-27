package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AssetContentSpecification
{
    @JacksonXmlProperty(localName = "ImageConversionConfigurationID",isAttribute = true)
    private String imageConversionConfigurationID;
    @JacksonXmlProperty(localName = "IncludesBinaryContent",isAttribute = true)
    private String includesBinaryContent;

    public String getImageConversionConfigurationID() {
        return imageConversionConfigurationID;
    }

    public String getIncludesBinaryContent() {
        return includesBinaryContent;
    }
}
