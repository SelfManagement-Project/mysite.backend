package com.mysite.web.health.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.web.common.service.IndexingService;
import com.mysite.web.health.dto.DietRequestDTO;
import com.mysite.web.health.dto.ExerciseRequestDTO;
import com.mysite.web.health.dto.HealthMetricsRequestDTO;
import com.mysite.web.health.dto.SleepRequestDTO;
import com.mysite.web.health.mapper.HealthMapper;
import com.mysite.web.health.service.HealthService;
import com.mysite.web.login.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

	@Autowired
	private HealthMapper healthMapper;

	@Autowired
    private IndexingService indexingService;
	
	
	@Override
	public List<ExerciseRequestDTO> getExercise(String token) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			String userEmail = JwtUtil.getSubjectFromToken(jwtToken);
//	       System.out.println("useremail:::" + userEmail);
			if (userEmail == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// 사용자의 일정 목록 조회
			List<ExerciseRequestDTO> exerciseList = healthMapper.getExercise(userEmail);
//	       System.out.println(scheduleList);
			if (exerciseList == null) {
				return Collections.emptyList();
			}

			return exerciseList;

		} catch (Exception e) {
			log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
		}
	}
	
	
	
	@Override
	public List<DietRequestDTO> getDiet(String token) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			String userEmail = JwtUtil.getSubjectFromToken(jwtToken);
//	       System.out.println("useremail:::" + userEmail);
			if (userEmail == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// 사용자의 일정 목록 조회
			List<DietRequestDTO> dietList = healthMapper.getDiet(userEmail);
//	       System.out.println(scheduleList);
			if (dietList == null) {
				return Collections.emptyList();
			}

			return dietList;

		} catch (Exception e) {
			log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
		}
	}
	
	
	
	@Override
	public List<SleepRequestDTO> getSleep(String token) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			String userEmail = JwtUtil.getSubjectFromToken(jwtToken);
//	       System.out.println("useremail:::" + userEmail);
			if (userEmail == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// 사용자의 일정 목록 조회
			List<SleepRequestDTO> sleepList = healthMapper.getSleep(userEmail);
//	       System.out.println(scheduleList);
			if (sleepList == null) {
				return Collections.emptyList();
			}

			return sleepList;

		} catch (Exception e) {
			log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
		}
	}
	
	

	@Override
	public List<HealthMetricsRequestDTO> getMetrics(String token) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			String userEmail = JwtUtil.getSubjectFromToken(jwtToken);
//	       System.out.println("useremail:::" + userEmail);
			if (userEmail == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// 사용자의 일정 목록 조회
			List<HealthMetricsRequestDTO> metricsList = healthMapper.getMetrics(userEmail);
//	       System.out.println(scheduleList);
			if (metricsList == null) {
				return Collections.emptyList();
			}

			return metricsList;

		} catch (Exception e) {
			log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
		}
	}
	
	
	
	
	
	
	
}
