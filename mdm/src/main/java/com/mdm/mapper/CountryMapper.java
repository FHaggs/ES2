package com.mdm.mapper;

import com.mdm.dto.CountryDTO;
import com.mdm.model.Country;

public class CountryMapper {
    public static CountryDTO toDTO(Country country) {
        if (country == null) return null;
        return CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .code(country.getCode())
                .region(country.getRegion())
                .subregion(country.getSubregion())
                .capital(country.getCapital())
                .population(country.getPopulation())
                .build();
    }

    public static Country toEntity(CountryDTO dto) {
        if (dto == null) return null;
        return Country.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .region(dto.getRegion())
                .subregion(dto.getSubregion())
                .capital(dto.getCapital())
                .population(dto.getPopulation())
                .build();
    }
} 