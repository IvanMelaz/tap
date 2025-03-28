package com.unicredit.tap.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JacksonXmlRootElement(localName = "PositionPMS")
@Getter
@Setter
@NoArgsConstructor
//@JsonRootName(namespace = "http://www.odcgroup.com/PositionPMS", value = "PositionPMS") //Important for namespace
public class PositionPMS {

    @JacksonXmlProperty(localName = "DataGroup")
    private DataGroup dataGroup;

    public PositionPMS(DataGroup dataGroup) {
        this.dataGroup = dataGroup;
    }
}
