package com.unicredit.tap.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement
@Data
/**
 * Simplify with Lombok
 */
public class XmlMapping {

    @XmlElement(name = "historicalExchangeRate")
    private String historicalExchangeRate;

    @XmlElement(name = "initialLoadDateAnInt")
    private Date initialLoadDateAnInt;

    @XmlElement(name = "cashAccount")
    private String cashAccount;

    @XmlElement(name = "valueDate")
    private Date valueDate;

    @XmlElement(name = "balance")
    private BigDecimal balance;

}
