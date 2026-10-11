package com.oldman.zanzhu.biz;

import com.oldman.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface AdminZanzhuBiz {

    List<Map<String, Object>> renList(DangqianYonghu dangqianYonghu, String keyword);

    void renAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void renUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void renDelete(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> jiluList(DangqianYonghu dangqianYonghu, String keyword);

    void jiluAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void jiluUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void jiluDelete(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> wuziList(DangqianYonghu dangqianYonghu, String keyword);

    void wuziAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void wuziUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void wuziDelete(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> yongtuList(DangqianYonghu dangqianYonghu, String keyword);

    void yongtuAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void yongtuUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void yongtuDelete(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> yixiangList(DangqianYonghu dangqianYonghu, String keyword, String chuliZhuangtai);

    Map<String, Object> yixiangDetail(DangqianYonghu dangqianYonghu, Long id);

    void yixiangUpdateStatus(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void yixiangDelete(DangqianYonghu dangqianYonghu, Long id);
}
