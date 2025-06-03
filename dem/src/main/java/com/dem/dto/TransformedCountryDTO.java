package com.dem.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransformedCountryDTO {
    private Long id;
    private String name;
    private String code;
    private String region;
    private String subregion;
    private String capital;
    private Long population;
    private Long etlTransactionId;
} 