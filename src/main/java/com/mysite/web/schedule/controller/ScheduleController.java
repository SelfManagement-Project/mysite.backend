package com.mysite.web.schedule.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.common.util.JsonResult;
import com.mysite.web.schedule.dto.CalendarRequestDTO;
import com.mysite.web.schedule.dto.CalendarResponseDTO;
import com.mysite.web.schedule.dto.TaskRequestDTO;
import com.mysite.web.schedule.dto.TaskResponseDTO;
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
//		System.out.println("token::::::" + token);
		List<CalendarResponseDTO> events = scheduleService.getScheduleByToken(token);
		return ResponseEntity.ok(JsonResult.success(events));
	}

	// 일정 추가
	@PostMapping("/calendar/write")
	public ResponseEntity<JsonResult> writeCalendar(@RequestHeader("Authorization") String token,
			@RequestBody CalendarRequestDTO newEvent) {
		System.out.println("token::::::" + token);
		System.out.println("newEvent::::::" + newEvent);
		int count = scheduleService.writeScheduleByToken(token, newEvent);
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 일정 수정
	@PutMapping("/calendar/modify")
	public ResponseEntity<JsonResult> modifyCalendar(@RequestHeader("Authorization") String token,
			@RequestBody CalendarRequestDTO updatedEvent) {
		System.out.println("token::::::" + token);
		System.out.println("newEvent::::::" + updatedEvent);
		int count = scheduleService.modifyScheduleByToken(token, updatedEvent);
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 일정 삭제
	@DeleteMapping("/calendar/delete/{scheduleId}")
	public ResponseEntity<JsonResult> deleteCalendar(@RequestHeader("Authorization") String token,
			@PathVariable("scheduleId") Long Id) {

		System.out.println("scheduleId:::" + Id);
		System.out.println("token::::::" + token);
		System.out.println("scheduleId::::::" + Id);

		int count = scheduleService.deleteScheduleByToken(token, Id);
		return ResponseEntity.ok(JsonResult.success(count));
	}

	// 오늘 할일 조회
	@GetMapping("/todos")
	public ResponseEntity<JsonResult> getTodos(@RequestHeader("Authorization") String token) {
		System.out.println("token::::::" + token);
		List<TaskResponseDTO> events = scheduleService.getTodosByToken(token);
		return ResponseEntity.ok(JsonResult.success(events));
//		return ResponseEntity.ok(JsonResult.success("test"));
	}

	// 오늘 할일 수정
	@PutMapping("/todos/{todoId}")
	public ResponseEntity<JsonResult> modifyTodos(@RequestHeader("Authorization") String token,
			@RequestBody TaskRequestDTO taskRequestDTO,
			@PathVariable("todoId") Long taskId) {
		System.out.println("token::::::" + token);
		System.out.println("Id::::::" + taskId);
		System.out.println("taskRequestDTO::::::" + taskRequestDTO);
		int count = scheduleService.modifyTodosByToken(token, taskId, taskRequestDTO);
		return ResponseEntity.ok(JsonResult.success(count));
//		return ResponseEntity.ok(JsonResult.success("test"));
	}

	// 다가오는 일정 조회
	@GetMapping("/upcoming")
	public ResponseEntity<JsonResult> getUpcoming(@RequestHeader("Authorization") String token) {
//		System.out.println("token::::::" + token);
//		List<ScheduleResponseDTO> events = scheduleService.getScheduleByToken(token);
//		return ResponseEntity.ok(JsonResult.success(events));
		return ResponseEntity.ok(JsonResult.success("test"));
	}

	// 다가오는 일정 조회
	@GetMapping("/weekly-progress")
	public ResponseEntity<JsonResult> getWeeklyProgress(@RequestHeader("Authorization") String token) {
//		System.out.println("token::::::" + token);
//		List<ScheduleResponseDTO> events = scheduleService.getScheduleByToken(token);
//		return ResponseEntity.ok(JsonResult.success(events));
		return ResponseEntity.ok(JsonResult.success("test"));
	}

}