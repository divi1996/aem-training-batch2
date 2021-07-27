//package com.aem.training2.site.core.services.impl;
//
//
//import com.aem.training2.site.core.models.AssetXMLBean.AssetUpload;
//import com.aem.training2.site.core.models.AssetXMLBean.STEPProductInformation;
//import com.aem.training2.site.core.models.AssetXMLBean.Value;
//import com.aem.training2.site.core.services.XMLtoAssetService;
//import com.aem.training2.site.core.servlets.XMLtoAssetServlet;
//import com.day.cq.commons.jcr.JcrConstants;
//import com.day.cq.dam.api.Asset;
//import com.day.cq.dam.api.AssetManager;
//import com.day.cq.replication.ReplicationActionType;
//import com.day.cq.replication.ReplicationException;
//import com.day.cq.replication.Replicator;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import com.google.gson.JsonObject;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.sling.api.request.RequestParameter;
//import org.apache.sling.api.resource.ModifiableValueMap;
//import org.apache.sling.api.resource.PersistenceException;
//import org.apache.sling.api.resource.Resource;
//import org.apache.sling.api.resource.ResourceResolver;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.annotations.Reference;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.jcr.Session;
//import javax.xml.bind.DatatypeConverter;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//import java.util.Objects;
//
//@Component(service = XMLtoAssetService.class, immediate = true)
//public class XMLtoAssetServiceImpl implements XMLtoAssetService {
//
//    private static final String IMAGE_XML = "imageXml";
//    private static final String FORMAT = "format";
//    private static final String UPC = "upc";
//    private static final String ANGLE = "angle";
//    private static final String TITLE = "title";
//    private static final String PRODUCT_NAME = "product_name";
//    private static final String ASSET_ANGLE = "asset.angle";
//    private static final String ASSET_TITLE = "asset.title";
//    private static final String ASSET_PRODUCT_NAME = "asset.product_name";
//    private static final String ASSET_FORMAT = "asset.format";
//    private static final String ASSET_UPC = "asset.upc";
//    private static final String ASSET_EXTENSION = "asset.extension";
//    private static final String ASSET_MIME_TYPE = "asset.mime-type";
//    private final Logger LOG = LoggerFactory.getLogger(XMLtoAssetServlet.class);
//    private static final long serialVersionUID = 1L;
//    private static final String ASSET_PATH = "/content/dam/training/";
//    private static final String METADATA_PATH = "/" + JcrConstants.JCR_CONTENT + "/metadata";
//    ResponseCodes codes = null;
//    @Reference
//    Replicator replicator;
//
//    private enum ResponseCodes {
//        CreateFailed(-7, "No write permissions"),
//        AuthorModeOnly(-6, "Request allowed only for author mode, Please check the URL"),
//        PublishFailed(-5, "Asset created.Failed to publish asset"),
//        MissingRequiredParams(-1, "Invalid request missing mandatory request form data"),
//        InternalServerError(-1, "Internal Server Error"),
//        ErrorAddingMetadata(-1, "Error Adding Metadata Properties"),
//        AssetAlreadyExists(-1, "Asset already exists, updated metadata"),
//        AssetCreated(0, "Asset Added"),
//        ErrorMappingXml(-1, "Error mapping xml to java objects"),
//        InvalidXmlFile(-1, "Invalid xml file in request");
//
//        private int id;
//        private String msg;
//
//        ResponseCodes(int x, String status) {
//            this.id = x;
//            this.msg = status;
//        }
//    }
//
//    /**
//     * Method to create Asset using the request Object
//     * that contains Asset mapping in an XML file
//     *
//     * @param request {@code SlingHttpServletRequest}
//     * @return Response {@code JsonObject}
//     */
//    @Override
//    public JsonObject createAsset(SlingHttpServletRequest request) {
//        JsonObject json = new JsonObject();
//        if (assetUtil.isAuthoring()) {
//            String xmlString = readFormData(request);
//            ResourceResolver resolver = request.getResourceResolver();
//            if (StringUtils.isNotBlank(xmlString) && Objects.nonNull(resolver)) {
//                XmlMapper xmlMapper = new XmlMapper();
//                try {
//                    STEPProductInformation obj = xmlMapper.readValue(xmlString, STEPProductInformation.class);
//                    initiateUpload(resolver, obj);
//                } catch (IOException e) {
//                    codes = ResponseCodes.ErrorMappingXml;
//                }
//            }
//        } else {
//            codes = ResponseCodes.AuthorModeOnly;
//        }
//        json.addProperty("code", codes.id);
//        json.addProperty("message", codes.msg);
//        return json;
//    }
//
//    /**
//     * Method to readImage from Base64 String
//     *
//     * @param resolver  Sling ResourceResolver object
//     * @param xmlObject Mapped xml Object
//     */
//    private void initiateUpload(ResourceResolver resolver, STEPProductInformation xmlObject) {
//        Asset asset = null;
//        if (isValidObject(xmlObject)) {
//            AssetUpload assetUpload = getImageProperties(xmlObject);
//            assetUpload.setAssetPath(assetUtil.getAssetPath(assetUpload.getUpc()));
////            assetUpload.setAssetPath(ASSET_PATH);
//            assetUpload.setAssetName(assetUpload.getTitle() + "." + assetUpload.getExtension());
//            asset = uploadFile(resolver, assetUpload);
//        } else {
//            codes = ResponseCodes.InvalidXmlFile;
//        }
//    }
//
//    /**
//     * Method to upload the Asset in Dam
//     *
//     * @param resolver    {@code ResourceResolver} object from request
//     * @param assetUpload {@code AssetUpload} bean that contains upload related attributes
//     *
//     * @return asset {@code Asset} Object is returned after create or update
//     */
//    private Asset uploadFile(ResourceResolver resolver, AssetUpload assetUpload ) {
//        Asset asset = null;
//        String newFile = assetUpload.getAssetPath() + assetUpload.getAssetName();
//        boolean assetExists = isAlreadyCreated(newFile, resolver);
//        if (!assetExists) {
//            AssetManager assetMngr = resolver.adaptTo(AssetManager.class);
//            asset = assetMngr.createAsset(newFile, getImageInputStream(assetUpload.getImageString()), assetUpload.getMimetype(), true);
//            if (Objects.nonNull(asset)) {
//                codes = ResponseCodes.AssetCreated;
//                addMetaProperties(newFile, assetUpload, resolver);
//            } else codes = ResponseCodes.CreateFailed;
//        } else {
//            codes = ResponseCodes.AssetAlreadyExists;
//            addMetaProperties(newFile, assetUpload, resolver);
//        }
//        publishAsset(newFile, resolver);
//        return asset;
//    }
//
//    /**
//     * Method to add Metadata to existing or created asset
//     *
//     * @param filePath path of the file to be created or updated
//     * @param assetBean {@code AssetUpload} object populated with props
//     * @param resolver {@code ResourceResolver} object having permissions to write
//     */
//    private void addMetaProperties(String filePath, AssetUpload assetBean, ResourceResolver resolver) {
//        Resource res = resolver.getResource(filePath + METADATA_PATH);
//        ModifiableValueMap map = res.adaptTo(ModifiableValueMap.class);
//        map.put(FORMAT, assetBean.getFormat());
//        map.put(UPC, assetBean.getUpc());
//        map.put(ANGLE, assetBean.getAngle());
//        map.put(TITLE, assetBean.getTitle());
//        map.put(PRODUCT_NAME, assetBean.getProductName());
//        try {
//            res.getResourceResolver().commit();
//        } catch (PersistenceException e) {
//            codes = ResponseCodes.ErrorAddingMetadata;
//        }
//    }
//
//    /**
//     * Method to convert String into {@code InputStream} object
//     * @param imageBase64String xmlImageString
//     * @return InputStream
//     */
//    private InputStream getImageInputStream(String imageBase64String) {
//        byte[] imageBytes = DatatypeConverter.parseBase64Binary(imageBase64String);
//        InputStream targetStream = new ByteArrayInputStream(imageBytes);
//        return targetStream;
//    }
//
//    /**
//     * Method to validate if asset already exists in dam
//     * @param assetPath path of the requested asset
//     * @param resolver {@code ResourceResolver} object
//     * @return {@code Boolean}
//     */
//    private Boolean isAlreadyCreated(String assetPath, ResourceResolver resolver) {
//        Resource res = resolver.getResource(assetPath);
//        return Objects.nonNull(res);
//    }
//
//    /**
//     * Method to check if the object is valid
//     * @param xmlObject {@link STEPProductInformation} object
//     * @return {@link Boolean}
//     */
//    private Boolean isValidObject(STEPProductInformation xmlObject) {
//        return (Objects.nonNull(xmlObject.getAssets()) &&
//                Objects.nonNull(xmlObject.getAssets().getAsset()) &&
//                Objects.nonNull(xmlObject.getAssets().getAsset().getAssetBinaryContent()) &&
//                Objects.nonNull(xmlObject.getAssets().getAsset().getAssetBinaryContent().getMIMEType()) &&
//                StringUtils.isNotBlank(xmlObject.getAssets().getAsset().getAssetBinaryContent().getBinaryContent()));
//    }
//
//    /**
//     * Method to set the image properties to the java bean from xml
//     * @param xmlObject {@link STEPProductInformation} xml object
//     * @return {@code AssetUpload} Asset bean object
//     */
//    private AssetUpload getImageProperties(STEPProductInformation xmlObject) {
//        Value[] metadataValues = xmlObject.getAssets().getAsset().getValues().getValue();
//        AssetUpload asset = new AssetUpload();
//        asset.setImageString(xmlObject.getAssets().getAsset().getAssetBinaryContent().getBinaryContent());
//        for (Value val : metadataValues) {
//            if (val.getAttributeID().equalsIgnoreCase(ASSET_ANGLE))
//                asset.setAngle(val.getContent());
//            if (val.getAttributeID().equalsIgnoreCase(ASSET_TITLE))
//                asset.setTitle(val.getContent());
//            if (val.getAttributeID().equalsIgnoreCase(ASSET_PRODUCT_NAME))
//                asset.setProductName(val.getContent());
//            if (val.getAttributeID().equalsIgnoreCase(ASSET_FORMAT))
//                asset.setFormat(val.getContent());
//            if (val.getAttributeID().equalsIgnoreCase(ASSET_UPC))
//                asset.setUpc(val.getContent());
//            if (val.getAttributeID().equalsIgnoreCase(ASSET_EXTENSION))
//                asset.setExtension(val.getContent());
//            if (val.getAttributeID().equalsIgnoreCase(ASSET_MIME_TYPE))
//                asset.setMimetype(val.getContent());
//        }
//        return asset;
//    }
//
//    /**
//     * Method to read the request from form data
//     * @param request {@link SlingHttpServletRequest} request object
//     * @return {@code String} returns xml string
//     */
//    private String readFormData(SlingHttpServletRequest request) {
//        final boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//        try {
//            if (isMultipart) {
//                Map<String, RequestParameter[]> params = request.getRequestParameterMap();
//                for (final Map.Entry<String, RequestParameter[]> pairs : params.entrySet()) {
//                    final String key = pairs.getKey();
//                    final RequestParameter[] pArr = pairs.getValue();
//                    final RequestParameter param = pArr[0];
//                    if (key.equalsIgnoreCase(IMAGE_XML)) {
//                        return param.getString();
//                    } else {
//                        codes = ResponseCodes.MissingRequiredParams;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            codes = ResponseCodes.InternalServerError;
//            LOG.error("Exception in readFormData: {}", e.getMessage(), e);
//        }
//        return StringUtils.EMPTY;
//    }
//
//    /**
//     * Method to replicate the asset
//     * @param assetPath {@link String} Path of the asset
//     * @param resolver {@link ResourceResolver} resolver object from request
//     */
//    private void publishAsset(String assetPath, ResourceResolver resolver) {
//        Session session = resolver.adaptTo(Session.class);
//        try {
//            replicator.replicate(session, ReplicationActionType.ACTIVATE, assetPath);
//        } catch (ReplicationException e) {
//            codes = ResponseCodes.PublishFailed;
//        }
//    }
//}
