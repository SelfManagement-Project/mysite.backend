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
public class CategoryBudgetDTO {
    private String categoryName;
    private BigDecimal amount;
    private BigDecimal percentage;
}