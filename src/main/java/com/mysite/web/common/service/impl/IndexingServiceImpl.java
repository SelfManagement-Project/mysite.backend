package com.mysite.web.common.service.impl;

import com.mysite.web.common.service.IndexingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IndexingServiceImpl implements IndexingService {
    
    @Value("${python.server.url}")
    private String pythonServerUrl;
    
    private final RestTemplate restTemplate;
    
    public IndexingServiceImpl() {
        this.restTemplate = new RestTemplate();
    }
    
    @Override
    public boolean indexTable(String tableName) {
        String url = pythonServerUrl + "/index/table/" + tableName;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("테이블 인덱싱 성공: {}", tableName);
                return true;
            } else {
                log.error("테이블 인덱싱 실패: {} - {}", tableName, response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            log.error("테이블 인덱싱 중 오류 발생: {} - {}", tableName, e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean indexRecord(String tableName, Long recordId) {
        String url = pythonServerUrl + "/index/record/" + tableName + "/" + recordId;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("레코드 인덱싱 성공: {} - ID: {}", tableName, recordId);
                return true;
            } else {
                log.error("레코드 인덱싱 실패: {} - ID: {} - {}", tableName, recordId, response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            log.error("레코드 인덱싱 중 오류 발생: {} - ID: {} - {}", tableName, recordId, e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean deleteFromIndex(String tableName, Long recordId) {
        String url = pythonServerUrl + "/index/delete/" + tableName + "/" + recordId;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("인덱스 삭제 성공: {} - ID: {}", tableName, recordId);
                return true;
            } else {
                log.error("인덱스 삭제 실패: {} - ID: {} - {}", tableName, recordId, response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            log.error("인덱스 삭제 중 오류 발생: {} - ID: {} - {}", tableName, recordId, e.getMessage());
            return false;
        }
    }
}