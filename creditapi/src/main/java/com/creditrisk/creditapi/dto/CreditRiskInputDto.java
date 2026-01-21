package com.creditrisk.creditapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreditRiskInputDto {

    @NotNull @Min(0)
    @JsonProperty("limit_bal")
    private Double limitBal;

    @NotNull
    @JsonProperty("sex")
    private Integer sex;

    @NotNull
    @JsonProperty("education")
    private Integer education;

    @NotNull
    @JsonProperty("marriage")
    private Integer marriage;

    @NotNull
    @JsonProperty("age")
    private Integer age;

    @NotNull @JsonProperty("pay_0") private Integer pay0;
    @NotNull @JsonProperty("pay_2") private Integer pay2;
    @NotNull @JsonProperty("pay_3") private Integer pay3;
    @NotNull @JsonProperty("pay_4") private Integer pay4;
    @NotNull @JsonProperty("pay_5") private Integer pay5;
    @NotNull @JsonProperty("pay_6") private Integer pay6;

    @NotNull @JsonProperty("bill_amt1") private Double billAmt1;
    @NotNull @JsonProperty("bill_amt2") private Double billAmt2;
    @NotNull @JsonProperty("bill_amt3") private Double billAmt3;

    @NotNull @JsonProperty("pay_amt1") private Double payAmt1;
    @NotNull @JsonProperty("pay_amt2") private Double payAmt2;
    @NotNull @JsonProperty("pay_amt3") private Double payAmt3;
}
