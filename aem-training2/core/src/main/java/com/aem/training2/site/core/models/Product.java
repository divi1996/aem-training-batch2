package com.aem.training2.site.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Named;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Product {
    @ValueMapValue
    @Named("jcr:title")
    private String title;
    @ValueMapValue
    private String price;
    @ValueMapValue
    private String summary;

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getSummary() {
        return summary;
    }
}
