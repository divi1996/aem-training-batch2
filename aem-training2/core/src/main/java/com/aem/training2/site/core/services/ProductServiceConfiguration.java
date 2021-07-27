package com.aem.training2.site.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Product Service Configuration", description = "This is the configuration utilized to get products from backend")
public @interface ProductServiceConfiguration {

    @AttributeDefinition(name="path",description = "path for my products", type = AttributeType.STRING)
    public String service_path() default "/content";

    @AttributeDefinition(name="isProd",description = "check if prod environment", type = AttributeType.BOOLEAN)
    public boolean isProd() default false;
}