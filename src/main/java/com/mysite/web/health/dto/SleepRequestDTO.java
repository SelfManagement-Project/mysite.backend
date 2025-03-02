package com.mysite.web.health.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SleepRequestDTO {
    private Long userId;
    private String sleepStart;
    private String sleepEnd;
    private int sleepQuality;
}
