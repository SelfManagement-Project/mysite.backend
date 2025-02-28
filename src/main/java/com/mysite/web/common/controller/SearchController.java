package com.mysite.web.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.common.service.SearchService;
import com.mysite.web.common.util.JsonResult;

@RestController
@RequestMapping("/api/common")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value= "keyword") String keyword, @RequestHeader("Authorization") String token) {
//    	System.out.println("token:::::" + token);
    	if (token == null || token.isEmpty()) {
            return ResponseEntity.ok(JsonResult.fail("로그인해주세요.")); // 401 Unauthorized 응답
        }
        List<Map<String, Object>> results = searchService.searchAllTables(token, keyword);
        System.out.println(results);
        return ResponseEntity.ok(results);
    }
}
