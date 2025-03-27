package com.unicredit.tap.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataGroup {

    @JacksonXmlProperty(localName = "CashPosition")
    @JacksonXmlElementWrapper(useWrapping = false) // Add this line
    private List<CashPosition> cashPositions;

    public DataGroup(List<CashPosition> cashPositions) {
        this.cashPositions = cashPositions;
    }
}