package com.creditrisk.creditapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MlBatchResponseDto {

    private Meta meta;

    @JsonProperty("results")
    private List<Item> results;

    @Data
    public static class Meta {
        @JsonProperty("model_version")
        private String modelVersion;
        private int total;
    }

    @Data
    public static class Item {
        private double probability;
        private String risk;
    }
}
