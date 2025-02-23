package com.mysite.web.finance.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Long transactionId;
    private String date;
    private String category;
    private String type;
    private BigDecimal amount;
    private String description;
    private boolean isIncome;
}