package com.mysite.web.finance.mapper;

import com.mysite.web.finance.dto.TransactionResponseDTO;
import com.mysite.web.finance.model.TransactionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TransactionMapper {
	List<TransactionResponseDTO> getTransactions(@Param("entity") TransactionEntity entity, @Param("offset") int offset,
			@Param("pageSize") int pageSize, @Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("sortField") String sortField, @Param("sortDirection") String sortDirection);
	
	// 총 데이터 개수 조회 추가
    int getTransactionTotalCount(@Param("entity") TransactionEntity entity,
                                 @Param("startDate") String startDate,
                                 @Param("endDate") String endDate);

	int deleteTransaction(TransactionEntity entity);
	void addTransaction(TransactionEntity entity);
}
