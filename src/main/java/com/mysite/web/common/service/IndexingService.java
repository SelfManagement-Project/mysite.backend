package com.mysite.web.common.service;

public interface IndexingService {
    boolean indexTable(String tableName);
    boolean indexRecord(String tableName, Long recordId);
    boolean deleteFromIndex(String tableName, Long recordId);
}