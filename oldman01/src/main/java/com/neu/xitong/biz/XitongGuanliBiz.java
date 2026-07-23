package com.neu.xitong.biz;

import com.neu.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface XitongGuanliBiz {

    List<Map<String, Object>> yonghuList(DangqianYonghu dangqianYonghu, String dengluZhanghao,
                                         String xingming, String jueseBianma, String zhuangtai);

    void yonghuAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void yonghuUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void yonghuChangeStatus(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void yonghuResetPassword(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    List<Map<String, Object>> yonghuRole(DangqianYonghu dangqianYonghu, Long yonghuId);

    void yonghuAssignRole(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    List<Map<String, Object>> jueseList(DangqianYonghu dangqianYonghu);

    void jueseAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void jueseUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void jueseChangeStatus(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void jueseDelete(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> huliRenyuanOptions(DangqianYonghu dangqianYonghu);

    List<Map<String, Object>> jiashuOptions(DangqianYonghu dangqianYonghu);
}
