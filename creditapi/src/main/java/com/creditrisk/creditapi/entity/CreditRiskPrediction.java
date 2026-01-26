package com.creditrisk.creditapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "credit_risk_predictions")
@Getter
@NoArgsConstructor
public class CreditRiskPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double probability;

    private String risk;

    private String modelVersion;

    private LocalDateTime createdAt;

    public CreditRiskPrediction(
            Double probability,
            String risk,
            String modelVersion
    ) {
        this.probability = probability;
        this.risk = risk;
        this.modelVersion = modelVersion;

    }
    public static CreditRiskPrediction of(Double probability, String risk, String modelVersion) {
        return new CreditRiskPrediction(probability, risk, modelVersion);
    }

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
