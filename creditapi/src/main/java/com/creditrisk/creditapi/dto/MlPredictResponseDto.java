package com.creditrisk.creditapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MlPredictResponseDto {

    @JsonProperty("prob_default")
    private double probDefault;

    @JsonProperty("risk_label")
    private String riskLabel;

    @JsonProperty("model_version")
    private String modelVersion;
}
