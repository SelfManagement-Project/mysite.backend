package com.mysite.web.schedule.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonthlyReportResponseDTO {
    private List<HabitWeeklyRateDTO> habitWeeklyRates;
    private Integer previousRate;
}