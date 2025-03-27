package com.unicredit.tap.writer;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.unicredit.tap.model.CashPosition;
import com.unicredit.tap.model.DataGroup;
import com.unicredit.tap.model.PositionPMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.List;

public class CashPositionXmlWriter implements ItemWriter<CashPosition> {

    private static final Logger log = LoggerFactory.getLogger(CashPositionXmlWriter.class);
    private final String outputFilePath;

    public CashPositionXmlWriter(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void write(Chunk<? extends CashPosition> chunk) throws Exception {
        List<? extends CashPosition> items = chunk.getItems();

        if (!items.isEmpty()) {
            File outputFile = new FileSystemResource(outputFilePath).getFile();

            DataGroup dataGroup = new DataGroup((List<CashPosition>) items);
            PositionPMS positionPMS = new PositionPMS(dataGroup);

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable pretty printing
            xmlMapper.writeValue(outputFile, positionPMS);

            log.info("Marshalled chunk to XML: {}", outputFile.getAbsolutePath());
        }
    }
}