package com.mysite.web.schedule.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.web.common.service.IndexingService;
import com.mysite.web.login.mapper.LoginMapper;
import com.mysite.web.login.util.JwtUtil;
import com.mysite.web.schedule.dto.CalendarRequestDTO;
import com.mysite.web.schedule.dto.CalendarResponseDTO;
import com.mysite.web.schedule.dto.HabitProgressDTO;
import com.mysite.web.schedule.dto.TaskRequestDTO;
import com.mysite.web.schedule.dto.TaskResponseDTO;
import com.mysite.web.schedule.dto.WeeklyProgressResponseDTO;
import com.mysite.web.schedule.mapper.ScheduleMapper;
import com.mysite.web.schedule.model.CalendarEntity;
import com.mysite.web.schedule.model.TaskEntity;
import com.mysite.web.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleMapper scheduleMapper;

	@Autowired
	private IndexingService indexingService;
	
	// 일정 조회
	@Override
	public List<CalendarResponseDTO> getScheduleByToken(String token) {
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
			List<CalendarResponseDTO> scheduleList = scheduleMapper.getScheduleByToken(userEmail);
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
	public int writeScheduleByToken(String token, CalendarRequestDTO newEvent) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//			System.out.println("userid:::" + userId);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// ScheduleEntity로 변환
			CalendarEntity scheduleEntity = CalendarEntity.builder().userId(userId).title(newEvent.getTitle())
					.date(newEvent.getDate()).start(newEvent.getStart()).end(newEvent.getEnd()).type(newEvent.getType())
					.description(newEvent.getDescription()).priority(newEvent.getPriority()).status("active")
//            .createdAt(LocalDateTime.now().toString())
//            .updatedAt(LocalDateTime.now().toString())
					.build();

			int writeSchedule = scheduleMapper.writeScheduleByToken(scheduleEntity);
//	       System.out.println(scheduleList);
			
			// 새로 추가된 일정을 인덱싱
			if (writeSchedule > 0 && scheduleEntity.getScheduleId() != null) {
				// 인덱싱 요청
				indexingService.indexRecord("schedule", scheduleEntity.getScheduleId());
			}

//	       return 0;
			return writeSchedule;

		} catch (Exception e) {
			log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	public int modifyScheduleByToken(String token, CalendarRequestDTO newEvent) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//			System.out.println("userid:::" + userId);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// ScheduleEntity로 변환
			CalendarEntity scheduleEntity = CalendarEntity.builder().userId(userId).scheduleId(newEvent.getScheduleId()).title(newEvent.getTitle())
					.date(newEvent.getDate()).start(newEvent.getStart()).end(newEvent.getEnd()).type(newEvent.getType())
					.description(newEvent.getDescription()).status("active")
//            .createdAt(LocalDateTime.now().toString())
//            .updatedAt(LocalDateTime.now().toString())
					.build();

			
			int modifySchedule = scheduleMapper.modifyScheduleByToken(scheduleEntity);
//	       System.out.println(scheduleList);

			// 수정된 일정을 인덱싱
			if (modifySchedule > 0) {
				// 인덱싱 요청
				indexingService.indexRecord("schedule", scheduleEntity.getScheduleId());
			}
//	       return 0;
			return modifySchedule;

		} catch (Exception e) {
			log.error("캘린더 수정 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 수정 처리 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	public int deleteScheduleByToken(String token, Long scheduleId) {
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
			CalendarEntity scheduleEntity = CalendarEntity.builder().userId(userId).scheduleId(scheduleId).build();

			int deleteSchedule = scheduleMapper.deleteScheduleByToken(scheduleEntity);

			// 삭제된 일정을 인덱스에서도 삭제
			if (deleteSchedule > 0) {
				// 인덱스에서 삭제 요청
				indexingService.deleteFromIndex("schedule", scheduleId);
			}
			
			return deleteSchedule;

		} catch (Exception e) {
			log.error("캘린더 삭제 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 삭제 처리 중 오류가 발생했습니다.", e);
		}
	}

	// 오늘 할 일 조회
	@Override
	public List<CalendarResponseDTO> getTodosByToken(String token) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//		       System.out.println("useremail:::" + userEmail);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// 사용자의 일정 목록 조회
			List<CalendarResponseDTO> taskList = scheduleMapper.getTodosByToken(userId);
