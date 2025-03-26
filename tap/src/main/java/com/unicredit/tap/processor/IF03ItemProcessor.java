package com.unicredit.tap.processor;

import com.unicredit.tap.model.IF03Mapping;
import com.unicredit.tap.model.TapXmlMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class IF03ItemProcessor implements  ItemProcessor<IF03Mapping,TapXmlMapping> {
    private static final Logger log = LoggerFactory.getLogger(IF03ItemProcessor.class);

    @Override
    public TapXmlMapping process(IF03Mapping item) throws Exception {
        TapXmlMapping tapXmlMapping = new TapXmlMapping();
        log.info("Processing item: {}",item);

        // Example of mapping fields from IF03Mapping to TapXmlMapping
        tapXmlMapping.setFileId(item.getFileId()); // Assuming this is the correct mapping

        log.info("Generating tapXmlMapping: {}",tapXmlMapping);

        return tapXmlMapping;
    }
}
