package com.aem.training2.site.core.models.AssetXMLBean;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Assets
{
    @JacksonXmlProperty(localName = "Asset")
    private AssetBean assetDetails;

    public AssetBean getAsset() {
        return assetDetails;
    }
}

