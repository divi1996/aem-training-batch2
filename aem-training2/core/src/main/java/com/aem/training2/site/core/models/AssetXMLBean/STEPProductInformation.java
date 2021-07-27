package com.aem.training2.site.core.models.AssetXMLBean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "STEP-ProductInformation")
public class STEPProductInformation {
    @JacksonXmlProperty(localName = "ExportContext",isAttribute = true)
    private String exportContext;
    @JacksonXmlProperty(localName = "Assets")
    private Assets assets;
    @JacksonXmlProperty(localName = "ExportTime",isAttribute = true)
    private String exportTime;
    @JacksonXmlProperty(localName = "ContextID",isAttribute = true)
    private String contextID;
    @JacksonXmlProperty(localName = "WorkspaceID",isAttribute = true)
    private String workspaceID;
    @JacksonXmlProperty(localName = "UseContextLocale",isAttribute = true)
    private String useContextLocale;

    public String getExportContext() {
        return exportContext;
    }

    public Assets getAssets() {
        return assets;
    }

    public String getExportTime() {
        return exportTime;
    }

    public String getContextID() {
        return contextID;
    }

    public String getWorkspaceID() {
        return workspaceID;
    }

    public String getUseContextLocale() {
        return useContextLocale;
    }
}
