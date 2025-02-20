package com.mysite.web.schedule.service;

import java.util.List;

import com.mysite.web.schedule.dto.CalendarRequestDTO;
import com.mysite.web.schedule.dto.CalendarResponseDTO;
import com.mysite.web.schedule.dto.TaskRequestDTO;
import com.mysite.web.schedule.dto.TaskResponseDTO;
import com.mysite.web.schedule.dto.WeeklyProgressResponseDTO;

public interface ScheduleService {
	List<CalendarResponseDTO> getScheduleByToken(String token);
	int writeScheduleByToken(String token, CalendarRequestDTO newEvent);
	int modifyScheduleByToken(String token, CalendarRequestDTO updatedEvent);
	int deleteScheduleByToken(String token, Long scheduleId);
	List<TaskResponseDTO> getTodosByToken(String token);
	int modifyTodosByToken(String token, Long taskId, TaskRequestDTO taskRequestDTO);
	List<CalendarResponseDTO> getUpcomingByToken(String token);
	WeeklyProgressResponseDTO getWeeklyProgress(String token);
}
