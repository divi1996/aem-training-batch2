package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AssetContent
{
    @JacksonXmlProperty(localName = "AssetContentSpecification")
    private AssetContentSpecification assetContentSpecification;

    public AssetContentSpecification getAssetContentSpecification() {
        return assetContentSpecification;
    }
}
