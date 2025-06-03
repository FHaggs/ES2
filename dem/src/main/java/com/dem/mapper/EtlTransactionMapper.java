package com.dem.mapper;

import com.dem.dto.EtlTransactionDTO;
import com.dem.model.EtlTransaction;

public class EtlTransactionMapper {
    public static EtlTransactionDTO toDTO(EtlTransaction entity) {
        if (entity == null) return null;
        return EtlTransactionDTO.builder()
                .id(entity.getId())
                .status(entity.getStatus().name())
                .startedAt(entity.getStartedAt())
                .finishedAt(entity.getFinishedAt())
                .errorMessage(entity.getErrorMessage())
                .build();
    }
} 