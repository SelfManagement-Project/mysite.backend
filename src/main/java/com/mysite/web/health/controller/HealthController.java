package com.mysite.web.health.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.common.util.JsonResult;
import com.mysite.web.health.dto.DietRequestDTO;
import com.mysite.web.health.dto.ExerciseRequestDTO;
import com.mysite.web.health.dto.HealthMetricsRequestDTO;
import com.mysite.web.health.dto.SleepRequestDTO;
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
            @RequestHeader("Authorization") String token) {
    	
    	List<ExerciseRequestDTO> result = healthService.getExercise(token);
//    	System.out.println("exercise test입니다" + result);
    	
        return ResponseEntity.ok(JsonResult.success(result));
//        return ResponseEntity.ok(JsonResult.success("test"));
    }
    
    @GetMapping("/diet")
    public ResponseEntity<JsonResult> getDiet(
            @RequestHeader("Authorization") String token) {
    	
    	List<DietRequestDTO> result = healthService.getDiet(token);
//    	System.out.println("diet test입니다");
    	
        return ResponseEntity.ok(JsonResult.success(result));
//        return ResponseEntity.ok(JsonResult.success("test"));
    }
    
    @GetMapping("/sleep")
    public ResponseEntity<JsonResult> getSleep(
            @RequestHeader("Authorization") String token) {
    	
    	List<SleepRequestDTO> result = healthService.getSleep(token);
//    	System.out.println("sleep test입니다");
    	
        return ResponseEntity.ok(JsonResult.success(result));
//        return ResponseEntity.ok(JsonResult.success("test"));
    }
    
    @GetMapping("/metrics")
    public ResponseEntity<JsonResult> getMetrics(
            @RequestHeader("Authorization") String token) {
    	
    	List<HealthMetricsRequestDTO> result = healthService.getMetrics(token);
//    	System.out.println("metrics test입니다"+ result);
    	
        return ResponseEntity.ok(JsonResult.success(result));
//        return ResponseEntity.ok(JsonResult.success("test"));
    }
	
	
}
