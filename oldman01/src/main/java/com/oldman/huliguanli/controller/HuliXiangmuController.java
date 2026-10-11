package com.oldman.huliguanli.controller;

import com.oldman.common.ApiResponse;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.common.auth.DangqianYonghuResolver;
import com.oldman.huliguanli.biz.HuliXiangmuBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/huli-xiangmu")
public class HuliXiangmuController {

    private final HuliXiangmuBiz huliXiangmuBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public HuliXiangmuController(HuliXiangmuBiz huliXiangmuBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.huliXiangmuBiz = huliXiangmuBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/list")
    public ApiResponse<List<Map<String, Object>>> list(@RequestParam(required = false) String xiangmuMingcheng,
                                                       @RequestParam(required = false) String zhuangtai,
                                                       HttpServletRequest request) {
        requireAdmin(request);
        return ApiResponse.ok(huliXiangmuBiz.list(xiangmuMingcheng, zhuangtai));
    }

    @PostMapping("/add")
    public ApiResponse<Void> add(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliXiangmuBiz.add(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/update")
    public ApiResponse<Void> update(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliXiangmuBiz.update(data);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        requireAdmin(request);
        huliXiangmuBiz.delete(id);
        return ApiResponse.ok(null);
    }

    @PostMapping("/change-status")
    public ApiResponse<Void> changeStatus(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        requireAdmin(request);
        huliXiangmuBiz.changeStatus(data);
        return ApiResponse.ok(null);
    }

    private void requireAdmin(HttpServletRequest request) {
        DangqianYonghu dangqianYonghu = dangqianYonghuResolver.resolve(request);
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以操作护理项目");
        }
    }
}



