package com.progresssoft.clustereddatawarehouse.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "deals_tbl",
        indexes = {@Index(name = "deal-ref-index",  columnList="deal_id", unique = true)})
@NoArgsConstructor
public class DealEntity extends BaseEntity {

    @Column(name = "deal_id", unique = true, nullable = false)
    private String dealId;
    @Column(name = "from_currency", length = 4)
    private String fromCurrency;
    @Column(name = "to_currency", length = 4)
    private String toCurrency;
    @Column(name = "deal_timestamp")
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime dealTimestamp;
    @Column(name = "deal_amount")
    private double dealAmount;

}
