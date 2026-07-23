package com.neu.huliguanli.biz;

import java.util.List;
import java.util.Map;

public interface HuliJibieBiz {

    List<Map<String, Object>> list(String jibieMingcheng, String zhuangtai);

    void add(Map<String, Object> data);

    void update(Map<String, Object> data);

    void changeStatus(Map<String, Object> data);

    void delete(Long id);

    List<Map<String, Object>> xiangmuList(Long jibieId);

    List<Map<String, Object>> unboundXiangmuList(Long jibieId);

    void bindXiangmu(Map<String, Object> data);

    void unbindXiangmu(Long jibieId, Long xiangmuId);
}


