package com.mysite.web.finance.service;

import java.util.List;

import com.mysite.web.finance.dto.BudgetStatusDTO;
import com.mysite.web.finance.dto.CategoryBudgetDTO;
import com.mysite.web.finance.dto.SavingsStatusDTO;
import com.mysite.web.finance.dto.TransactionDTO;

public interface FinanceService {
    List<TransactionDTO> getTransactions(String token);
    List<CategoryBudgetDTO> getCategoryBudgets(String token);
    BudgetStatusDTO getBudgetStatus(String token);
    SavingsStatusDTO getSavingsStatus(String token);
}