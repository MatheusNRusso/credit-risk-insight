package com.creditrisk.creditapi.service;

import com.creditrisk.creditapi.client.MlClient;
import com.creditrisk.creditapi.dto.CreditRiskApiRequestDto;
import com.creditrisk.creditapi.dto.MlBatchResponseDto;
import com.creditrisk.creditapi.dto.MlPredictResponseDto;
import com.creditrisk.creditapi.entity.CreditRiskPrediction;
import com.creditrisk.creditapi.mapper.CreditRiskMapper;
import com.creditrisk.creditapi.repository.CreditRiskPredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreditRiskService {

    private final MlClient mlClient;
    private final CreditRiskPredictionRepository repository;
    private final CreditRiskMapper creditRiskMapper;

    public MlPredictResponseDto predict(CreditRiskApiRequestDto apiDto) {

        System.out.println("API DTO (camel): " + apiDto);
        var mlDto = creditRiskMapper.toMlDto(apiDto);
        System.out.println("ML DTO (java camel, json snake): " + mlDto);

        var responseRisk = mlClient.predict(mlDto);

        var entityRisk = CreditRiskPrediction.of(
                responseRisk.getProbDefault(),
                responseRisk.getRiskLabel(),
                responseRisk.getModelVersion()
        );

        repository.save(entityRisk);
        return responseRisk;
    }

    public MlBatchResponseDto predictBatch(MultipartFile file) throws IOException {
        return mlClient.predictBatch(file.getBytes(), file.getOriginalFilename());
    }
}
