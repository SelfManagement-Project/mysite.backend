package com.mysite.web.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.web.common.service.SearchService;

@RestController
@RequestMapping("/api/common")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> search(@RequestParam(value= "keyword") String keyword) {
        List<Map<String, Object>> results = searchService.searchAllTables(keyword);
        System.out.println(results);
        return ResponseEntity.ok(results);
    }
}
