package com.oldman.admin.biz;

import com.oldman.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface AdminLaorenXiugaiShenqingBiz {

    List<Map<String, Object>> list(DangqianYonghu dangqianYonghu, String laorenXingming,
                                   String jiashuXingming, String shenqingZhuangtai);

    Map<String, Object> detail(DangqianYonghu dangqianYonghu, Long id);

    void pass(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void reject(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void delete(DangqianYonghu dangqianYonghu, Long id);
}
