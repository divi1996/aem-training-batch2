package com.aem.training2.site.core.models;

import com.aem.training2.site.core.services.ProductService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HelloWorldModel {

    @ValueMapValue
    private String name;

    @ValueMapValue
    private String path;

    @SlingObject
    SlingHttpServletRequest request;

    @OSGiService
    ProductService service;

    private List<Product> productList = new ArrayList<>();

    @PostConstruct
    protected void init(){
       productList= service.getProductList();
        name = name.toUpperCase();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public String getName() {
        return name;
    }
}
