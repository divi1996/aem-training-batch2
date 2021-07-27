package com.aem.training2.site.core.servlets;

import com.aem.training2.site.core.models.Profile;
import com.aem.training2.site.core.services.ProductService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component(
        service = Servlet.class,
        property = {Constants.SERVICE_DESCRIPTION + "=Simple servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_POST,
                "sling.servlet.paths=" + "/bin/profile"})
public class SimpleServlet extends SlingAllMethodsServlet {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    ProductService productService;


    @Override
    protected void doGet(final SlingHttpServletRequest req,
                          final SlingHttpServletResponse resp) throws ServletException, IOException {
        logger.info("servlet execution started");
        String userId = req.getParameter("userId");
        Cookie cookie = req.getCookie("token-id");
        if (Objects.nonNull(cookie)) {
            logger.debug("the parameters are :: {}",userId);
            String token = cookie.getValue();
            Profile profile = new Profile("Yogesh", "male", "developer", "India", "Pizza");
            Gson gson = new Gson();
            resp.getWriter().write(gson.toJson(productService.getProductList()));
        }else{
            logger.error("error in execution due to invalid cookie token");
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("You are not authorized to access this api, please login");
        }
    }

}
