package com.oldman.denglu.controller;

import com.oldman.common.ApiResponse;
import com.oldman.denglu.dto.DengluQingqiu;
import com.oldman.denglu.dto.DengluXiangying;
import com.oldman.denglu.biz.DengluBiz;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/denglu")
public class DengluController {

    private final DengluBiz dengluBiz;

    public DengluController(DengluBiz dengluBiz) {
        this.dengluBiz = dengluBiz;
    }

    @PostMapping("/login")
    public ApiResponse<DengluXiangying> login(@RequestBody DengluQingqiu qingqiu) {
        return ApiResponse.ok(dengluBiz.denglu(qingqiu));
    }
}



