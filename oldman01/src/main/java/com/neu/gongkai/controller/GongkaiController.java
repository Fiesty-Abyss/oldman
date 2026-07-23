package com.neu.gongkai.controller;

import com.neu.common.ApiResponse;
import com.neu.gongkai.biz.GongkaiBiz;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/gongkai")
public class GongkaiController {

    private final GongkaiBiz gongkaiBiz;

    public GongkaiController(GongkaiBiz gongkaiBiz) {
        this.gongkaiBiz = gongkaiBiz;
    }

    @PostMapping("/jiashu-zhuce")
    public ApiResponse<Void> jiashuZhuce(@RequestBody Map<String, Object> data) {
        gongkaiBiz.jiashuZhuce(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/ruzhu-shenqing-add")
    public ApiResponse<Void> ruzhuShenqingAdd(@RequestBody Map<String, Object> data) {
        gongkaiBiz.ruzhuShenqingAdd(data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/zanzhu-yixiang-add")
    public ApiResponse<Void> zanzhuYixiangAdd(@RequestBody Map<String, Object> data) {
        gongkaiBiz.zanzhuYixiangAdd(data);
        return ApiResponse.ok(null);
    }
}
