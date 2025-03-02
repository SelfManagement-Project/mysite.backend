package com.mysite.web.health.service;

import java.util.List;

import com.mysite.web.health.dto.DietRequestDTO;
import com.mysite.web.health.dto.ExerciseRequestDTO;
import com.mysite.web.health.dto.HealthMetricsRequestDTO;
import com.mysite.web.health.dto.SleepRequestDTO;


public interface HealthService {
	List<ExerciseRequestDTO> getExercise(String token);
	List<DietRequestDTO> getDiet(String token);
	List<SleepRequestDTO> getSleep(String token);
	List<HealthMetricsRequestDTO> getMetrics(String token);
}
