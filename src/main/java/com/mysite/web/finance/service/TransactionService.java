package com.mysite.web.finance.service;

import com.mysite.web.finance.dto.TransactionRequestDTO;
import com.mysite.web.finance.dto.TransactionResponseDTO;
import java.util.List;
import java.util.Map;

public interface TransactionService {
	Map<String, Object> getTransactions(String token, int page, int pageSize, String type, String category,
            String search, String startDate, String endDate, String sortField,
            String sortDirection);

	int deleteTransaction(String token, Long transactionId);
	void addTransaction(String token, TransactionRequestDTO transactionRequestDTO);
}
