package com.oldman.admin.controller;

import com.oldman.admin.biz.AdminRuzhuShenqingBiz;
import com.oldman.common.ApiResponse;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.common.auth.DangqianYonghuResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/ruzhu-shenqing")
public class AdminRuzhuShenqingController {

    private final AdminRuzhuShenqingBiz adminRuzhuShenqingBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public AdminRuzhuShenqingController(AdminRuzhuShenqingBiz adminRuzhuShenqingBiz,
                                        DangqianYonghuResolver dangqianYonghuResolver) {
        this.adminRuzhuShenqingBiz = adminRuzhuShenqingBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<Map<String, Object>>> list(@RequestParam(required = false) String laorenXingming,
                                                       @RequestParam(required = false) String jiashuShouji,
                                                       @RequestParam(required = false) String shenqingZhuangtai,
                                                       HttpServletRequest request) {
        return ApiResponse.ok(adminRuzhuShenqingBiz.list(current(request), laorenXingming, jiashuShouji, shenqingZhuangtai));
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<Map<String, Object>> detail(@PathVariable Long id, HttpServletRequest request) {
        return ApiResponse.ok(adminRuzhuShenqingBiz.detail(current(request), id));
    }

    @GetMapping("/passed-list")
    public ApiResponse<List<Map<String, Object>>> passedList(@RequestParam(required = false) String laorenXingming,
                                                             @RequestParam(required = false) String jiashuShouji,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(adminRuzhuShenqingBiz.passedList(current(request), laorenXingming, jiashuShouji));
    }

    @GetMapping("/bring/{id}")
    public ApiResponse<Map<String, Object>> bring(@PathVariable Long id, HttpServletRequest request) {
        return ApiResponse.ok(adminRuzhuShenqingBiz.bring(current(request), id));
    }

    @PostMapping("/pass")
    public ApiResponse<Void> pass(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminRuzhuShenqingBiz.pass(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/reject")
    public ApiResponse<Void> reject(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        adminRuzhuShenqingBiz.reject(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        adminRuzhuShenqingBiz.delete(current(request), id);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
