package com.mysite.web.schedule.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mysite.web.schedule.dto.*;
import com.mysite.web.schedule.model.*;

@Mapper
public interface ScheduleMapper {
    // 기존 메서드 유지
    List<CalendarResponseDTO> getScheduleByToken(String userEmail);
    int writeScheduleByToken(CalendarEntity write);
    int modifyScheduleByToken(CalendarEntity modify);
    int deleteScheduleByToken(CalendarEntity scheduleEntity);
    List<CalendarResponseDTO> getTodosByToken(Long userId);
    int modifyTodosByToken(TaskEntity taskEntity);
    List<CalendarResponseDTO> getUpcomingByToken(Long userId);
    int getTotalWeeklyTasks(Long userId);
    int getCompletedWeeklyTasks(Long userId);
    List<HabitProgressDTO> getHabitsWithProgress(Long userId);
    
    // 새로 추가할 메서드
    Long addHabit(HabitEntity habit);
    List<HabitProgressDTO> getHabitsWithProgressByDateRange(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    int updateHabitGoal(@Param("userId") Long userId, @Param("habitId") Long habitId, @Param("goalCount") Integer goalCount);
    List<DailyProgressDTO> getDailyHabitProgress(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    Integer getWeeklyCompletionRate(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    Integer getMonthlyCompletionRate(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    List<HabitWeeklyRateDTO> getHabitWeeklyRates(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // ScheduleMapper 인터페이스에 추가
    HabitLogEntity getHabitLogByDate(@Param("userId") Long userId, @Param("habitId") Long habitId, @Param("logDate") LocalDate date);
    int insertHabitLog(HabitLogEntity log);
    int updateHabitLog(HabitLogEntity log);
    int deleteHabitLog(@Param("userId") Long userId, @Param("habitId") Long habitId, @Param("logDate") LocalDate date);
    List<HabitStatusDTO> getTodayHabitStatus(@Param("userId") Long userId, @Param("today") LocalDate today);
}