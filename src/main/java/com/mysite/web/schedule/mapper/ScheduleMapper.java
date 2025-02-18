package com.mysite.web.schedule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.web.schedule.dto.ScheduleRequestDTO;
import com.mysite.web.schedule.dto.ScheduleResponseDTO;
import com.mysite.web.schedule.model.ScheduleEntity;


@Mapper
public interface ScheduleMapper {
	List<ScheduleResponseDTO> getScheduleByToken(String userEmail);
	int writeScheduleByToken(ScheduleEntity dd);
}
