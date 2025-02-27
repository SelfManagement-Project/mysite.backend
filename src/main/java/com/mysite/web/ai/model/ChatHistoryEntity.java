package com.mysite.web.ai.model;

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
public class ChatHistoryEntity {
    private Long historyId;  // chat_history 테이블 PK
    private Long chatId;     // chat 테이블의 FK
    private String sessionId;
    private String messageType;
    private String content;
    private String createdAt;
}
