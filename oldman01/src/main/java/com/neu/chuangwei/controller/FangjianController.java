package com.neu.chuangwei.controller;

import com.neu.chuangwei.biz.ChuangweiBiz;
import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fangjian")
public class FangjianController {

    private final ChuangweiBiz chuangweiBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public FangjianController(ChuangweiBiz chuangweiBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.chuangweiBiz = chuangweiBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<Map<String, Object>>> list(HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(chuangweiBiz.fangjianList());
    }

    @GetMapping("/options")
    public ApiResponse<List<Map<String, Object>>> options(HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(chuangweiBiz.fangjianOptions());
    }

    @PostMapping("/add")
    public ApiResponse<Void> add(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.fangjianAdd(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.fangjianUpdate(data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.fangjianDelete(id);
        return ApiResponse.ok(null);
    }

    private void requireAdmin(HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = dangqianYonghuResolver.resolve(request);
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以维护房间信息");
        }
    }
}
