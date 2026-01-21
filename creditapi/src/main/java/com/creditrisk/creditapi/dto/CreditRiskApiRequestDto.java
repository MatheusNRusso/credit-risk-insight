package com.creditrisk.creditapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreditRiskApiRequestDto {

    @NotNull @Min(0) private Double limitBal;

    @NotNull private Integer sex;
    @NotNull private Integer education;
    @NotNull private Integer marriage;
    @NotNull private Integer age;

    @NotNull private Integer pay0;
    @NotNull private Integer pay2;
    @NotNull private Integer pay3;
    @NotNull private Integer pay4;
    @NotNull private Integer pay5;
    @NotNull private Integer pay6;

    @NotNull private Double billAmt1;
    @NotNull private Double billAmt2;
    @NotNull private Double billAmt3;

    @NotNull private Double payAmt1;
    @NotNull private Double payAmt2;
    @NotNull private Double payAmt3;
}
