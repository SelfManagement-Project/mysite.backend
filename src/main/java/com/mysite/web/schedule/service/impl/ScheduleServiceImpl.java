package com.mysite.web.schedule.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.web.login.mapper.LoginMapper;
import com.mysite.web.login.util.JwtUtil;
import com.mysite.web.schedule.dto.ScheduleRequestDTO;
import com.mysite.web.schedule.dto.ScheduleResponseDTO;
import com.mysite.web.schedule.mapper.ScheduleMapper;
import com.mysite.web.schedule.model.ScheduleEntity;
import com.mysite.web.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleMapper scheduleMapper;
	// 일정 조회
	@Override
	public List<ScheduleResponseDTO> getScheduleByToken(String token) {
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
	       List<ScheduleResponseDTO> scheduleList = scheduleMapper.getScheduleByToken(userEmail);
//	       System.out.println(scheduleList);
	       if (scheduleList == null) {
	           return Collections.emptyList();
	       }
	       
	       return scheduleList;

	   } catch (Exception e) {
	       log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
	       throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
	   }
	}
	
	@Override
	public int writeScheduleByToken(String token, ScheduleRequestDTO newEvent) {
	   try {
	       // Bearer 토큰에서 실제 토큰 값 추출
	       String jwtToken = token.substring(7); // "Bearer " 제거
	       
	       // JWT 토큰 검증 및 사용자 정보 추출
	       Long userId = JwtUtil.getUserIdFromToken(jwtToken);
	       System.out.println("userid:::" + userId);
	       if (userId == null) {
	           throw new RuntimeException("유효하지 않은 토큰입니다.");
	       }

	    // ScheduleEntity로 변환
        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
            .userId(userId)
            .title(newEvent.getTitle())
            .date(newEvent.getDate())
            .start(newEvent.getStart())
            .end(newEvent.getEnd())
            .type(newEvent.getType())
            .description(newEvent.getDescription())
            .status("active")
//            .createdAt(LocalDateTime.now().toString())
//            .updatedAt(LocalDateTime.now().toString())
            .build();
	       
	       
	       int writeSchedule = scheduleMapper.writeScheduleByToken(scheduleEntity);
//	       System.out.println(scheduleList);
	       
	       
//	       return 0;
	       return writeSchedule;

	   } catch (Exception e) {
	       log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
	       throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
	   }
	}
	
	
	
}
