package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AssetModel {
    @JacksonXmlProperty(localName = "STEP-ProductInformation")
    private STEPProductInformation stepProductInformation;

    public STEPProductInformation getStepProductInformation() {
        return stepProductInformation;
    }
}