//			System.out.println(taskList);
			if (taskList == null) {
				return Collections.emptyList();
			}

			return taskList;

		} catch (Exception e) {
			log.error("오늘 할 일 조회 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("오늘 할 일 조회 처리 중 오류가 발생했습니다.", e);
		}
	}
	
	
	
	
	@Override
	public int modifyTodosByToken(String token, Long scheduleId, TaskRequestDTO taskRequestDTO) {
//		System.out.println("calendarRequestDTO:::" + calendarRequestDTO.isCompleted());
//		System.out.println("scheduleId:::" + scheduleId);
	    try {
	        // Bearer 토큰에서 실제 토큰 값 추출
	        String jwtToken = token.substring(7);

	        // JWT 토큰 검증 및 사용자 정보 추출
	        Long userId = JwtUtil.getUserIdFromToken(jwtToken);
//	        System.out.println("userid:::" + userId);
	        if (userId == null) {
	            throw new RuntimeException("유효하지 않은 토큰입니다.");
	        }

	        // TaskEntity 생성
	        TaskEntity taskEntity = TaskEntity.builder()
	            .scheduleId(scheduleId)
	            .userId(userId)
	            .isCompleted(taskRequestDTO.getIsCompleted())  // 체크박스를 토글하는 것이므로 true로 설정
//	            .updatedAt(LocalDateTime.now().toString())
	            .build();

	        
	        System.out.println("taskEntity:::"+ taskEntity);
	        int modifyTodos = scheduleMapper.modifyTodosByToken(taskEntity);
	        
	        // 수정된 할 일을 인덱싱
	        if (modifyTodos > 0) {
	            // 인덱싱 요청
	            indexingService.indexRecord("schedule", scheduleId);
	        }
	        return modifyTodos;

	    } catch (Exception e) {
	        log.error("할일 수정 처리 중 오류 발생: {}", e.getMessage(), e);
	        throw new RuntimeException("할일 수정 처리 중 오류가 발생했습니다.", e);
	    }
	}
	
	
	// 다가오는 일정 조회
	@Override
	public List<CalendarResponseDTO> getUpcomingByToken(String token) {
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			// 사용자의 일정 목록 조회
			List<CalendarResponseDTO> scheduleList = scheduleMapper.getUpcomingByToken(userId);
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
    public WeeklyProgressResponseDTO getWeeklyProgress(String token) {
		
		try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			int totalTasks = scheduleMapper.getTotalWeeklyTasks(userId);
	        int completedTasks = scheduleMapper.getCompletedWeeklyTasks(userId);
	        
	        
	        WeeklyProgressResponseDTO info = new WeeklyProgressResponseDTO(completedTasks, totalTasks);
	        
	        
	        return info;


		} catch (Exception e) {
			log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
		}
		

    }
	
	 @Override
    public List<HabitProgressDTO> getHabitsByUser(String token) {
		 
		 
		 
		 try {
			// Bearer 토큰에서 실제 토큰 값 추출
			String jwtToken = token.substring(7); // "Bearer " 제거

			// JWT 토큰 검증 및 사용자 정보 추출
			Long userId = JwtUtil.getUserIdFromToken(jwtToken);
			if (userId == null) {
				throw new RuntimeException("유효하지 않은 토큰입니다.");
			}

			List<HabitProgressDTO> habits = scheduleMapper.getHabitsWithProgress(userId);
	        
//	        System.out.println(habits);
	        
			return habits;
		} catch (Exception e) {
			log.error("캘린더 조회 처리 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("캘린더 조회 처리 중 오류가 발생했습니다.", e);
		}
		 
    }
	
	
	
	
	

}
