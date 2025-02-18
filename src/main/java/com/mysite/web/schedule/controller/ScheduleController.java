package com.mysite.web.schedule.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.common.util.JsonResult;
import com.mysite.web.schedule.dto.ScheduleRequestDTO;
import com.mysite.web.schedule.dto.ScheduleResponseDTO;
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
		List<ScheduleResponseDTO> events = scheduleService.getScheduleByToken(token);
		return ResponseEntity.ok(JsonResult.success(events));
	}

	// 일정 추가
	@PostMapping("/calendar/write")
	public ResponseEntity<JsonResult> writeCalendar(@RequestHeader("Authorization") String token, @RequestBody ScheduleRequestDTO newEvent) {
		System.out.println("token::::::" + token);
		System.out.println("newEvent::::::" + newEvent);
		int count = scheduleService.writeScheduleByToken(token, newEvent);
		return ResponseEntity.ok(JsonResult.success(count));
	}
}