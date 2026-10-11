package com.oldman.admin.controller;

import com.oldman.admin.biz.AdminFeiyongBiz;
import com.oldman.common.ApiResponse;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.common.auth.DangqianYonghuResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/feiyong")
public class AdminFeiyongController {

    private final AdminFeiyongBiz adminFeiyongBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public AdminFeiyongController(AdminFeiyongBiz adminFeiyongBiz,
                                  DangqianYonghuResolver dangqianYonghuResolver) {
        this.adminFeiyongBiz = adminFeiyongBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/zhangdan-list")
    public ApiResponse<List<Map<String, Object>>> zhangdanList(@RequestParam(required = false) String laorenXingming,
                                                               @RequestParam(required = false) String zhangdanYuefen,
                                                               @RequestParam(required = false) String zhuangtai,
                                                               HttpServletRequest request) {
        return ApiResponse.ok(adminFeiyongBiz.zhangdanList(current(request), laorenXingming, zhangdanYuefen, zhuangtai));
    }

    @PostMapping("/zhangdan-add")
    public ApiResponse<Void> zhangdanAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminFeiyongBiz.zhangdanAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/zhangdan-update")
    public ApiResponse<Void> zhangdanUpdate(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminFeiyongBiz.zhangdanUpdate(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/zhangdan-delete/{id}")
    public ApiResponse<Void> zhangdanDelete(@PathVariable Long id, HttpServletRequest request) {
        adminFeiyongBiz.zhangdanDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/zhangdan-detail/{id}")
    public ApiResponse<Map<String, Object>> zhangdanDetail(@PathVariable Long id, HttpServletRequest request) {
        return ApiResponse.ok(adminFeiyongBiz.zhangdanDetail(current(request), id));
    }

    @GetMapping("/jiaofei-list")
    public ApiResponse<List<Map<String, Object>>> jiaofeiList(@RequestParam(required = false) Long zhangdanId,
                                                              @RequestParam(required = false) String laorenXingming,
                                                              @RequestParam(required = false) String zhangdanYuefen,
                                                              HttpServletRequest request) {
        return ApiResponse.ok(adminFeiyongBiz.jiaofeiList(current(request), zhangdanId, laorenXingming, zhangdanYuefen));
    }

    @PostMapping("/jiaofei-add")
    public ApiResponse<Void> jiaofeiAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminFeiyongBiz.jiaofeiAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/jiaofei-delete/{id}")
    public ApiResponse<Void> jiaofeiDelete(@PathVariable Long id, HttpServletRequest request) {
        adminFeiyongBiz.jiaofeiDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
