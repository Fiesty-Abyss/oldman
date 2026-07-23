package com.neu.admin.biz;

import com.neu.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface AdminJiluBiz {

    List<Map<String, Object>> huliList(DangqianYonghu dangqianYonghu, String laorenXingming,
                                       String huliRenyuanXingming, String xiangmuMingcheng, String riqi);

    void huliDelete(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> jiankangList(DangqianYonghu dangqianYonghu, String laorenXingming, String riqi);

    List<Map<String, Object>> fuyaoList(DangqianYonghu dangqianYonghu, String laorenXingming,
                                        String yaopinMingcheng, String zhixingZhuangtai, String riqi);
}
