package com.oldman.yaopin.controller;

import com.oldman.common.ApiResponse;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.common.auth.DangqianYonghuResolver;
import com.oldman.yaopin.biz.YaopinBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/yaopin")
public class YaopinController {

    private final YaopinBiz yaopinBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public YaopinController(YaopinBiz yaopinBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.yaopinBiz = yaopinBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<Map<String, Object>>> list(@RequestParam(required = false) String yaopinMingcheng,
                                                       HttpServletRequest request) {
        return ApiResponse.ok(yaopinBiz.list(current(request), yaopinMingcheng));
    }

    @PostMapping("/add")
    public ApiResponse<Void> add(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        yaopinBiz.add(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        yaopinBiz.delete(current(request), id);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
