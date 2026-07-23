package com.neu.zanzhu.controller;

import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import com.neu.zanzhu.biz.AdminZanzhuBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/zanzhu")
public class AdminZanzhuController {

    private final AdminZanzhuBiz adminZanzhuBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public AdminZanzhuController(AdminZanzhuBiz adminZanzhuBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.adminZanzhuBiz = adminZanzhuBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/ren-list")
    public ApiResponse<List<Map<String, Object>>> renList(@RequestParam(required = false) String keyword,
                                                          HttpServletRequest request) {
        return ApiResponse.ok(adminZanzhuBiz.renList(current(request), keyword));
    }

    @PostMapping("/ren-add")
    public ApiResponse<Void> renAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.renAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/ren-update")
    public ApiResponse<Void> renUpdate(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.renUpdate(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/ren-delete/{id}")
    public ApiResponse<Void> renDelete(@PathVariable Long id, HttpServletRequest request) {
        adminZanzhuBiz.renDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/jilu-list")
    public ApiResponse<List<Map<String, Object>>> jiluList(@RequestParam(required = false) String keyword,
                                                           HttpServletRequest request) {
        return ApiResponse.ok(adminZanzhuBiz.jiluList(current(request), keyword));
    }

    @PostMapping("/jilu-add")
    public ApiResponse<Void> jiluAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.jiluAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/jilu-update")
    public ApiResponse<Void> jiluUpdate(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.jiluUpdate(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/jilu-delete/{id}")
    public ApiResponse<Void> jiluDelete(@PathVariable Long id, HttpServletRequest request) {
        adminZanzhuBiz.jiluDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/wuzi-list")
    public ApiResponse<List<Map<String, Object>>> wuziList(@RequestParam(required = false) String keyword,
                                                           HttpServletRequest request) {
        return ApiResponse.ok(adminZanzhuBiz.wuziList(current(request), keyword));
    }

    @PostMapping("/wuzi-add")
    public ApiResponse<Void> wuziAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.wuziAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/wuzi-update")
    public ApiResponse<Void> wuziUpdate(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.wuziUpdate(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/wuzi-delete/{id}")
    public ApiResponse<Void> wuziDelete(@PathVariable Long id, HttpServletRequest request) {
        adminZanzhuBiz.wuziDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/yongtu-list")
    public ApiResponse<List<Map<String, Object>>> yongtuList(@RequestParam(required = false) String keyword,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(adminZanzhuBiz.yongtuList(current(request), keyword));
    }

    @PostMapping("/yongtu-add")
    public ApiResponse<Void> yongtuAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.yongtuAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/yongtu-update")
    public ApiResponse<Void> yongtuUpdate(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.yongtuUpdate(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/yongtu-delete/{id}")
    public ApiResponse<Void> yongtuDelete(@PathVariable Long id, HttpServletRequest request) {
        adminZanzhuBiz.yongtuDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/yixiang-list")
    public ApiResponse<List<Map<String, Object>>> yixiangList(@RequestParam(required = false) String keyword,
                                                              @RequestParam(required = false) String chuliZhuangtai,
                                                              HttpServletRequest request) {
        return ApiResponse.ok(adminZanzhuBiz.yixiangList(current(request), keyword, chuliZhuangtai));
    }

    @GetMapping("/yixiang-detail/{id}")
    public ApiResponse<Map<String, Object>> yixiangDetail(@PathVariable Long id, HttpServletRequest request) {
        return ApiResponse.ok(adminZanzhuBiz.yixiangDetail(current(request), id));
    }

    @PostMapping("/yixiang-update-status")
    public ApiResponse<Void> yixiangUpdateStatus(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminZanzhuBiz.yixiangUpdateStatus(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/yixiang-delete/{id}")
    public ApiResponse<Void> yixiangDelete(@PathVariable Long id, HttpServletRequest request) {
        adminZanzhuBiz.yixiangDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
