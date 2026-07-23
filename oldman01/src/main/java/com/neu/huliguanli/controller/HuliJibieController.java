package com.neu.huliguanli.controller;

import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import com.neu.huliguanli.biz.HuliJibieBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/huli-jibie")
public class HuliJibieController {

    private final HuliJibieBiz huliJibieBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public HuliJibieController(HuliJibieBiz huliJibieBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.huliJibieBiz = huliJibieBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<Map<String, Object>>> list(@RequestParam(required = false) String jibieMingcheng,
                                                       @RequestParam(required = false) String zhuangtai,
                                                       HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(huliJibieBiz.list(jibieMingcheng, zhuangtai));
    }

    @PostMapping("/add")
    public ApiResponse<Void> add(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliJibieBiz.add(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliJibieBiz.update(data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        huliJibieBiz.delete(id);
        return ApiResponse.ok(null);
    }

    @PostMapping("/change-status")
    public ApiResponse<Void> changeStatus(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliJibieBiz.changeStatus(data);
        return ApiResponse.ok(null);
    }

    @GetMapping("/{id}/xiangmu-list")
    public ApiResponse<List<Map<String, Object>>> xiangmuList(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(huliJibieBiz.xiangmuList(id));
    }

    @GetMapping("/{id}/unbound-xiangmu-list")
    public ApiResponse<List<Map<String, Object>>> unboundXiangmuList(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(huliJibieBiz.unboundXiangmuList(id));
    }

    @PostMapping("/bind-xiangmu")
    public ApiResponse<Void> bindXiangmu(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliJibieBiz.bindXiangmu(data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/unbind-xiangmu")
    public ApiResponse<Void> unbindXiangmu(@RequestParam Long jibieId,
                                           @RequestParam Long xiangmuId,
                                           HttpServletRequest request) {
        requireAdmin(request);
        huliJibieBiz.unbindXiangmu(jibieId, xiangmuId);
        return ApiResponse.ok(null);
    }

    private void requireAdmin(HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = dangqianYonghuResolver.resolve(request);
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以操作护理级别");
        }
    }
}



