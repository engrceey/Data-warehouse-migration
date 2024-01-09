package com.progresssoft.clustereddatawarehouse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DealTableHeaders {
    DEAL_ID("deal_id"),
    FROM_CURRENCY("from_currency"),
    TO_CURRENCY("to_currency"),
    DEAL_TIMESTAMP("deal_timestamp"),
    DEAL_AMOUNT("deal_amount");
    private final String name;

}
