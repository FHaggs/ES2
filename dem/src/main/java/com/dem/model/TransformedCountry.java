package com.dem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransformedCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String region;
    private String subregion;
    private String capital;
    private Long population;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etl_transaction_id")
    private EtlTransaction etlTransaction;
} 