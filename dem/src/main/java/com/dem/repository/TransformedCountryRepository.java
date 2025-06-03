package com.dem.repository;

import com.dem.model.TransformedCountry;
import com.dem.model.EtlTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransformedCountryRepository extends JpaRepository<TransformedCountry, Long> {
    List<TransformedCountry> findByEtlTransaction(EtlTransaction etlTransaction);
} 