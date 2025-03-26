package com.unicredit.tap.reader;

import com.unicredit.tap.model.IF03Mapping;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class If03MappingItemReader implements ItemReader<IF03Mapping>, ItemStream {

    private Resource resource;
    private BufferedReader reader;
    private List<String[]> lines;
    private int currentIndex = 0;

    public If03MappingItemReader(Resource resource) {
        this.resource = resource;
        this.lines = new ArrayList<>();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            if (!resource.exists()) {
                throw new FileNotFoundException("Resource not found: " + resource.getDescription());
            }
            this.reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            this.reader.readLine(); // Skip header
        } catch (IOException e) {
            throw new ItemStreamException("Error opening resource: " + resource.getDescription(), e);
        }
    }

    @Override
    public IF03Mapping read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (lines.isEmpty()) {
            readCsvFile();
        }

        if (currentIndex < lines.size()) {
            String[] values = lines.get(currentIndex++);
            return mapToIf03Mapping(values);
        } else {
            return null; // End of data
        }
    }

    private void readCsvFile() throws IOException {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";"); // Split by semicolon
                lines.add(values);
            }
        } catch (IOException e) {
            throw new ItemStreamException("Error reading resource: " + resource.getDescription(), e);
        }
    }

    private IF03Mapping mapToIf03Mapping(String[] values) {
        IF03Mapping if03Mapping = new IF03Mapping();
        if03Mapping.setFileId(values[0]);
        if03Mapping.setDateOfAccounting(values[1]);
        if03Mapping.setAccountingDate(values[2]);
        if03Mapping.setPortfolioId(values[3]);
        if03Mapping.setAccountNumber(new BigDecimal(values[4]));
        if03Mapping.setValuationDate(values[5]);
        if03Mapping.setAssetValueBookingDate(Float.parseFloat(values[6]));
        if03Mapping.setCashAccountCurrency(values[7]);
        if03Mapping.setExchangeRate(new BigDecimal(values[8]));
        return if03Mapping;
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // No need to update execution context in this example
    }

    @Override
    public void close() throws ItemStreamException {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                throw new ItemStreamException("Error closing resource: " + resource.getDescription(), e);
            }
        }
    }
}
