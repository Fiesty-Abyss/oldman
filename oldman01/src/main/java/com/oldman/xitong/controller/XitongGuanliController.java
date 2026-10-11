package com.oldman.xitong.controller;

import com.oldman.common.ApiResponse;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.common.auth.DangqianYonghuResolver;
import com.oldman.xitong.biz.XitongGuanliBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/xitong")
public class XitongGuanliController {

    private final XitongGuanliBiz xitongGuanliBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public XitongGuanliController(XitongGuanliBiz xitongGuanliBiz,
                                  DangqianYonghuResolver dangqianYonghuResolver) {
        this.xitongGuanliBiz = xitongGuanliBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/yonghu-list")
    public ApiResponse<List<Map<String, Object>>> yonghuList(@RequestParam(required = false) String dengluZhanghao,
                                                             @RequestParam(required = false) String xingming,
                                                             @RequestParam(required = false) String jueseBianma,
                                                             @RequestParam(required = false) String zhuangtai,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(xitongGuanliBiz.yonghuList(current(request), dengluZhanghao, xingming, jueseBianma, zhuangtai));
    }

    @PostMapping("/yonghu-add")
    public ApiResponse<Void> yonghuAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        xitongGuanliBiz.yonghuAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/yonghu-update")
    public ApiResponse<Void> yonghuUpdate(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        xitongGuanliBiz.yonghuUpdate(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/yonghu-change-status")
    public ApiResponse<Void> yonghuChangeStatus(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        xitongGuanliBiz.yonghuChangeStatus(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/yonghu-reset-password")
    public ApiResponse<Void> yonghuResetPassword(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        xitongGuanliBiz.yonghuResetPassword(current(request), data);
        return ApiResponse.ok(null);
    }

    @GetMapping("/yonghu-role/{yonghuId}")
    public ApiResponse<List<Map<String, Object>>> yonghuRole(@PathVariable Long yonghuId, HttpServletRequest request) {
        return ApiResponse.ok(xitongGuanliBiz.yonghuRole(current(request), yonghuId));
    }

    @PostMapping("/yonghu-assign-role")
    public ApiResponse<Void> yonghuAssignRole(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        xitongGuanliBiz.yonghuAssignRole(current(request), data);
        return ApiResponse.ok(null);
    }

    @GetMapping("/juese-list")
    public ApiResponse<List<Map<String, Object>>> jueseList(HttpServletRequest request) {
        return ApiResponse.ok(xitongGuanliBiz.jueseList(current(request)));
    }

    @PostMapping("/juese-add")
    public ApiResponse<Void> jueseAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        xitongGuanliBiz.jueseAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/juese-update")
    public ApiResponse<Void> jueseUpdate(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        xitongGuanliBiz.jueseUpdate(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/juese-change-status")
    public ApiResponse<Void> jueseChangeStatus(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        xitongGuanliBiz.jueseChangeStatus(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/juese-delete/{id}")
    public ApiResponse<Void> jueseDelete(@PathVariable Long id, HttpServletRequest request) {
        xitongGuanliBiz.jueseDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/huli-renyuan-options")
    public ApiResponse<List<Map<String, Object>>> huliRenyuanOptions(HttpServletRequest request) {
        return ApiResponse.ok(xitongGuanliBiz.huliRenyuanOptions(current(request)));
    }

    @GetMapping("/jiashu-options")
    public ApiResponse<List<Map<String, Object>>> jiashuOptions(HttpServletRequest request) {
        return ApiResponse.ok(xitongGuanliBiz.jiashuOptions(current(request)));
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
