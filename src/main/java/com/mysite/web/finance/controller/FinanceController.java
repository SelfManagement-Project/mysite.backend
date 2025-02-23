package com.mysite.web.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.common.util.JsonResult;
import com.mysite.web.finance.dto.BudgetStatusDTO;
import com.mysite.web.finance.dto.CategoryBudgetDTO;
import com.mysite.web.finance.dto.SavingsStatusDTO;
import com.mysite.web.finance.dto.TransactionDTO;
import com.mysite.web.finance.service.FinanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {
    
	@Autowired
    private FinanceService financeService;

    @GetMapping("/transactions")
    public ResponseEntity<JsonResult> getTransactions(
            @RequestHeader("Authorization") String token) {
    	
    	List<TransactionDTO> result = financeService.getTransactions(token);
    	
    	
        return ResponseEntity.ok(JsonResult.success(result));
//        return ResponseEntity.ok(JsonResult.success("test"));
    }

    @GetMapping("/category-budgets")
    public ResponseEntity<JsonResult> getCategoryBudgets(
            @RequestHeader("Authorization") String token) {
    	List<CategoryBudgetDTO> result = financeService.getCategoryBudgets(token);
        return ResponseEntity.ok(JsonResult.success(result));
//    	return ResponseEntity.ok(JsonResult.success("test"));
    }

    @GetMapping("/budget-status")
    public ResponseEntity<JsonResult> getBudgetStatus(
            @RequestHeader("Authorization") String token) {
    	BudgetStatusDTO result = financeService.getBudgetStatus(token);
        return ResponseEntity.ok(JsonResult.success(result));
//    	return ResponseEntity.ok(JsonResult.success("test"));
    }

    @GetMapping("/savings-status")
    public ResponseEntity<JsonResult> getSavingsStatus(
            @RequestHeader("Authorization") String token) {
    	SavingsStatusDTO result = financeService.getSavingsStatus(token);
        return ResponseEntity.ok(JsonResult.success(result));
//    	return ResponseEntity.ok(JsonResult.success("test"));
    }
}