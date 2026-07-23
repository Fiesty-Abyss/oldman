package com.neu.admin.biz;

import com.neu.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface AdminRuzhuShenqingBiz {

    List<Map<String, Object>> list(DangqianYonghu dangqianYonghu, String laorenXingming,
                                   String jiashuShouji, String shenqingZhuangtai);

    Map<String, Object> detail(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> passedList(DangqianYonghu dangqianYonghu, String laorenXingming, String jiashuShouji);

    Map<String, Object> bring(DangqianYonghu dangqianYonghu, Long id);

    void pass(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void reject(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void delete(DangqianYonghu dangqianYonghu, Long id);
}
