package com.mysite.web.common.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.web.common.mapper.SearchMapper;
import com.mysite.web.common.service.SearchService;
import com.mysite.web.login.util.JwtUtil;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchMapper searchMapper;

	@Autowired
	private DataSource dataSource; // ✅ DataSource 주입 추가

	@Override
	public List<Map<String, Object>> searchAllTables(String token, String keyword) {

		// Bearer 토큰에서 실제 토큰 값 추출
		String jwtToken = token.substring(7); // "Bearer " 제거

		// JWT 토큰 검증 및 사용자 정보 추출
		Long userId = JwtUtil.getUserIdFromToken(jwtToken);

		// 1. DB에서 모든 테이블 목록 조회
		List<String> tableNames = searchMapper.getAllTableNames();
		List<Map<String, Object>> allResults = new ArrayList<>();

		for (String tableName : tableNames) {
			// 2. 각 테이블의 컬럼 목록 조회
			List<String> columnNames = getTableColumns(tableName);
			if (columnNames.isEmpty())
				continue; // 컬럼이 없으면 스킵

			System.out.println("test11:::::::::::::");
			// 3. 검색 실행
			List<Map<String, Object>> results = searchMapper.searchTable(tableName, keyword, columnNames, userId);
			if (!results.isEmpty()) {
				Map<String, Object> tableResult = new HashMap<>();
				tableResult.put("table", tableName);
				tableResult.put("data", results);
				allResults.add(tableResult);
			}
		}
		return allResults;
	}

	// ✅ 테이블 컬럼 조회 메서드 추가
	private List<String> getTableColumns(String tableName) {
		String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND table_schema = 'public'";
		List<String> columnNames = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, tableName);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					columnNames.add(rs.getString("column_name"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columnNames;
	}
}
