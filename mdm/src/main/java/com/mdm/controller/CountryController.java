package com.mdm.controller;

import com.mdm.dto.CountryDTO;
import com.mdm.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Country", description = "Administração de dados mestres de países")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping
    @Operation(summary = "Listar países", description = "Consulta países por nome e/ou código")
    public List<CountryDTO> list(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String code) {
        return countryService.findAll(name, code);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar país por ID")
    public ResponseEntity<CountryDTO> get(@PathVariable Long id) {
        CountryDTO dto = countryService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Criar país")
    public ResponseEntity<CountryDTO> create(@Valid @RequestBody CountryDTO dto) {
        return ResponseEntity.ok(countryService.create(dto));
    }

    @PostMapping("/upsert/batch")
    @Operation(summary = "Criar ou atualizar países em lote (upsert)")
    public ResponseEntity<List<CountryDTO>> upsertBatch(@Valid @RequestBody List<CountryDTO> dtos) {
        List<CountryDTO> result = dtos.stream()
            .map(countryService::upsert)
            .toList();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar país")
    public ResponseEntity<CountryDTO> update(@PathVariable Long id, @Valid @RequestBody CountryDTO dto) {
        return ResponseEntity.ok(countryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir país")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 