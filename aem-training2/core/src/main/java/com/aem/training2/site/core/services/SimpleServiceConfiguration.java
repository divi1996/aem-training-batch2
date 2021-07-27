package com.aem.training2.site.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Simple Service Configuration 2",
        description = "This is a simple service configuration that I am trying to create")
public @interface SimpleServiceConfiguration {

    @AttributeDefinition(name = "path", description = "path for my products", type = AttributeType.STRING)
    public String service_path() default "/content";

    @AttributeDefinition(name="boolean", description = "any boolean", type = AttributeType.BOOLEAN)
    public boolean any_boolean() default false;
}
