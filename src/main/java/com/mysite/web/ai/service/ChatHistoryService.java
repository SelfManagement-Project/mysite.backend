package com.mysite.web.ai.service;

import java.util.List;

import com.mysite.web.ai.dto.ChatHistoryResponse;


public interface ChatHistoryService {
	List<ChatHistoryResponse> getChatHistories(String token, String search);
}
