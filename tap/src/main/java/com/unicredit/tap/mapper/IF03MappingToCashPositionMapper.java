package com.unicredit.tap.mapper;

import com.unicredit.tap.model.CashPosition;
import com.unicredit.tap.model.IF03Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IF03MappingToCashPositionMapper {

    IF03MappingToCashPositionMapper INSTANCE = Mappers.getMapper(IF03MappingToCashPositionMapper.class);

    @Mapping(source = "assetValue", target = "balance")
    @Mapping(source = "cashAccountNumber", target = "cashAccount")
    @Mapping(source = "exchangeRate", target = "historicalExchangeRate")
    @Mapping(source = "transferDate", target = "initialLoadDate", dateFormat = "yyyyMMdd")
    @Mapping(source = "portfolioId", target = "portfolio")
    @Mapping(source = "cashAccountCcy", target = "positionCurrency")
    @Mapping(source = "valuationDate", target = "valueDate", dateFormat = "yyyyMMdd")
    CashPosition map(IF03Mapping if03Mapping);
}