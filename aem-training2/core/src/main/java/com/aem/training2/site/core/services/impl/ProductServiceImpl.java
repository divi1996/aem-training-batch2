package com.aem.training2.site.core.services.impl;

import com.aem.training2.site.core.constants.CommonConstants;
import com.aem.training2.site.core.models.Product;
import com.aem.training2.site.core.services.ProductService;
import com.aem.training2.site.core.services.ProductServiceConfiguration;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;

import javax.jcr.Session;
import java.util.*;

@Component(service = ProductService.class, immediate = true)
@Designate(ocd = ProductServiceConfiguration.class)
public class ProductServiceImpl implements ProductService {

    @Reference
    ResourceResolverFactory factory;

    @Reference
    QueryBuilder builder;

    String path = StringUtils.EMPTY;

    @Activate
    @Modified
    protected void activateOrModified(ProductServiceConfiguration configuration){
        path = configuration.service_path();
    }

    @Override
    public List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        ResourceResolver resolver = null;
        try {
            resolver = getResourceResolver(CommonConstants.SERVICE_USER);
            Resource res = resolver.getResource(this.path);
//            List<String> paths = getMatchingPaths(this.path, resolver);
            for (Resource childRes:
                    res.getChildren()) {
                Product product = childRes.adaptTo(Product.class);
                productList.add(product);
            }
        } catch (LoginException e) {
            e.printStackTrace();
        }finally {
            if (resolver.isLive())
                resolver.close();
        }
        return productList;
    }

    private List<String> getMatchingPaths(String path, ResourceResolver resolver){

        List<String> paths = new ArrayList<>();
        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("path","/var/commerce/products/we-retail/me/coats");

        Session session = resolver.adaptTo(Session.class);

        Query query = builder.createQuery(PredicateGroup.create(queryParams), session);
        SearchResult result = query.getResult();

        Iterator<Resource> resultedResources = result.getResources();

        for (Iterator<Resource> it = resultedResources; it.hasNext(); ) {
            Resource res = it.next();

            paths.add(res.getPath());

        }

        return paths;
    }

    private ResourceResolver getResourceResolver(String serviceUser) throws LoginException {
        ResourceResolver resolver = null;
        if (StringUtils.isNotBlank(serviceUser)){
            final Map<String, Object> authInfo = new HashMap<>();
            authInfo.put(ResourceResolverFactory.SUBSERVICE, serviceUser);
            resolver = factory.getResourceResolver(authInfo);
        }
        return resolver;
    }

}
