package com.mysite.web.health.service;

import java.util.List;

import com.mysite.web.health.dto.DietRequestDTO;
import com.mysite.web.health.dto.DietResponseDTO;
import com.mysite.web.health.dto.ExerciseRequestDTO;
import com.mysite.web.health.dto.ExerciseResponseDTO;
import com.mysite.web.health.dto.HealthMetricsRequestDTO;
import com.mysite.web.health.dto.HealthMetricsResponseDTO;
import com.mysite.web.health.dto.SleepRequestDTO;
import com.mysite.web.health.dto.SleepResponseDTO;


public interface HealthService {
	List<ExerciseResponseDTO> getExercise(String token, String date);
	List<DietResponseDTO> getDiet(String token, String date);
	List<SleepResponseDTO> getSleep(String token, String date);
	List<HealthMetricsResponseDTO> getMetrics(String token, String date);
}
