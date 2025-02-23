package com.mysite.web.finance.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.web.finance.dto.BudgetStatusDTO;
import com.mysite.web.finance.dto.CategoryBudgetDTO;
import com.mysite.web.finance.dto.SavingsStatusDTO;
import com.mysite.web.finance.dto.TransactionDTO;

@Mapper
public interface FinanceMapper {
    List<TransactionDTO> getTransactionsByEmail(String email);
    List<CategoryBudgetDTO> getCategoryBudgetsByEmail(String email);
    BudgetStatusDTO getBudgetStatusByEmail(String email);
    SavingsStatusDTO getSavingsStatusByEmail(String email);
}