package com.mysite.web.health.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SleepResponseDTO {
    private Long sleepId;
    private Long userId;
    private String sleepStart;
    private String sleepEnd;
    private int sleepQuality;
    private String createdAt;
    private String updatedAt;
}
