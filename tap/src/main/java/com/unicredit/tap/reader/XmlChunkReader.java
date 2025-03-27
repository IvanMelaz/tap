package com.unicredit.tap.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class XmlChunkReader implements ItemReader<String>, ItemStream {

    private static final Logger log = LoggerFactory.getLogger(XmlChunkReader.class);

    private final Resource xmlResource;
    private BufferedReader reader;
    private String xmlContent;
    private boolean read = false;

    public XmlChunkReader(Resource resource) {
        this.xmlResource = resource;
    }

    @Override
    public String read() throws Exception {
        if (!read) {
            read = true;
            return xmlContent;
        } else {
            return null;
        }

    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            if (!xmlResource.exists()) {
                throw new FileNotFoundException("Resource not found: " + xmlResource.getDescription());
            }

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(xmlResource.getFile())));
            StringBuilder contentBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }

            xmlContent = contentBuilder.toString();

        } catch (Exception e) {
            throw new ItemStreamException("Error opening XML file: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // Not needed for this example
    }

    @Override
    public void close() throws ItemStreamException {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                log.error("Error closing XML file: {}", e.getMessage());
            }
        }
    }
}