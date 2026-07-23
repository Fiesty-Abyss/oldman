package com.neu.huliyuan.controller;

import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import com.neu.huliyuan.biz.HuliyuanYewuBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/huliyuan")
public class HuliyuanYewuController {

    private final HuliyuanYewuBiz huliyuanYewuBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public HuliyuanYewuController(HuliyuanYewuBiz huliyuanYewuBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.huliyuanYewuBiz = huliyuanYewuBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/laoren-list")
    public ApiResponse<List<Map<String, Object>>> laorenList(@RequestParam(required = false) String laorenXingming,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.laorenList(current(request), laorenXingming));
    }

    @GetMapping("/laoren-detail/{laorenId}")
    public ApiResponse<Map<String, Object>> laorenDetail(@PathVariable Long laorenId, HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.laorenDetail(current(request), laorenId));
    }

    @GetMapping("/tongji")
    public ApiResponse<Map<String, Integer>> tongji(HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.tongji(current(request)));
    }

    @GetMapping("/{laorenId}/huli-xiangmu-list")
    public ApiResponse<List<Map<String, Object>>> huliXiangmuList(@PathVariable Long laorenId, HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.huliXiangmuList(current(request), laorenId));
    }

    @GetMapping("/huli-jilu/list")
    public ApiResponse<List<Map<String, Object>>> huliJiluList(@RequestParam(required = false) Long laorenId,
                                                               HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.huliJiluList(current(request), laorenId));
    }

    @PostMapping("/huli-jilu/add")
    public ApiResponse<Void> addHuliJilu(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        huliyuanYewuBiz.addHuliJilu(current(request), data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/huli-jilu/delete/{id}")
    public ApiResponse<Void> deleteHuliJilu(@PathVariable Long id, HttpServletRequest request) {
        huliyuanYewuBiz.deleteHuliJilu(current(request), id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/jiankang-jiance/list")
    public ApiResponse<List<Map<String, Object>>> jiankangJianceList(@RequestParam(required = false) Long laorenId,
                                                                     HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.jiankangJianceList(current(request), laorenId));
    }

    @PostMapping("/jiankang-jiance/add")
    public ApiResponse<Void> addJiankangJiance(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        huliyuanYewuBiz.addJiankangJiance(current(request), data);
        return ApiResponse.ok(null);
    }

    @GetMapping("/yongyao-jihua/list")
    public ApiResponse<List<Map<String, Object>>> yongyaoJihuaList(@RequestParam(required = false) Long laorenId,
                                                                   HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.yongyaoJihuaList(current(request), laorenId));
    }

    @GetMapping("/fuyao-jilu/list")
    public ApiResponse<List<Map<String, Object>>> fuyaoJiluList(@RequestParam(required = false) Long laorenId,
                                                                HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.fuyaoJiluList(current(request), laorenId));
    }

    @PostMapping("/fuyao-jilu/add")
    public ApiResponse<Void> addFuyaoJilu(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        huliyuanYewuBiz.addFuyaoJilu(current(request), data);
        return ApiResponse.ok(null);
    }

    @GetMapping("/waichu/list")
    public ApiResponse<List<Map<String, Object>>> waichuList(@RequestParam(required = false) Long laorenId,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.waichuList(current(request), laorenId));
    }

    @PostMapping("/waichu/add")
    public ApiResponse<Void> addWaichu(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        huliyuanYewuBiz.addWaichu(current(request), data);
        return ApiResponse.ok(null);
    }

    @GetMapping("/tuizhu/list")
    public ApiResponse<List<Map<String, Object>>> tuizhuList(@RequestParam(required = false) Long laorenId,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(huliyuanYewuBiz.tuizhuList(current(request), laorenId));
    }

    @PostMapping("/tuizhu/add")
    public ApiResponse<Void> addTuizhu(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        huliyuanYewuBiz.addTuizhu(current(request), data);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}



