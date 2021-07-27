package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AssetBinaryContent
{
    @JacksonXmlProperty(localName = "ImageConversionConfigurationID",isAttribute = true)
    private String imageConversionConfigurationID;
    @JacksonXmlProperty(localName = "MIMEType",isAttribute = true)
    private String mimeType;
    @JacksonXmlProperty(localName = "Checksum",isAttribute = true)
    private String checksum;
    @JacksonXmlProperty(localName = "BinaryContent")
    private String binaryContent;

    public String getImageConversionConfigurationID() {
        return imageConversionConfigurationID;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getBinaryContent() {
        return binaryContent;
    }
}