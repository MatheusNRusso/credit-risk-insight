package com.creditrisk.creditapi.controller;

import com.creditrisk.creditapi.dto.CreditRiskApiRequestDto;
import com.creditrisk.creditapi.dto.CreditRiskInputDto;
import com.creditrisk.creditapi.dto.MlBatchResponseDto;
import com.creditrisk.creditapi.dto.MlPredictResponseDto;
import com.creditrisk.creditapi.service.CreditRiskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CreditRiskController {

    private final CreditRiskService service;

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of("status", "ok");
    }

    @PostMapping("/predict")
    public MlPredictResponseDto predict(@Valid @RequestBody CreditRiskApiRequestDto dto) {
        return service.predict(dto);
    }

    @PostMapping(value = "/predict/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MlBatchResponseDto predictBatch(@RequestParam("file") MultipartFile file) throws Exception {
        return service.predictBatch(file);
    }
}
