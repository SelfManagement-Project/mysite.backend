package com.mysite.web.schedule.controller;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.common.util.JsonResult;
import com.mysite.web.schedule.dto.CalendarRequestDTO;
import com.mysite.web.schedule.dto.CalendarResponseDTO;
import com.mysite.web.schedule.dto.HabitGoalRequestDTO;
import com.mysite.web.schedule.dto.HabitProgressDTO;
import com.mysite.web.schedule.dto.HabitRequestDTO;
import com.mysite.web.schedule.dto.HabitStatusDTO;
import com.mysite.web.schedule.dto.MonthlyReportResponseDTO;
import com.mysite.web.schedule.dto.TaskRequestDTO;
import com.mysite.web.schedule.dto.TaskResponseDTO;
import com.mysite.web.schedule.dto.WeeklyProgressResponseDTO;
import com.mysite.web.schedule.dto.WeeklyReportResponseDTO;
import com.mysite.web.schedule.service.ScheduleService;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	// 일정 조회
	@GetMapping("/calendar/list")
	public ResponseEntity<JsonResult> getCalendar(@RequestHeader("Authorization") String token) {
		List<CalendarResponseDTO> events = scheduleService.getScheduleByToken(token);
		System.out.println(events);

		return ResponseEntity.ok(JsonResult.success(events));
	}

	// 일정 추가
	@PostMapping("/calendar/write")
	public ResponseEntity<JsonResult> writeCalendar(@RequestHeader("Authorization") String token,
			@RequestBody CalendarRequestDTO newEvent) {
//		System.out.println(newEvent);
		int count = scheduleService.writeScheduleByToken(token, newEvent);
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 일정 수정
	@PutMapping("/calendar/modify")
	public ResponseEntity<JsonResult> modifyCalendar(@RequestHeader("Authorization") String token,
			@RequestBody CalendarRequestDTO updatedEvent) {
		int count = scheduleService.modifyScheduleByToken(token, updatedEvent);
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 일정 삭제
	@DeleteMapping("/calendar/delete/{scheduleId}")
	public ResponseEntity<JsonResult> deleteCalendar(@RequestHeader("Authorization") String token,
			@PathVariable("scheduleId") Long Id) {

		int count = scheduleService.deleteScheduleByToken(token, Id);
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 오늘 할일 조회
	@GetMapping("/todos")
	public ResponseEntity<JsonResult> getTodos(@RequestHeader("Authorization") String token) {
		List<CalendarResponseDTO> events = scheduleService.getTodosByToken(token);
//		System.out.println("events:::" + events);
		return ResponseEntity.ok(JsonResult.success(events));
	}

	// 오늘 할일 수정
	@PutMapping("/todos/{todoId}")
	public ResponseEntity<JsonResult> modifyTodos(@RequestHeader("Authorization") String token,
			@RequestBody TaskRequestDTO taskRequestDTO, @PathVariable("todoId") Long scheduleId) {
		System.out.println("test::::" + taskRequestDTO.getIsCompleted());
		int count = scheduleService.modifyTodosByToken(token, scheduleId, taskRequestDTO);
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 다가오는 일정 조회
	@GetMapping("/upcoming")
	public ResponseEntity<JsonResult> getUpcoming(@RequestHeader("Authorization") String token) {
//		System.out.println("token::::::" + token);
		List<CalendarResponseDTO> events = scheduleService.getUpcomingByToken(token);
//		System.out.println(events);

		return ResponseEntity.ok(JsonResult.success(events));
	}

	// 주간 할 일 완료율 조회
	@GetMapping("/weekly-progress")
	public ResponseEntity<JsonResult> getWeeklyProgress(@RequestHeader("Authorization") String token) {
		WeeklyProgressResponseDTO response = scheduleService.getWeeklyProgress(token);

//        System.out.println(response);
		return ResponseEntity.ok(JsonResult.success(response));
	}

	// 습관 차트 데이터 조회
	@GetMapping("/habits")
	public ResponseEntity<JsonResult> getUserHabits(@RequestHeader("Authorization") String token) {
		List<HabitProgressDTO> habits = scheduleService.getHabitsByUser(token);
		return ResponseEntity.ok().body(JsonResult.success(habits));
	}

	// 새 습관 추가
	@PostMapping("/habits")
	public ResponseEntity<JsonResult> addHabit(@RequestHeader("Authorization") String token,
			@RequestBody HabitRequestDTO habitRequest) {
		Long habitId = scheduleService.addHabit(token, habitRequest);
		return ResponseEntity.ok(JsonResult.success(habitId));
	}

	// 기간별 습관 데이터 조회
	@GetMapping("/habits/range")
	public ResponseEntity<JsonResult> getHabitsByDateRange(@RequestHeader("Authorization") String token,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<HabitProgressDTO> habits = scheduleService.getHabitsByDateRange(token, startDate, endDate);
		return ResponseEntity.ok(JsonResult.success(habits));
	}

	// 습관 목표 설정
	@PutMapping("/habits/{habitId}/goal")
	public ResponseEntity<JsonResult> updateHabitGoal(@RequestHeader("Authorization") String token,
			@PathVariable("habitId") Long habitId, @RequestBody HabitGoalRequestDTO goalRequest) {
		int count = scheduleService.updateHabitGoal(token, habitId, goalRequest.getGoalCount());
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 주간 리포트 조회
	@GetMapping("/habits/report/weekly")
	public ResponseEntity<JsonResult> getWeeklyHabitReport(@RequestHeader("Authorization") String token) {
		WeeklyReportResponseDTO report = scheduleService.getWeeklyHabitReport(token);
		return ResponseEntity.ok(JsonResult.success(report));
	}

	// 월간 리포트 조회
	@GetMapping("/habits/report/monthly")
	public ResponseEntity<JsonResult> getMonthlyHabitReport(@RequestHeader("Authorization") String token) {
		MonthlyReportResponseDTO report = scheduleService.getMonthlyHabitReport(token);
		return ResponseEntity.ok(JsonResult.success(report));
	}

	// ScheduleController.java에 추가
	@PostMapping("/habits/{habitId}/checkin")
	public ResponseEntity<JsonResult> checkInHabit(@RequestHeader("Authorization") String token,
			@PathVariable("habitId") Long habitId,
			@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

		// 날짜 파라미터가 없으면 오늘 날짜 사용
		if (date == null) {
			date = LocalDate.now();
		}

		boolean result = scheduleService.checkInHabit(token, habitId, date);
		return ResponseEntity.ok(JsonResult.success(result));
	}

	// 이미 완료된 습관 취소(체크 해제)
	@DeleteMapping("/habits/{habitId}/checkin")
	public ResponseEntity<JsonResult> undoHabitCheckin(@RequestHeader("Authorization") String token,
			@PathVariable("habitId") Long habitId,
			@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

		// 날짜 파라미터가 없으면 오늘 날짜 사용
		if (date == null) {
			date = LocalDate.now();
		}

		boolean result = scheduleService.undoHabitCheckin(token, habitId, date);
		return ResponseEntity.ok(JsonResult.success(result));
	}

	// 오늘의 습관 상태 조회
	@GetMapping("/habits/today")
	public ResponseEntity<JsonResult> getTodayHabits(@RequestHeader("Authorization") String token) {
		List<HabitStatusDTO> habits = scheduleService.getTodayHabits(token);
		System.out.println("habits:::::::" + habits);
		return ResponseEntity.ok(JsonResult.success(habits));
	}

}