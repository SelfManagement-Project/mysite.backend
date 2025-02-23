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
public class BudgetStatusDTO {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal totalBudget;
    private BigDecimal usedAmount;
    private BigDecimal remaining;
    private BigDecimal usagePercentage;
}