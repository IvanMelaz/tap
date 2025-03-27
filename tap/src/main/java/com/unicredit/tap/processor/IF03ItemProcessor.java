package com.unicredit.tap.processor;

import com.unicredit.tap.mapper.IF03MappingToCashPositionMapper;
import com.unicredit.tap.model.CashPosition;
import com.unicredit.tap.model.IF03Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class IF03ItemProcessor implements  ItemProcessor<IF03Mapping, CashPosition> {
    private static final Logger log = LoggerFactory.getLogger(IF03ItemProcessor.class);

    @Override
    public CashPosition process(IF03Mapping item) throws Exception {
        log.info("Processing item: {}",item);


        CashPosition cashPosition = IF03MappingToCashPositionMapper.INSTANCE.map(item);
        // Example of mapping fields from IF03Mapping to TapXmlMapping
        log.info("Converting cashPosition: {}",cashPosition);
        return cashPosition;
    }


}
