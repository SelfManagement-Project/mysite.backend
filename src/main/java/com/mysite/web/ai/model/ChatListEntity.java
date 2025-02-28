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
public class ChatListEntity {
	private Long chatId;        // chat 테이블의 PK
    private Long userId;        // 사용자 ID (FK)
    private String sessionId;   // 세션 ID
    private String message;     // 사용자가 입력한 메시지
    private String response;    // 챗봇 응답
    private String createdAt;   // 생성일시
    private Boolean isCompleted; // 완료 여부
}
