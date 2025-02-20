package com.mysite.web.schedule.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mysite.web.schedule.dto.CalendarRequestDTO;
import com.mysite.web.schedule.dto.CalendarResponseDTO;
import com.mysite.web.schedule.dto.TaskResponseDTO;
import com.mysite.web.schedule.model.CalendarEntity;
import com.mysite.web.schedule.model.TaskEntity;


@Mapper
public interface ScheduleMapper {
	List<CalendarResponseDTO> getScheduleByToken(String userEmail);
	int writeScheduleByToken(CalendarEntity write);
	int modifyScheduleByToken(CalendarEntity modify);
	int deleteScheduleByToken(CalendarEntity scheduleEntity);
	List<TaskResponseDTO> getTodosByToken(Long userId);
	int modifyTodosByToken(TaskEntity taskEntity);
}
