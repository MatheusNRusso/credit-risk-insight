package com.creditrisk.creditapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "credit_risk_predictions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditRiskPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double probability;

    private String risk;

    private String modelVersion;

    private LocalDateTime createdAt;
}
