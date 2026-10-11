package com.oldman.admin.controller;

import com.oldman.admin.biz.AdminLaorenXiugaiShenqingBiz;
import com.oldman.common.ApiResponse;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.common.auth.DangqianYonghuResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/laoren-xiugai-shenqing")
public class AdminLaorenXiugaiShenqingController {

    private final AdminLaorenXiugaiShenqingBiz adminLaorenXiugaiShenqingBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public AdminLaorenXiugaiShenqingController(AdminLaorenXiugaiShenqingBiz adminLaorenXiugaiShenqingBiz,
                                               DangqianYonghuResolver dangqianYonghuResolver) {
        this.adminLaorenXiugaiShenqingBiz = adminLaorenXiugaiShenqingBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<Map<String, Object>>> list(@RequestParam(required = false) String laorenXingming,
                                                       @RequestParam(required = false) String jiashuXingming,
                                                       @RequestParam(required = false) String shenqingZhuangtai,
                                                       HttpServletRequest request) {
        return ApiResponse.ok(adminLaorenXiugaiShenqingBiz.list(current(request), laorenXingming, jiashuXingming, shenqingZhuangtai));
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<Map<String, Object>> detail(@PathVariable Long id, HttpServletRequest request) {
        return ApiResponse.ok(adminLaorenXiugaiShenqingBiz.detail(current(request), id));
    }

    @PostMapping("/pass")
    public ApiResponse<Void> pass(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminLaorenXiugaiShenqingBiz.pass(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/reject")
    public ApiResponse<Void> reject(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminLaorenXiugaiShenqingBiz.reject(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        adminLaorenXiugaiShenqingBiz.delete(current(request), id);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
