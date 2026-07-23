package com.neu.admin.biz;

import com.neu.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface AdminFeiyongBiz {

    List<Map<String, Object>> zhangdanList(DangqianYonghu dangqianYonghu, String laorenXingming,
                                           String zhangdanYuefen, String zhuangtai);

    void zhangdanAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void zhangdanUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void zhangdanDelete(DangqianYonghu dangqianYonghu, Long id);

    Map<String, Object> zhangdanDetail(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> jiaofeiList(DangqianYonghu dangqianYonghu, Long zhangdanId,
                                          String laorenXingming, String zhangdanYuefen);

    void jiaofeiAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void jiaofeiDelete(DangqianYonghu dangqianYonghu, Long id);
}
