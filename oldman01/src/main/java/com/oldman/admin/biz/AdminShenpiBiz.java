package com.oldman.admin.biz;

import com.oldman.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface AdminShenpiBiz {

    List<Map<String, Object>> waichuList(DangqianYonghu dangqianYonghu, String laorenXingming, String shenpiZhuangtai);

    void waichuPass(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void waichuReject(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void waichuHuiyuan(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void waichuDelete(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> tuizhuList(DangqianYonghu dangqianYonghu, String laorenXingming, String shenpiZhuangtai);

    void tuizhuPass(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void tuizhuReject(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void tuizhuDelete(DangqianYonghu dangqianYonghu, Long id);
}
