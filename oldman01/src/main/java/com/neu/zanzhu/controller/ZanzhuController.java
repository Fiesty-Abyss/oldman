package com.neu.zanzhu.controller;

import com.neu.common.ApiResponse;
import com.neu.zanzhu.biz.ZanzhuBiz;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zanzhu")
public class ZanzhuController {

    private final ZanzhuBiz zanzhuBiz;

    public ZanzhuController(ZanzhuBiz zanzhuBiz) {
        this.zanzhuBiz = zanzhuBiz;
    }

    @GetMapping("/gongkai-list")
    public ApiResponse<List<Map<String, Object>>> gongkaiList() {
        return ApiResponse.ok(zanzhuBiz.gongkaiList());
    }

    @GetMapping("/yongtu-list")
    public ApiResponse<List<Map<String, Object>>> yongtuList() {
        return ApiResponse.ok(zanzhuBiz.yongtuList());
    }

    @GetMapping("/wuzi-list")
    public ApiResponse<List<Map<String, Object>>> wuziList() {
        return ApiResponse.ok(zanzhuBiz.wuziList());
    }
}



