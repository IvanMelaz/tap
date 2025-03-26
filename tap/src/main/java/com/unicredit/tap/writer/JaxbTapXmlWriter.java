package com.unicredit.tap.writer;

import com.unicredit.tap.model.TapXmlMapping;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;

public class JaxbTapXmlWriter implements ItemWriter<TapXmlMapping> {

    private static final Logger log = LoggerFactory.getLogger(JaxbTapXmlWriter.class);

    private String outputFilePath;

    public JaxbTapXmlWriter(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void write(Chunk<? extends TapXmlMapping> chunk) throws Exception {
        List<? extends TapXmlMapping> items = chunk.getItems();

        if (!items.isEmpty()) {
            Resource resource = new FileSystemResource(outputFilePath);
            File outputFile = resource.getFile();

            JAXBContext context = JAXBContext.newInstance(TapXmlMapping.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            for (TapXmlMapping item : items) {
                marshaller.marshal(item, outputFile);
                log.info("Marshalled item to XML: {}", item);
            }
        }
    }
}
