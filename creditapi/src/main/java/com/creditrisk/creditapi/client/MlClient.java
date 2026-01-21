package com.creditrisk.creditapi.client;

import com.creditrisk.creditapi.dto.CreditRiskInputDto;
import com.creditrisk.creditapi.dto.MlBatchResponseDto;
import com.creditrisk.creditapi.dto.MlPredictResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class MlClient {

    private final WebClient mlWebClient;
    private final ObjectMapper objectMapper;

    public MlPredictResponseDto predict(CreditRiskInputDto dto) {
        try {
            System.out.println("JSON ENVIADO PARA PYTHON:");
            System.out.println(objectMapper.writeValueAsString(dto));

            return mlWebClient.post()
                    .uri("/predict")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(MlPredictResponseDto.class)
                    .block();

        } catch (WebClientResponseException e) {
            System.out.println("ML STATUS: " + e.getStatusCode());
            System.out.println("ML BODY: " + e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public MlBatchResponseDto predictBatch(byte[] csvBytes, String filename) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("file", new ByteArrayResource(csvBytes) {
                    @Override
                    public String getFilename() { return filename; }
                })
                .contentType(MediaType.TEXT_PLAIN);

        return mlWebClient.post()
                .uri("/predict/batch")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(MlBatchResponseDto.class)
                .block();
    }
}
