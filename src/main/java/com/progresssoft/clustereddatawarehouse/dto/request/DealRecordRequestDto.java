package com.progresssoft.clustereddatawarehouse.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Immutable Dto to carry Deals info
 * @param dealId
 * @param fromCurrency
 * @param toCurrency
 * @param dealTimestamp
 * @param dealAmount
 */
public record DealRecordRequestDto(
        @NotBlank(message = "deal Id is required")
        String dealId,
        String fromCurrency, String toCurrency,
        LocalDateTime dealTimestamp,
        double dealAmount
) {
}
