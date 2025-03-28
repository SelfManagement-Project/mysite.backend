package com.mysite.web.finance.model;

import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    private Long transactionId;
    private Long userId;
    private String date;
    private String category;
    private String type;
    private BigDecimal amount;
    private String description;
    private boolean isIncome;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
