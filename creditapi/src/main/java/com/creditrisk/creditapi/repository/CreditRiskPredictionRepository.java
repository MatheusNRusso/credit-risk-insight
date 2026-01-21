package com.creditrisk.creditapi.repository;

import com.creditrisk.creditapi.entity.CreditRiskPrediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRiskPredictionRepository
        extends JpaRepository<CreditRiskPrediction, Long> {
}
