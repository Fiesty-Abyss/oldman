package com.neu.jiashu.controller;

import com.neu.common.ApiResponse;
import com.neu.common.auth.DangqianYonghu;
import com.neu.common.auth.DangqianYonghuResolver;
import com.neu.jiashu.biz.JiashuYewuBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jiashu")
public class JiashuYewuController {

    private final JiashuYewuBiz jiashuYewuBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public JiashuYewuController(JiashuYewuBiz jiashuYewuBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.jiashuYewuBiz = jiashuYewuBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/laoren-xinxi")
    public ApiResponse<List<Map<String, Object>>> laorenXinxi(HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.laorenXinxi(current(request)));
    }

    @PostMapping("/laoren-touxiang-upload")
    public ApiResponse<Map<String, Object>> laorenTouxiangUpload(@RequestParam Long laorenId,
                                                                 @RequestParam MultipartFile file,
                                                                 HttpServletRequest request) {
        return new ApiResponse<>(200, "头像更新成功", jiashuYewuBiz.laorenTouxiangUpload(current(request), laorenId, file));
    }

    @GetMapping("/laoren-xiugai-shenqing-list")
    public ApiResponse<List<Map<String, Object>>> laorenXiugaiShenqingList(HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.laorenXiugaiShenqingList(current(request)));
    }

    @PostMapping("/laoren-xiugai-shenqing-add")
    public ApiResponse<Void> laorenXiugaiShenqingAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        jiashuYewuBiz.laorenXiugaiShenqingAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @PostMapping("/laoren-xiugai-touxiang-upload")
    public ApiResponse<Map<String, Object>> laorenXiugaiTouxiangUpload(@RequestParam Long laorenId,
                                                                       @RequestParam MultipartFile file,
                                                                       HttpServletRequest request) {
        return new ApiResponse<>(200, "头像已上传，审核通过后生效", jiashuYewuBiz.laorenXiugaiTouxiangUpload(current(request), laorenId, file));
    }

    @GetMapping("/tongji")
    public ApiResponse<Map<String, Integer>> tongji(HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.tongji(current(request)));
    }

    @GetMapping("/chuangwei-info")
    public ApiResponse<List<Map<String, Object>>> chuangweiInfo(@RequestParam(required = false) Long laorenId,
                                                                HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.chuangweiInfo(current(request), laorenId));
    }

    @GetMapping("/huli-jilu-list")
    public ApiResponse<List<Map<String, Object>>> huliJiluList(@RequestParam(required = false) Long laorenId,
                                                               HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.huliJiluList(current(request), laorenId));
    }

    @GetMapping("/jiankang-jiance-list")
    public ApiResponse<List<Map<String, Object>>> jiankangJianceList(@RequestParam(required = false) Long laorenId,
                                                                     HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.jiankangJianceList(current(request), laorenId));
    }

    @GetMapping("/yongyao-jihua-list")
    public ApiResponse<List<Map<String, Object>>> yongyaoJihuaList(@RequestParam(required = false) Long laorenId,
                                                                   HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.yongyaoJihuaList(current(request), laorenId));
    }

    @GetMapping("/fuyao-jilu-list")
    public ApiResponse<List<Map<String, Object>>> fuyaoJiluList(@RequestParam(required = false) Long laorenId,
                                                                HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.fuyaoJiluList(current(request), laorenId));
    }

    @GetMapping("/feiyong-zhangdan-list")
    public ApiResponse<List<Map<String, Object>>> feiyongZhangdanList(@RequestParam(required = false) Long laorenId,
                                                                      HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.feiyongZhangdanList(current(request), laorenId));
    }

    @GetMapping("/jiaofei-jilu-list")
    public ApiResponse<List<Map<String, Object>>> jiaofeiJiluList(@RequestParam(required = false) Long laorenId,
                                                                  HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.jiaofeiJiluList(current(request), laorenId));
    }

    @GetMapping("/waichu-list")
    public ApiResponse<List<Map<String, Object>>> waichuList(@RequestParam(required = false) Long laorenId,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.waichuList(current(request), laorenId));
    }

    @PostMapping("/waichu-add")
    public ApiResponse<Void> waichuAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        jiashuYewuBiz.waichuAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    @GetMapping("/tuizhu-list")
    public ApiResponse<List<Map<String, Object>>> tuizhuList(@RequestParam(required = false) Long laorenId,
                                                             HttpServletRequest request) {
        return ApiResponse.ok(jiashuYewuBiz.tuizhuList(current(request), laorenId));
    }

    @PostMapping("/tuizhu-add")
    public ApiResponse<Void> tuizhuAdd(@RequestBody Map<String, Object> data, HttpServletRequest request) {
        jiashuYewuBiz.tuizhuAdd(current(request), data);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
