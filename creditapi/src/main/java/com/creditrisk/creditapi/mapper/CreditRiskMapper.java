package com.creditrisk.creditapi.mapper;

import com.creditrisk.creditapi.dto.CreditRiskApiRequestDto;
import com.creditrisk.creditapi.dto.CreditRiskInputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditRiskMapper {

    CreditRiskInputDto toMlDto(CreditRiskApiRequestDto apiDto);
}
