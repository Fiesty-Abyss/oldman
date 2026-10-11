package com.oldman.huliguanli.biz;

import java.util.List;
import java.util.Map;

public interface HuliXiangmuBiz {

    List<Map<String, Object>> list(String xiangmuMingcheng, String zhuangtai);

    void add(Map<String, Object> data);

    void update(Map<String, Object> data);

    void changeStatus(Map<String, Object> data);

    void delete(Long id);
}


