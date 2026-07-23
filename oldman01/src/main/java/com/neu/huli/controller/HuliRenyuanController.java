package com.neu.huli.controller;

import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import com.neu.huli.biz.HuliRenyuanBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/huli-renyuan")
public class HuliRenyuanController {

    private final HuliRenyuanBiz huliRenyuanBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public HuliRenyuanController(HuliRenyuanBiz huliRenyuanBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.huliRenyuanBiz = huliRenyuanBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<Map<String, Object>>> list(HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(huliRenyuanBiz.list());
    }

    @PostMapping("/add")
    public ApiResponse<Void> add(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliRenyuanBiz.add(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliRenyuanBiz.update(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        huliRenyuanBiz.delete(id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/{id}/laoren-list")
    public ApiResponse<List<Map<String, Object>>> fuwuLaoren(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(huliRenyuanBiz.fuwuLaoren(id));
    }

    @GetMapping("/{id}/unassigned-laoren-options")
    public ApiResponse<List<Map<String, Object>>> unassignedLaorenOptions(@PathVariable Long id,
                                                                          @RequestParam(required = false) String laorenXingming,
                                                                          HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(huliRenyuanBiz.unassignedLaorenOptions(id, laorenXingming));
    }

    @PostMapping("/assign-laoren")
    public ApiResponse<Void> assignLaoren(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliRenyuanBiz.assignLaoren(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/{huliRenyuanId}/remove-laoren/{laorenId}")
    public ApiResponse<Void> removeLaoren(@PathVariable Long huliRenyuanId, @PathVariable Long laorenId, HttpServletRequest request) {
        requireAdmin(request);
        huliRenyuanBiz.removeLaoren(huliRenyuanId, laorenId);
        return ApiResponse.ok(null);
    }

    private void requireAdmin(HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = dangqianYonghuResolver.resolve(request);
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以操作护理员信息");
        }
    }
}



