package com.oldman.laoren.controller;

import com.oldman.common.ApiResponse;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.common.auth.DangqianYonghuResolver;
import com.oldman.laoren.entity.LaorenXinxi;
import com.oldman.laoren.biz.LaorenXinxiBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/laoren-xinxi")
public class LaorenXinxiController {

    private final LaorenXinxiBiz laorenXinxiBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public LaorenXinxiController(LaorenXinxiBiz laorenXinxiBiz,
                                 DangqianYonghuResolver dangqianYonghuResolver) {
        this.laorenXinxiBiz = laorenXinxiBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<LaorenXinxi>> list(HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = dangqianYonghuResolver.resolve(request);
        return ApiResponse.ok(laorenXinxiBiz.list(dangqianYonghu));
    }

    @GetMapping("/options")
    public ApiResponse<List<Map<String, Object>>> options(HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(laorenXinxiBiz.options());
    }

    @PostMapping("/add")
    public ApiResponse<Void> add(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        laorenXinxiBiz.add(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        laorenXinxiBiz.update(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        laorenXinxiBiz.delete(id);
        return ApiResponse.ok(null);
    }

    @PostMapping("/ruzhu")
    public ApiResponse<Void> ruzhu(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = requireAdmin(request);
        laorenXinxiBiz.ruzhu(data, dangqianYonghu);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu requireAdmin(HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = dangqianYonghuResolver.resolve(request);
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以操作老人信息");
        }
        return dangqianYonghu;
    }
}



