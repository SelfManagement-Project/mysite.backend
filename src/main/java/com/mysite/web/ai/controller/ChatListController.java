package com.mysite.web.ai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.ai.dto.ChatListResponse;
import com.mysite.web.ai.service.ChatListService;
import com.mysite.web.common.util.JsonResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class ChatListController {

	@Autowired
	private ChatListService chatListService;

	// 일정 조회
	@GetMapping("/chat_history/list")
	public ResponseEntity<JsonResult> getChatHistories(@RequestHeader("Authorization") String token,
			@RequestParam(value = "search", required = false) String search) {
		
//		System.out.println(search);
		
		List<ChatListResponse> chat_list = chatListService.getChatList(token, search);
		return ResponseEntity.ok(JsonResult.success(chat_list));
	}

}
