package com.dem.repository;

import com.dem.model.EtlTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtlTransactionRepository extends JpaRepository<EtlTransaction, Long> {
} 