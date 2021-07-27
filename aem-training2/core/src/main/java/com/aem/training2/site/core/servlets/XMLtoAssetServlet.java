/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.aem.training2.site.core.servlets;

import com.aem.training2.site.core.models.AssetXMLBean.STEPProductInformation;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.stream.Collectors;

@Component(
        service = Servlet.class,
        property = {Constants.SERVICE_DESCRIPTION + "=Asset creation servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_POST,
                "sling.servlet.paths=" + "/bin/createxml"})
public class XMLtoAssetServlet extends SlingAllMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(XMLtoAssetServlet.class);

    @Override
    protected void doPost(final SlingHttpServletRequest req,
                          final SlingHttpServletResponse resp) throws ServletException, IOException {
        String imageRequestString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            if (StringUtils.isNotBlank(imageRequestString)) {
                XmlMapper xmlMapper = new XmlMapper();
                STEPProductInformation requestObj = xmlMapper.readValue(imageRequestString, STEPProductInformation.class);
                String hello = "abc";
            }
        }catch (Exception e){
            LOG.error("inside doPost method, exception is :: ",e);
        }
    }
}
