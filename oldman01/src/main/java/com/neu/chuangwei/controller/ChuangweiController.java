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
@RequestMapping("/chuangwei")
public class ChuangweiController {

    private final ChuangweiBiz chuangweiBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public ChuangweiController(ChuangweiBiz chuangweiBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.chuangweiBiz = chuangweiBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<Map<String, Object>>> list(HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(chuangweiBiz.list());
    }

    @GetMapping("/base-list")
    public ApiResponse<List<Map<String, Object>>> baseList(HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(chuangweiBiz.chuangweiBaseList());
    }

    @GetMapping("/kongxian-options")
    public ApiResponse<List<Map<String, Object>>> kongxianOptions(@RequestParam(required = false) Long fangjianId,
                                                                  HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(chuangweiBiz.kongxianOptions(fangjianId));
    }

    @PostMapping("/add")
    public ApiResponse<Void> add(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.chuangweiAdd(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.chuangweiUpdate(data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.chuangweiDelete(id);
        return ApiResponse.ok(null);
    }

    @PostMapping("/fenpei")
    public ApiResponse<Void> fenpei(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.fenpei(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/tiaohuan")
    public ApiResponse<Void> tiaohuan(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.tiaohuan(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/shifang/{laorenId}")
    public ApiResponse<Void> shifang(@PathVariable Long laorenId, HttpServletRequest request) {
        requireAdmin(request);
        chuangweiBiz.shifang(laorenId);
        return ApiResponse.ok(null);
    }

    private void requireAdmin(HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = dangqianYonghuResolver.resolve(request);
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以操作床位信息");
        }
    }
}



