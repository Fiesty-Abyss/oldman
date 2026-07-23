package com.neu.wenjian.biz;

import com.neu.common.auth.DangqianYonghu;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface WenjianBiz {

    List<Map<String, Object>> imageList(DangqianYonghu dangqianYonghu, String type, String keyword);

    Map<String, Object> uploadImage(DangqianYonghu dangqianYonghu, MultipartFile file, String type);

    Map<String, Object> replaceImage(DangqianYonghu dangqianYonghu, MultipartFile file, String type, String oldName);

    void deleteImage(DangqianYonghu dangqianYonghu, String type, String name);
}
