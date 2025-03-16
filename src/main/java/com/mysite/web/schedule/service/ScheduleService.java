package com.mysite.web.schedule.service;

import java.time.LocalDate;
import java.util.List;

import com.mysite.web.schedule.dto.CalendarRequestDTO;
import com.mysite.web.schedule.dto.CalendarResponseDTO;
import com.mysite.web.schedule.dto.HabitProgressDTO;
import com.mysite.web.schedule.dto.HabitRequestDTO;
import com.mysite.web.schedule.dto.HabitStatusDTO;
import com.mysite.web.schedule.dto.MonthlyReportResponseDTO;
import com.mysite.web.schedule.dto.TaskRequestDTO;
import com.mysite.web.schedule.dto.TaskResponseDTO;
import com.mysite.web.schedule.dto.WeeklyProgressResponseDTO;
import com.mysite.web.schedule.dto.WeeklyReportResponseDTO;

public interface ScheduleService {
	List<CalendarResponseDTO> getScheduleByToken(String token);
	int writeScheduleByToken(String token, CalendarRequestDTO newEvent);
	int modifyScheduleByToken(String token, CalendarRequestDTO updatedEvent);
	int deleteScheduleByToken(String token, Long scheduleId);
	List<CalendarResponseDTO> getTodosByToken(String token);
	int modifyTodosByToken(String token, Long scheduleId, TaskRequestDTO taskRequestDTO);
	List<CalendarResponseDTO> getUpcomingByToken(String token);
	WeeklyProgressResponseDTO getWeeklyProgress(String token);
	List<HabitProgressDTO> getHabitsByUser(String token);
	
	// 새로 추가할 메서드
    Long addHabit(String token, HabitRequestDTO habitRequest);
    List<HabitProgressDTO> getHabitsByDateRange(String token, LocalDate startDate, LocalDate endDate);
    int updateHabitGoal(String token, Long habitId, Integer goalCount);
    WeeklyReportResponseDTO getWeeklyHabitReport(String token);
    MonthlyReportResponseDTO getMonthlyHabitReport(String token);
    
    boolean checkInHabit(String token, Long habitId, LocalDate date);
    boolean undoHabitCheckin(String token, Long habitId, LocalDate date);
    List<HabitStatusDTO> getTodayHabits(String token);
}
