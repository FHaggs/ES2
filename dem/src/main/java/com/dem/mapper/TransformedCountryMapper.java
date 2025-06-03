package com.dem.mapper;

import com.dem.dto.TransformedCountryDTO;
import com.dem.model.TransformedCountry;

public class TransformedCountryMapper {
    public static TransformedCountryDTO toDTO(TransformedCountry entity) {
        if (entity == null) return null;
        return TransformedCountryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .region(entity.getRegion())
                .subregion(entity.getSubregion())
                .capital(entity.getCapital())
                .population(entity.getPopulation())
                .etlTransactionId(entity.getEtlTransaction() != null ? entity.getEtlTransaction().getId() : null)
                .build();
    }
} 