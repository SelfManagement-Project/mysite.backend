package com.mysite.web.finance.controller;

import com.mysite.web.common.util.JsonResult;
import com.mysite.web.finance.dto.TransactionRequestDTO;
import com.mysite.web.finance.dto.TransactionResponseDTO;
import com.mysite.web.finance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finance/info")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/transactions")
	public ResponseEntity<JsonResult> getTransactions(@RequestHeader("Authorization") String token,
			@RequestParam("page") int page, @RequestParam("pageSize") int pageSize,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "sortField", required = false) String sortField,
			@RequestParam(value = "sortDirection", required = false) String sortDirection) {
		Map<String, Object> response = transactionService.getTransactions(token, page, pageSize, type, category, search,
				startDate, endDate, sortField, sortDirection);
		System.out.println(response);
		return ResponseEntity.ok(JsonResult.success(response));
	}

	@PostMapping
	public ResponseEntity<JsonResult> addTransaction(@RequestHeader("Authorization") String token,
			@RequestBody TransactionRequestDTO transactionRequestDTO) {
		transactionService.addTransaction(token, transactionRequestDTO);
		return ResponseEntity.ok(JsonResult.success("거래 추가 완료"));
	}

	// 일정 삭제
	@DeleteMapping("transactions/{transactionId}")
	public ResponseEntity<JsonResult> deleteTransaction(@RequestHeader("Authorization") String token,
			@PathVariable("transactionId") Long transactionId) {

		int count = transactionService.deleteTransaction(token, transactionId);
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 수정, 삭제 등의 메서드 추가 가능
}
