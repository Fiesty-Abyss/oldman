package com.neu.huliyuan.biz;

import com.neu.common.auth.DangqianYonghu;

import java.util.List;
import java.util.Map;

public interface HuliyuanYewuBiz {

    List<Map<String, Object>> laorenList(DangqianYonghu dangqianYonghu, String laorenXingming);

    Map<String, Object> laorenDetail(DangqianYonghu dangqianYonghu, Long laorenId);

    Map<String, Integer> tongji(DangqianYonghu dangqianYonghu);

    List<Map<String, Object>> huliXiangmuList(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> huliJiluList(DangqianYonghu dangqianYonghu, Long laorenId);

    void addHuliJilu(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    void deleteHuliJilu(DangqianYonghu dangqianYonghu, Long id);

    List<Map<String, Object>> jiankangJianceList(DangqianYonghu dangqianYonghu, Long laorenId);

    void addJiankangJiance(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    List<Map<String, Object>> yongyaoJihuaList(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> fuyaoJiluList(DangqianYonghu dangqianYonghu, Long laorenId);

    void addFuyaoJilu(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    List<Map<String, Object>> waichuList(DangqianYonghu dangqianYonghu, Long laorenId);

    void addWaichu(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    List<Map<String, Object>> tuizhuList(DangqianYonghu dangqianYonghu, Long laorenId);

    void addTuizhu(DangqianYonghu dangqianYonghu, Map<String, Object> data);
}


