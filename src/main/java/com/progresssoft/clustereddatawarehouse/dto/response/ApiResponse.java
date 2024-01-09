package com.progresssoft.clustereddatawarehouse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApiResponse<T> {

    private String responseCode;
    private String responseMessage;
    private Boolean responseStatus;
    private final LocalDateTime responseTime = LocalDateTime.now();
    private T data;

}
