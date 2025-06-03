package com.dem.service;

import com.dem.client.MdmClient;
import com.dem.model.EtlTransaction;
import com.dem.model.TransformedCountry;
import com.dem.repository.EtlTransactionRepository;
import com.dem.repository.TransformedCountryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EtlService {
    @Value("https://restcountries.com/v3.1/all")
    private String restCountriesUrl;

    @Autowired
    private EtlTransactionRepository etlTransactionRepository;
    @Autowired
    private TransformedCountryRepository transformedCountryRepository;
    @Autowired
    private MdmClient mdmClient;

    public EtlTransaction startExtraction() {
        EtlTransaction tx = EtlTransaction.builder()
                .status(EtlTransaction.Status.RUNNING)
                .startedAt(LocalDateTime.now())
                .build();
        etlTransactionRepository.save(tx);
        try {
            List<Map<String, Object>> countries = fetchCountries();
            List<TransformedCountry> transformed = countries.stream()
                    .map(c -> transformCountry(c, tx))
                    .collect(Collectors.toList());
            transformedCountryRepository.saveAll(transformed);
            tx.setStatus(EtlTransaction.Status.TRANSFORMED);
            etlTransactionRepository.save(tx);
        } catch (Exception e) {
            tx.setStatus(EtlTransaction.Status.FAILED);
            tx.setErrorMessage(e.getMessage());
            etlTransactionRepository.save(tx);
            throw new RuntimeException("ETL extraction/transform failed: " + e.getMessage(), e);
        }
        return tx;
    }

    @Transactional
    public void loadToMdm(Long transactionId) {
        EtlTransaction tx = etlTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        if (tx.getStatus() != EtlTransaction.Status.TRANSFORMED) {
            throw new IllegalStateException("Transaction not ready for load");
        }
        List<TransformedCountry> countries = transformedCountryRepository.findByEtlTransaction(tx);
        List<Map<String, Object>> payload = countries.stream().map(this::toMdmPayload).collect(Collectors.toList());
        try {
            mdmClient.createCountries(payload);
            tx.setStatus(EtlTransaction.Status.LOADED);
            tx.setFinishedAt(LocalDateTime.now());
            etlTransactionRepository.save(tx);
        } catch (Exception e) {
            tx.setStatus(EtlTransaction.Status.FAILED);
            tx.setErrorMessage(e.getMessage());
            etlTransactionRepository.save(tx);
            throw new RuntimeException("ETL load failed: " + e.getMessage(), e);
        }
    }

    public List<EtlTransaction> listTransactions() {
        return etlTransactionRepository.findAll();
    }

    private List<Map<String, Object>> fetchCountries() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.getForEntity(restCountriesUrl, List.class);
        return response.getBody();
    }

    private TransformedCountry transformCountry(Map<String, Object> raw, EtlTransaction tx) {
        // Normalização básica dos campos
        String name = Optional.ofNullable(raw.get("name")).map(n -> ((Map)n).get("common")).map(Object::toString).orElse("");
        String code = Optional.ofNullable(raw.get("cca2")).map(Object::toString).orElse("");
        String region = Optional.ofNullable(raw.get("region")).map(Object::toString).orElse("");
        String subregion = Optional.ofNullable(raw.get("subregion")).map(Object::toString).orElse("");
        String capital = Optional.ofNullable(raw.get("capital")).filter(List.class::isInstance).map(List.class::cast).filter(l -> !l.isEmpty()).map(l -> l.get(0).toString()).orElse("");
        Long population = Optional.ofNullable(raw.get("population")).map(o -> ((Number)o).longValue()).orElse(0L);
        return TransformedCountry.builder()
                .name(name)
                .code(code)
                .region(region)
                .subregion(subregion)
                .capital(capital)
                .population(population)
                .etlTransaction(tx)
                .build();
    }

    private Map<String, Object> toMdmPayload(TransformedCountry c) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", c.getName());
        map.put("code", c.getCode());
        map.put("region", c.getRegion());
        map.put("subregion", c.getSubregion());
        map.put("capital", c.getCapital());
        map.put("population", c.getPopulation());
        return map;
    }
} 