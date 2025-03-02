package com.mysite.web.health.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.web.health.dto.DietRequestDTO;
import com.mysite.web.health.dto.ExerciseRequestDTO;
import com.mysite.web.health.dto.HealthMetricsRequestDTO;
import com.mysite.web.health.dto.SleepRequestDTO;


@Mapper
public interface HealthMapper {
	List<ExerciseRequestDTO> getExercise(String userEmail);
	List<DietRequestDTO> getDiet(String userEmail);
	List<SleepRequestDTO> getSleep(String userEmail);
	List<HealthMetricsRequestDTO> getMetrics(String userEmail);
}
