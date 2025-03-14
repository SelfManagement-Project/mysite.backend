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

	// 대화목록 Tab의 chat 리스트 조회
	@GetMapping("/chat/list/total")
	public ResponseEntity<JsonResult> getChatList(@RequestHeader("Authorization") String token,
			@RequestParam(value = "search", required = false) String search) {

//		System.out.println(search);

		List<ChatListResponse> chat_list = chatListService.getChatList(token, search);
		return ResponseEntity.ok(JsonResult.success(chat_list));
	}

	// 최근 대화 챗 리스트
	@GetMapping("/chat/list/recent")
	public ResponseEntity<JsonResult> getChatListRecent(@RequestHeader("Authorization") String token) {

//			System.out.println(search);

		List<ChatListResponse> chat_list_recent = chatListService.getChatListRecent(token);
		return ResponseEntity.ok(JsonResult.success(chat_list_recent));
	}
}
