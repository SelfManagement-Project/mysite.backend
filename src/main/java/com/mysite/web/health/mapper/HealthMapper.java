package com.mysite.web.health.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.web.health.dto.DietRequestDTO;
import com.mysite.web.health.dto.DietResponseDTO;
import com.mysite.web.health.dto.ExerciseRequestDTO;
import com.mysite.web.health.dto.ExerciseResponseDTO;
import com.mysite.web.health.dto.HealthMetricsRequestDTO;
import com.mysite.web.health.dto.HealthMetricsResponseDTO;
import com.mysite.web.health.dto.SleepRequestDTO;
import com.mysite.web.health.dto.SleepResponseDTO;
import com.mysite.web.health.model.DietEntity;
import com.mysite.web.health.model.ExerciseEntity;
import com.mysite.web.health.model.HealthMetricsEntity;
import com.mysite.web.health.model.SleepEntity;


@Mapper
public interface HealthMapper {
	List<ExerciseResponseDTO> getExercise(ExerciseEntity exerciseEntity);
	List<DietResponseDTO> getDiet(DietEntity dietEntity);
	List<SleepResponseDTO> getSleep(SleepEntity sleepEntity);
	List<HealthMetricsResponseDTO> getMetrics(HealthMetricsEntity healthMetricsEntity);
}
