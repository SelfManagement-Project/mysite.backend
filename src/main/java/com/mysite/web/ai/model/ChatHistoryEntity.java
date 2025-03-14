package com.mysite.web.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistoryEntity {
    private Long historyId;
    private Long chatId;
    private String sessionId;
    private String messageType;
    private String content;
    private String createdAt;
}
