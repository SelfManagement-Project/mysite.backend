package com.mysite.web.finance.service.impl;

import com.mysite.web.common.service.IndexingService;
import com.mysite.web.finance.dto.TransactionRequestDTO;
import com.mysite.web.finance.dto.TransactionResponseDTO;
import com.mysite.web.finance.mapper.TransactionMapper;
import com.mysite.web.finance.model.TransactionEntity;
import com.mysite.web.finance.service.TransactionService;
import com.mysite.web.login.util.JwtUtil;
import com.mysite.web.schedule.model.CalendarEntity;
import com.mysite.web.schedule.service.impl.ScheduleServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;
    
    @Autowired
	private IndexingService indexingService;

    @Override
    public Map<String, Object> getTransactions(
            String token, int page, int pageSize, String type, String category, String search, 
            String startDate, String endDate, String sortField, String sortDirection) {

        Long userId = JwtUtil.getUserIdFromToken(token.substring(7));
        int offset = (page - 1) * pageSize;

        TransactionEntity entity = TransactionEntity.builder()
                .userId(userId)
                .type(type)
                .category(category)
                .description(search)
                .build();

        List<TransactionResponseDTO> result = transactionMapper.getTransactions(entity, offset, pageSize, startDate, endDate, sortField, sortDirection);
        
        // 추가: 총 개수 조회 후 전체 페이지 수 계산
        int totalCount = transactionMapper.getTransactionTotalCount(entity, startDate, endDate);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        Map<String, Object> response = new HashMap<>();
        response.put("items", result);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }
    
    
    
    @Override
	public int deleteTransaction(String token, Long transactionId) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//			System.out.println("userid:::" + userId);
			
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// ScheduleEntity로 변환 - 삭제시에는 userId와 scheduleId만 필요
			TransactionEntity transactionEntity = TransactionEntity.builder().userId(userId).transactionId(transactionId).build();

			int deleteTransaction = transactionMapper.deleteTransaction(transactionEntity);

			// 삭제된 일정을 인덱스에서도 삭제
			if (deleteTransaction > 0) {
				// 인덱스에서 삭제 요청
				indexingService.deleteFromIndex("Transaction", transactionId);
			}

			return deleteTransaction;

		} catch (Exception e) {
			log.error("캘린더 삭제 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 삭제 처리 중 오류가 발생했습니다.", e);
		}
	}
    
    
    
    
    
    
    
    
    
    


    @Override
    public void addTransaction(String token, TransactionRequestDTO dto) {
        Long userId = JwtUtil.getUserIdFromToken(token.substring(7));
        TransactionEntity entity = TransactionEntity.builder()
                .userId(userId)
                .date(dto.getDate())
                .category(dto.getCategory())
                .type(dto.getType())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .isIncome(dto.isIncome())
                .build();
        transactionMapper.addTransaction(entity);
    }
}
