package com.mysite.web.health.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.common.util.JsonResult;
import com.mysite.web.health.dto.DietResponseDTO;
import com.mysite.web.health.dto.ExerciseResponseDTO;
import com.mysite.web.health.dto.HealthMetricsResponseDTO;
import com.mysite.web.health.dto.SleepResponseDTO;
import com.mysite.web.health.service.HealthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {
	
	@Autowired
    private HealthService healthService;
    
    @GetMapping("/exercise")
    public ResponseEntity<JsonResult> getExercise(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date) {
    	
    	List<ExerciseResponseDTO> result = healthService.getExercise(token, date);
        return ResponseEntity.ok(JsonResult.success(result));
    }
    
    @GetMapping("/diet")
    public ResponseEntity<JsonResult> getDiet(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date) {
    	System.out.println("date:::::::::::" + date);
    	List<DietResponseDTO> result = healthService.getDiet(token, date);
    	
        return ResponseEntity.ok(JsonResult.success(result));
    }
    
    @GetMapping("/sleep")
    public ResponseEntity<JsonResult> getSleep(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date) {
    	
    	List<SleepResponseDTO> result = healthService.getSleep(token, date);
    	
        return ResponseEntity.ok(JsonResult.success(result));
    }
    
    @GetMapping("/metrics")
    public ResponseEntity<JsonResult> getMetrics(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date) {
    	
    	List<HealthMetricsResponseDTO> result = healthService.getMetrics(token, date);
    	
        return ResponseEntity.ok(JsonResult.success(result));
    }
}