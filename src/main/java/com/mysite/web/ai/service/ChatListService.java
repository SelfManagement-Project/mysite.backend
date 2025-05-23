package com.mysite.web.ai.service;

import java.util.List;

import com.mysite.web.ai.dto.ChatHistoryResponseDTO;
import com.mysite.web.ai.dto.ChatListResponse;


public interface ChatListService {
	List<ChatListResponse> getChatList(String token, String search);
	List<ChatListResponse> getChatListRecent(String token);
	List<ChatHistoryResponseDTO> getChatHistory(String token, Long chatId);
}
