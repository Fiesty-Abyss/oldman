package com.oldman.wenjian.controller;

import com.oldman.common.ApiResponse;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.common.auth.DangqianYonghuResolver;
import com.oldman.wenjian.biz.WenjianBiz;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/wenjian")
public class WenjianController {

    private final WenjianBiz wenjianBiz;
    private final DangqianYonghuResolver dangqianYonghuResolver;

    public WenjianController(WenjianBiz wenjianBiz, DangqianYonghuResolver dangqianYonghuResolver) {
        this.wenjianBiz = wenjianBiz;
        this.dangqianYonghuResolver = dangqianYonghuResolver;
    }

    @GetMapping("/image-list")
    public ApiResponse<List<Map<String, Object>>> imageList(@RequestParam(required = false) String type,
                                                            @RequestParam(required = false) String keyword,
                                                            HttpServletRequest request) {
        return ApiResponse.ok(wenjianBiz.imageList(current(request), type, keyword));
    }

    @PostMapping("/upload-image")
    public ApiResponse<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("type") String type,
                                                        HttpServletRequest request) {
        return ApiResponse.ok(wenjianBiz.uploadImage(current(request), file, type));
    }

    @PostMapping("/replace-image")
    public ApiResponse<Map<String, Object>> replaceImage(@RequestParam("file") MultipartFile file,
                                                         @RequestParam("type") String type,
                                                         @RequestParam("oldName") String oldName,
                                                         HttpServletRequest request) {
        return ApiResponse.ok(wenjianBiz.replaceImage(current(request), file, type, oldName));
    }

    @DeleteMapping("/delete-image")
    public ApiResponse<Void> deleteImage(@RequestParam("type") String type,
                                         @RequestParam("name") String name,
                                         HttpServletRequest request) {
        wenjianBiz.deleteImage(current(request), type, name);
        return ApiResponse.ok(null);
    }

    private DangqianYonghu current(HttpServletRequest request) {
        return dangqianYonghuResolver.resolve(request);
    }
}
