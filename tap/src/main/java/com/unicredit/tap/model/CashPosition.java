package com.unicredit.tap.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement(name ="CashPosition" )
@Getter
@Setter
public class CashPosition {

    @XmlElement(name = "balance")
    @XmlJavaTypeAdapter(CashPosition.BigDecimalAdapter.class)
    private BigDecimal balance;

    @XmlElement(name = "cashAccount")
    @XmlJavaTypeAdapter(CashPosition.BigDecimalAdapter.class)
    private BigDecimal cashAccount;

    @XmlElement(name = "historicalExchangeRate")
    @XmlJavaTypeAdapter(CashPosition.BigDecimalAdapter.class)
    private BigDecimal historicalExchangeRate;

    @XmlElement(name = "initialLoadDate")
    @XmlJavaTypeAdapter(CashPosition.DateAdapter.class)
    private Date initialLoadDate;

    @XmlElement(name = "portfolio")
    private String portfolio;

    @XmlElement(name = "positionCurrency")
    private String positionCurrency;

    @XmlElement(name = "valueDate")
    @XmlJavaTypeAdapter(CashPosition.DateAdapter.class)
    private Date valueDate;

    // BigDecimal Adapter
    public static class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {
        @Override
        public BigDecimal unmarshal(String v) throws Exception {
            return new BigDecimal(v);
        }

        @Override
        public String marshal(BigDecimal v) throws Exception {
            return v.toString();
        }
    }

    // Date Adapter
    public static class DateAdapter extends XmlAdapter<String, Date> {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        @Override
        public Date unmarshal(String v) throws Exception {
            return dateFormat.parse(v);
        }

        @Override
        public String marshal(Date v) throws Exception {
            return dateFormat.format(v);
        }
    }
}