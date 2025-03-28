package com.unicredit.tap.writer;

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
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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

            String xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(positionPMS);
            String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
            xmlString = xmlDeclaration + xmlString;

            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {
                writer.write(xmlString);
            }

            log.info("Marshalled chunk to XML: {}", outputFile.getAbsolutePath());
        }
    }
}