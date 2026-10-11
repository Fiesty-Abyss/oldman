package com.oldman.laoren.biz;

import com.oldman.common.auth.DangqianYonghu;
import com.oldman.laoren.entity.LaorenXinxi;

import java.util.List;
import java.util.Map;

public interface LaorenXinxiBiz {

    List<LaorenXinxi> list(DangqianYonghu dangqianYonghu);

    List<Map<String, Object>> options();

    void add(Map<String, Object> data);

    void update(Map<String, Object> data);

    void delete(Long id);

    void ruzhu(Map<String, Object> data, DangqianYonghu dangqianYonghu);
}


