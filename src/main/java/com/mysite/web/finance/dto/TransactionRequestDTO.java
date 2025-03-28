package com.mysite.web.finance.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    private String date;
    private String category;
    private String type;
    private BigDecimal amount;
    private String description;
    private boolean isIncome;
}
