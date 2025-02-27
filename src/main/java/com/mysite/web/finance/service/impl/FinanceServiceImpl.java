package com.mysite.web.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.web.common.service.IndexingService;
import com.mysite.web.finance.dto.BudgetStatusDTO;
import com.mysite.web.finance.dto.CategoryBudgetDTO;
import com.mysite.web.finance.dto.SavingsStatusDTO;
import com.mysite.web.finance.dto.TransactionDTO;
import com.mysite.web.finance.mapper.FinanceMapper;
import com.mysite.web.finance.service.FinanceService;
import com.mysite.web.login.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

	@Autowired
	private FinanceMapper financeMapper;

	@Autowired
    private IndexingService indexingService;
	
	@Override
	public List<TransactionDTO> getTransactions(String token) {
		// Bearer 토큰에서 실제 토큰 값 추출
		String jwtToken = token.substring(7); // "Bearer " 제거

		// JWT 토큰 검증 및 사용자 정보 추출
		String userEmail = JwtUtil.getSubjectFromToken(jwtToken);
		return financeMapper.getTransactionsByEmail(userEmail);
	}

	@Override
	public List<CategoryBudgetDTO> getCategoryBudgets(String token) {
		// Bearer 토큰에서 실제 토큰 값 추출
		String jwtToken = token.substring(7); // "Bearer " 제거

		// JWT 토큰 검증 및 사용자 정보 추출
		String userEmail = JwtUtil.getSubjectFromToken(jwtToken);
		return financeMapper.getCategoryBudgetsByEmail(userEmail);
	}

	@Override
	public BudgetStatusDTO getBudgetStatus(String token) {
		// Bearer 토큰에서 실제 토큰 값 추출
		String jwtToken = token.substring(7); // "Bearer " 제거

		// JWT 토큰 검증 및 사용자 정보 추출
		String userEmail = JwtUtil.getSubjectFromToken(jwtToken);
		return financeMapper.getBudgetStatusByEmail(userEmail);
	}

	@Override
	public SavingsStatusDTO getSavingsStatus(String token) {
		// Bearer 토큰에서 실제 토큰 값 추출
		String jwtToken = token.substring(7); // "Bearer " 제거

		// JWT 토큰 검증 및 사용자 정보 추출
		String userEmail = JwtUtil.getSubjectFromToken(jwtToken);
		return financeMapper.getSavingsStatusByEmail(userEmail);
	}
}