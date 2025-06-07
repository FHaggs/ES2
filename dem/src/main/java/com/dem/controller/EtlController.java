package com.dem.controller;

import com.dem.dto.EtlTransactionDTO;
import com.dem.mapper.EtlTransactionMapper;
import com.dem.model.EtlTransaction;
import com.dem.service.EtlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/etl")
@Tag(name = "ETL", description = "Gestão de operações de Extração, Transformação e Carga")
public class EtlController {
  @Autowired
    private EtlService etlService;

    @PostMapping("/start")
    @Operation(summary = "Iniciar extração e transformação de países")
    public ResponseEntity<EtlTransactionDTO> start() {
        EtlTransaction tx = etlService.startExtraction();
        return ResponseEntity.ok(EtlTransactionMapper.toDTO(tx));
    }

    @PostMapping("/load/{transactionId}")
    @Operation(summary = "Carregar dados transformados no MDM")
    public ResponseEntity<Void> load(@PathVariable Long transactionId) {
        etlService.loadToMdm(transactionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transactions")
    @Operation(summary = "Listar transações ETL")
    public List<EtlTransactionDTO> list() {
        return etlService.listTransactions().stream().map(EtlTransactionMapper::toDTO).collect(Collectors.toList());
    }
} 