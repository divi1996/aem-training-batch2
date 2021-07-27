//package com.aem.training2.site.core.services.impl;
//
//import com.aem.training2.site.core.models.AssetXMLBean.STEPProductInformation;
//import com.day.cq.commons.jcr.JcrConstants;
//import com.day.cq.replication.Replicator;
//import org.apache.sling.api.resource.ResourceResolver;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.annotations.Reference;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.Objects;
//import java.util.Optional;
//
//@Component(service = AssetDAMCreationService.class, immediate = true)
//public class AssetDAMCreationServiceImpl implements AssetDAMCreationService {
//
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
//    private static final Logger LOG = LoggerFactory.getLogger(AssetDAMCreationServiceImpl.class);
//    private static final long serialVersionUID = 1L;
//    private static final String METADATA_PATH = "/" + JcrConstants.JCR_CONTENT + "/metadata";
//
//    @Reference
//    private Replicator replicator;
//    @Reference
//    private AssetsUtil assetUtil;
//
//    /**
//     * Method to create Asset using the request Object
//     * that contains Asset mapping in an XML file
//     *
//     * @param stepProductInformation Object contained xml attributes
//     * @param resolver
//     * @return Response
//     */
//    @Override
//    public StiboImageResponseCodes createAsset(STEPProductInformation stepProductInformation, ResourceResolver resolver) {
//        return initiateUpload(resolver, stepProductInformation);
//    }
//
//    /**
//     * Method to readImage from Base64 String
//     *
//     * @param resolver  Sling ResourceResolver object
//     * @param xmlObject Mapped xml Object
//     */
//    private StiboImageResponseCodes initiateUpload(ResourceResolver resolver, STEPProductInformation xmlObject) {
//        if (isValidObject(xmlObject)) {
//            AssetUpload assetUpload = getImageProperties(xmlObject);
//            assetUpload.setAssetPath(assetUtil.getAssetPath(assetUpload.getUpc()));
//            assetUpload.setAssetName(assetUpload.getTitle() + "." + assetUpload.getExtension());
//            return uploadFile(resolver, assetUpload);
//        } else {
//            return StiboImageResponseCodes.InvalidXml;
//        }
//    }
//
//    /**
//     * Method to upload the Asset in Dam
//     *
//     * @param resolver object from request
//     * @param assetUpload bean that contains upload related attributes
//     * @return asset Object is returned after create or update
//     */
//    private StiboImageResponseCodes uploadFile(ResourceResolver resolver, AssetUpload assetUpload) {
//        Asset asset = null;
//        String fileName = assetUpload.getAssetPath() + assetUpload.getAssetName();
//        boolean assetExists = isAlreadyCreated(fileName, resolver);
//        if (!assetExists) {
//            AssetManager assetMngr = resolver.adaptTo(AssetManager.class);
//            if (assetMngr != null) {
//                asset = assetMngr.createAsset(fileName, getImageInputStream(assetUpload.getImageString()), assetUpload.getMimetype(), true);
//                if (Objects.nonNull(asset)) {
//                    return addMetaPropertiesAndPublish(fileName, assetUpload, resolver);
//                } else return StiboImageResponseCodes.CreateFailed;
//            }
//        } else {
//            return addMetaPropertiesAndPublish(fileName, assetUpload, resolver);
//        }
//        return StiboImageResponseCodes.AssetUploadSuccess;
//    }
//
//    /**
//     * Method to add Metadata to existing or created asset
//     *
//     * @param filePath path of the file to be created or updated
//     * @param assetBean object populated with props
//     * @param resolver  object having permissions to write
//     */
//    private StiboImageResponseCodes addMetaPropertiesAndPublish(String filePath, AssetUpload assetBean, ResourceResolver resolver) {
//        Resource res = resolver.getResource(filePath + METADATA_PATH);
//        Session session = resolver.adaptTo(Session.class);
//        ModifiableValueMap map = res.adaptTo(ModifiableValueMap.class);
//        if (map != null) {
//            map.put(FORMAT, assetBean.getFormat());
//            map.put(UPC, assetBean.getUpc());
//            map.put(ANGLE, assetBean.getAngle());
//            map.put(TITLE, assetBean.getTitle());
//            map.put(PRODUCT_NAME, assetBean.getProductName());
//            try {
//                resolver.commit();
//                replicator.replicate(session, ReplicationActionType.ACTIVATE, filePath);
//            } catch (PersistenceException e) {
//                return StiboImageResponseCodes.ErrorAddingMetadata;
//            } catch (ReplicationException e) {
//                return StiboImageResponseCodes.PublishFailed;
//            }
//        }
//        return StiboImageResponseCodes.AssetUploadSuccess;
//    }
//
//    /**
//     * Method to convert String into {@code InputStream} object
//     *
//     * @param imageBase64String xmlImageString
//     * @return InputStream
//     */
//    private InputStream getImageInputStream(String imageBase64String) {
//        byte[] imageBytes = DatatypeConverter.parseBase64Binary(imageBase64String);
//        return new ByteArrayInputStream(imageBytes);
//    }
//
//    /**
//     * Method to validate if asset already exists in dam
//     *
//     * @param assetPath path of the requested asset
//     * @param resolver
//     * @return boolean
//     */
//    private boolean isAlreadyCreated(String assetPath, ResourceResolver resolver) {
//        return resolver.getResource(assetPath) != null;
//    }
//
//    /**
//     * Method to check if the object is valid
//     *
//     * @param xmlObject object
//     * @return boolean
//     */
//    private boolean isValidObject(STEPProductInformation xmlObject) {
//        return Optional.ofNullable(xmlObject.getAssets())
//                .map(Assets::getAsset)
//                .map(AssetBean::getAssetBinaryContent)
//                .filter(content -> content.getMimeType() != null)
//                .map(AssetBinaryContent::getBinaryContent)
//                .map(StringUtils::isNotBlank)
//                .orElse(false);
//    }
//
//    /**
//     * Method to set the image properties to the java bean from xml
//     *
//     * @param xmlObject
//     * @return AssetUpload bean object
//     */
//    private List<AssetUpload> getImageProperties(STEPProductInformation xmlObject) {
//        Value[] metadataValues = xmlObject.getAssets().getAsset().getValues().getValue();
//        AssetUpload asset = new AssetUpload();
//        asset.setImageString(xmlObject.getAssets().getAsset().getAssetBinaryContent().getBinaryContent());
//        for (Value val : metadataValues) {
//            String attributeId = val.getAttributeID();
//            switch (attributeId) {
//                case ASSET_ANGLE:
//                    asset.setAngle(val.getContent());
//                    break;
//                case ASSET_TITLE:
//                    asset.setTitle(val.getContent());
//                    break;
//                case ASSET_PRODUCT_NAME:
//                    asset.setProductName(val.getContent());
//                    break;
//                case ASSET_FORMAT:
//                    asset.setFormat(val.getContent());
//                    break;
//                case ASSET_UPC:
//                    asset.setUpc(val.getContent());
//                    break;
//                case ASSET_EXTENSION:
//                    asset.setExtension(val.getContent());
//                    break;
//                case ASSET_MIME_TYPE:
//                    asset.setMimetype(val.getContent());
//                    break;
//            }
//        }
//        return asset;
//    }
//
//}
