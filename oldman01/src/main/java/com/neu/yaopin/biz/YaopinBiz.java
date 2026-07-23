package com.neu.yaopin.biz;

import com.neu.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface YaopinBiz {

    List<Map<String, Object>> list(DangqianYonghu dangqianYonghu, String yaopinMingcheng);

    void add(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void delete(DangqianYonghu dangqianYonghu, Long id);
}
