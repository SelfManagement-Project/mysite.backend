package com.mysite.web.finance.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionResponseDTO {
    private Long transactionId;
    private String date;
    private String category;
    private String type;
    private BigDecimal amount;
    private String description;
    private boolean isIncome;
    private String createdAt;
    private String updatedAt;
}
