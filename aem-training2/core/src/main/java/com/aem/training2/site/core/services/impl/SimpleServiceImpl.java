package com.aem.training2.site.core.services.impl;

import com.aem.training2.site.core.services.SimpleService;
import com.aem.training2.site.core.services.SimpleServiceConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component(service = SimpleService.class, immediate = true)
@Designate(ocd = SimpleServiceConfiguration.class)
public class SimpleServiceImpl implements SimpleService{

    public static final String SIMPLE_SYSTEM_USER = "simpleSystemUser";
    private final Logger LOG = LoggerFactory.getLogger(SimpleServiceImpl.class);
    String path = StringUtils.EMPTY;
    Boolean anyBool = false;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Activate
    @Modified
    protected void activateOrModified(SimpleServiceConfiguration config){
        path = config.service_path();
        anyBool = config.any_boolean();
    }

    @Override
    public String getMessage(String name){
        return "path ::".concat(path);
    }

    @Override
    public List<String> getProductList(){
        ResourceResolver resolver = getResourceResolver(resourceResolverFactory, SIMPLE_SYSTEM_USER);
        List<String> productsList = new ArrayList<>();
        if (Objects.nonNull(resolver)){
            Resource parentProductResource = resolver.getResource(path);
            for (Resource res:
                    parentProductResource.getChildren()) {
                String title = res.getValueMap().get("jcr:title",String.class);
                productsList.add(title);
            }
        }
        return productsList;
    }

    private ResourceResolver getResourceResolver(ResourceResolverFactory resourceResolverFactory, String systemUser){
        ResourceResolver resourceResolver = null;
        if (Objects.nonNull(resourceResolverFactory) && StringUtils.isNotBlank(systemUser)){
            try {
                final Map<String, Object> authInfo = new HashMap<>();
                authInfo.put(ResourceResolverFactory.SUBSERVICE, systemUser);
                resourceResolver = resourceResolverFactory.getResourceResolver(authInfo);
            } catch (final LoginException loginException) {
                LOG.error("exception occured");
            }
        }
        return resourceResolver;
    }
}
