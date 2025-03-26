package com.mysite.web.health.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.web.common.service.IndexingService;
import com.mysite.web.health.dto.DietRequestDTO;
import com.mysite.web.health.dto.DietResponseDTO;
import com.mysite.web.health.dto.ExerciseRequestDTO;
import com.mysite.web.health.dto.ExerciseResponseDTO;
import com.mysite.web.health.dto.HealthMetricsRequestDTO;
import com.mysite.web.health.dto.HealthMetricsResponseDTO;
import com.mysite.web.health.dto.SleepRequestDTO;
import com.mysite.web.health.dto.SleepResponseDTO;
import com.mysite.web.health.mapper.HealthMapper;
import com.mysite.web.health.model.DietEntity;
import com.mysite.web.health.model.ExerciseEntity;
import com.mysite.web.health.model.HealthMetricsEntity;
import com.mysite.web.health.model.SleepEntity;
import com.mysite.web.health.service.HealthService;
import com.mysite.web.login.util.JwtUtil;
import com.mysite.web.schedule.model.CalendarEntity;

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
	public List<ExerciseResponseDTO> getExercise(String token, String date) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//	       System.out.println("useremail:::" + userEmail);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			ExerciseEntity exerciseEntity = ExerciseEntity.builder().userId(userId).createdAt(date).build();

			// 사용자의 일정 목록 조회
			List<ExerciseResponseDTO> exerciseList = healthMapper.getExercise(exerciseEntity);
	       System.out.println("exerciseList:::" + exerciseList);
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
	public List<DietResponseDTO> getDiet(String token, String date) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//		       System.out.println("useremail:::" + userEmail);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			DietEntity dietEntity = DietEntity.builder().userId(userId).createdAt(date).build();
			
			// 사용자의 일정 목록 조회
			List<DietResponseDTO> dietList = healthMapper.getDiet(dietEntity);
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
	public List<SleepResponseDTO> getSleep(String token, String date) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//		       System.out.println("useremail:::" + userEmail);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}
			SleepEntity sleepEntity = SleepEntity.builder().userId(userId).createdAt(date).build();
			// 사용자의 일정 목록 조회
			List<SleepResponseDTO> sleepList = healthMapper.getSleep(sleepEntity);
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
	public List<HealthMetricsResponseDTO> getMetrics(String token, String date) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//		       System.out.println("useremail:::" + userEmail);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			HealthMetricsEntity healthMetricsEntity = HealthMetricsEntity.builder().userId(userId).createdAt(date).build();
			
			// 사용자의 일정 목록 조회
			List<HealthMetricsResponseDTO> metricsList = healthMapper.getMetrics(healthMetricsEntity);
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
