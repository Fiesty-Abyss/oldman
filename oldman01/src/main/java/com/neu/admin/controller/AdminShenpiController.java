package com.neu.admin.controller;

import com.neu.admin.biz.AdminShenpiBiz;
import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/shenpi")
public class AdminShenpiController {

    private final AdminShenpiBiz adminShenpiBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public AdminShenpiController(AdminShenpiBiz adminShenpiBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.adminShenpiBiz = adminShenpiBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/waichu-list")
    public ApiResponse<List<Map<String, Object>>> waichuList(@RequestParam(required = false) String laorenXingming,
                                                             @RequestParam(required = false) String shenpiZhuangtai,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(adminShenpiBiz.waichuList(current(request), laorenXingming, shenpiZhuangtai));
    }

    @PostMapping("/waichu-pass")
    public ApiResponse<Void> waichuPass(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminShenpiBiz.waichuPass(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/waichu-reject")
    public ApiResponse<Void> waichuReject(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminShenpiBiz.waichuReject(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/waichu-huiyuan")
    public ApiResponse<Void> waichuHuiyuan(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminShenpiBiz.waichuHuiyuan(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/waichu-delete/{id}")
    public ApiResponse<Void> waichuDelete(@PathVariable Long id, HttpServletRequest request) {
        adminShenpiBiz.waichuDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/tuizhu-list")
    public ApiResponse<List<Map<String, Object>>> tuizhuList(@RequestParam(required = false) String laorenXingming,
                                                             @RequestParam(required = false) String shenpiZhuangtai,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(adminShenpiBiz.tuizhuList(current(request), laorenXingming, shenpiZhuangtai));
    }

    @PostMapping("/tuizhu-pass")
    public ApiResponse<Void> tuizhuPass(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminShenpiBiz.tuizhuPass(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/tuizhu-reject")
    public ApiResponse<Void> tuizhuReject(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminShenpiBiz.tuizhuReject(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/tuizhu-delete/{id}")
    public ApiResponse<Void> tuizhuDelete(@PathVariable Long id, HttpServletRequest request) {
        adminShenpiBiz.tuizhuDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
