package com.neu.jiashu.biz;

import com.neu.common.auth.DangqianYonghu;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface JiashuYewuBiz {

    List<Map<String, Object>> laorenXinxi(DangqianYonghu dangqianYonghu);

    Map<String, Object> laorenTouxiangUpload(DangqianYonghu dangqianYonghu, Long laorenId, MultipartFile file);

    List<Map<String, Object>> laorenXiugaiShenqingList(DangqianYonghu dangqianYonghu);

    void laorenXiugaiShenqingAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    Map<String, Object> laorenXiugaiTouxiangUpload(DangqianYonghu dangqianYonghu, Long laorenId, MultipartFile file);

    Map<String, Integer> tongji(DangqianYonghu dangqianYonghu);

    List<Map<String, Object>> chuangweiInfo(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> huliJiluList(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> jiankangJianceList(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> yongyaoJihuaList(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> fuyaoJiluList(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> feiyongZhangdanList(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> jiaofeiJiluList(DangqianYonghu dangqianYonghu, Long laorenId);

    List<Map<String, Object>> waichuList(DangqianYonghu dangqianYonghu, Long laorenId);

    void waichuAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);

    List<Map<String, Object>> tuizhuList(DangqianYonghu dangqianYonghu, Long laorenId);

    void tuizhuAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data);
}
