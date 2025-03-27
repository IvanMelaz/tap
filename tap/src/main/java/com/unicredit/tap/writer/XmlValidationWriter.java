package com.unicredit.tap.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.Resource;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlValidationWriter implements ItemWriter<String> {

    private static final Logger log = LoggerFactory.getLogger(XmlValidationWriter.class);

    private final List<Resource> xsdResources;

    public XmlValidationWriter(List<Resource> xsdResources) {
        this.xsdResources = xsdResources;
    }

    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        for (String xmlString : chunk.getItems()) {
            Map<String, Schema> loadedSchemas = new HashMap<>(); // Store loaded schemas

            try {
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                factory.setResourceResolver(new CustomResourceResolver(loadedSchemas)); // Set custom resolver

                for (Resource resource : xsdResources) {
                    Schema schema = factory.newSchema(new StreamSource(resource.getInputStream()));
                    loadedSchemas.put(resource.getFilename(), schema); // Add loaded schema to map
                }

                for (Schema schema : loadedSchemas.values()) {
                    Validator validator = schema.newValidator();
                    validator.validate(new StreamSource(new StringReader(xmlString)));
                    log.info("XML validation against a schema successful.");
                }

                log.info("XML validation against all schemas successful.");

            } catch (SAXException | IOException e) {
                log.error("XML validation failed: {}", e.getMessage());
                throw new Exception("XML validation failed: " + e.getMessage());
            }
        }
    }

    // Custom LSResourceResolver
    private static class CustomResourceResolver implements LSResourceResolver {

        private final Map<String, Schema> loadedSchemas;

        public CustomResourceResolver(Map<String, Schema> loadedSchemas) {
            this.loadedSchemas = loadedSchemas;
        }

        @Override
        public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
            log.info("Resolving systemId: {}", systemId); // Add this line
            if (loadedSchemas.containsKey(systemId)) {
                return new LSInputImpl(loadedSchemas.get(systemId));
            }
            return null;
        }
    }

    // LSInput Implementation for providing loaded Schema
    private static class LSInputImpl implements LSInput {
        private Schema schema;

        public LSInputImpl(Schema schema) {
            this.schema = schema;
        }

        @Override
        public String getPublicId() {
            return null;
        }

        @Override
        public void setPublicId(String publicId) {

        }

        @Override
        public String getSystemId() {
            return null;
        }

        @Override
        public void setSystemId(String systemId) {

        }

        @Override
        public String getBaseURI() {
            return null;
        }

        @Override
        public void setBaseURI(String baseURI) {

        }

        @Override
        public String getEncoding() {
            return null;
        }

        @Override
        public void setEncoding(String encoding) {

        }

        @Override
        public boolean getCertifiedText() {
            return false;
        }

        @Override
        public void setCertifiedText(boolean certifiedText) {

        }

        @Override
        public java.io.InputStream getByteStream() {
            return null;
        }

        @Override
        public void setByteStream(java.io.InputStream byteStream) {

        }

        @Override
        public java.io.Reader getCharacterStream() {
            return null;
        }

        @Override
        public void setCharacterStream(java.io.Reader characterStream) {

        }

        @Override
        public String getStringData() {
            return null;
        }

        @Override
        public void setStringData(String stringData) {

        }
    }
}
