package com.mysite.web.schedule.service;

import java.util.List;

import com.mysite.web.schedule.dto.ScheduleRequestDTO;
import com.mysite.web.schedule.dto.ScheduleResponseDTO;

public interface ScheduleService {
	List<ScheduleResponseDTO> getScheduleByToken(String token);
	int writeScheduleByToken(String token, ScheduleRequestDTO newEvent);
}
