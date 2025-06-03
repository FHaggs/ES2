package com.mdm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String code;
    private String region;
    private String subregion;
    private String capital;
    private Long population;
} 