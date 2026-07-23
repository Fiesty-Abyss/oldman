package com.neu.admin.controller;

import com.neu.admin.biz.AdminJiluBiz;
import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/jilu")
public class AdminJiluController {

    private final AdminJiluBiz adminJiluBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public AdminJiluController(AdminJiluBiz adminJiluBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.adminJiluBiz = adminJiluBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/huli-list")
    public ApiResponse<List<Map<String, Object>>> huliList(@RequestParam(required = false) String laorenXingming,
                                                           @RequestParam(required = false) String huliRenyuanXingming,
                                                           @RequestParam(required = false) String xiangmuMingcheng,
                                                           @RequestParam(required = false) String riqi,
                                                           HttpServletRequest request) {
        return ApiResponse.ok(adminJiluBiz.huliList(current(request), laorenXingming, huliRenyuanXingming, xiangmuMingcheng, riqi));
    }

    @DeleteMapping("/huli-delete/{id}")
    public ApiResponse<Void> huliDelete(@PathVariable Long id, HttpServletRequest request) {
        adminJiluBiz.huliDelete(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/jiankang-list")
    public ApiResponse<List<Map<String, Object>>> jiankangList(@RequestParam(required = false) String laorenXingming,
                                                               @RequestParam(required = false) String riqi,
                                                               HttpServletRequest request) {
        return ApiResponse.ok(adminJiluBiz.jiankangList(current(request), laorenXingming, riqi));
    }

    @GetMapping("/fuyao-list")
    public ApiResponse<List<Map<String, Object>>> fuyaoList(@RequestParam(required = false) String laorenXingming,
                                                            @RequestParam(required = false) String yaopinMingcheng,
                                                            @RequestParam(required = false) String zhixingZhuangtai,
                                                            @RequestParam(required = false) String riqi,
                                                            HttpServletRequest request) {
        return ApiResponse.ok(adminJiluBiz.fuyaoList(current(request), laorenXingming, yaopinMingcheng, zhixingZhuangtai, riqi));
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
