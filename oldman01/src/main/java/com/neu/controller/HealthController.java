package com.neu.controller;

import com.neu.common.ApiResponse;
import com.neu.health.mapper.HealthMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HealthController {

    private final HealthMapper healthMapper;

    public HealthController(HealthMapper healthMapper) {
        this.healthMapper = healthMapper;
    }

    @GetMapping({"/hello", "/api/hello"})
    public ApiResponse<Map<String, Object>> hello() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("project", "oldman01");
        data.put("packageName", "com.neu");
        data.put("time", LocalDateTime.now().toString());
        return ApiResponse.ok(data);
    }

    @GetMapping({"/db/ping", "/api/db/ping"})
    public ApiResponse<Map<String, Object>> pingDatabase() {
        Integer result = healthMapper.ping();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("database", "oldman");
        data.put("result", result);
        return ApiResponse.ok(data);
    }
}



