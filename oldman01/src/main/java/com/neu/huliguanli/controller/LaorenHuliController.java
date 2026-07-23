package com.neu.huliguanli.controller;

import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import com.neu.huliguanli.biz.LaorenHuliBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/laoren-huli")
public class LaorenHuliController {

    private final LaorenHuliBiz laorenHuliBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public LaorenHuliController(LaorenHuliBiz laorenHuliBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.laorenHuliBiz = laorenHuliBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/laoren-list")
    public ApiResponse<List<Map<String, Object>>> laorenList(HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(laorenHuliBiz.laorenList());
    }

    @GetMapping("/{laorenId}")
    public ApiResponse<Map<String, Object>> current(@PathVariable Long laorenId, HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(laorenHuliBiz.current(laorenId));
    }

    @PostMapping("/set-jibie")
    public ApiResponse<Void> setJibie(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        laorenHuliBiz.setJibie(data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/remove/{laorenId}")
    public ApiResponse<Void> remove(@PathVariable Long laorenId, HttpServletRequest request) {
        requireAdmin(request);
        laorenHuliBiz.remove(laorenId);
        return ApiResponse.ok(null);
    }

    private void requireAdmin(HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = dangqianYonghuResolver.resolve(request);
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以设置老人护理级别");
        }
    }
}



