package com.mysite.web.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SearchMapper {

    // 모든 테이블 목록 조회
    List<String> getAllTableNames();

    // 특정 테이블에서 검색 (XML 동적 SQL 사용)
    List<Map<String, Object>> searchTable(@Param("tableName") String tableName, @Param("keyword") String keyword, @Param("columns") List<String> columns);
}
