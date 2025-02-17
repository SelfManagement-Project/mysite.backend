package com.mysite.web.schedule.model;

import com.mysite.web.login.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduleEntity {
	private String scheduleId;
    private UserEntity user;
    private String scheduleTitle;
    private String scheduleStart;
    private String scheduleEnd;
    private boolean allDay;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;
}
