package com.aem.training2.site.core.services;

import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;

public interface XMLtoAssetService {

    JsonObject createAsset(SlingHttpServletRequest request);
}
