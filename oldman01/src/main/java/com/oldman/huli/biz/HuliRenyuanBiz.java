package com.oldman.huli.biz;

import java.util.List;
import java.util.Map;

public interface HuliRenyuanBiz {

    List<Map<String, Object>> list();

    void add(Map<String, Object> data);

    void update(Map<String, Object> data);

    void delete(Long id);

    List<Map<String, Object>> fuwuLaoren(Long huliRenyuanId);

    List<Map<String, Object>> unassignedLaorenOptions(Long huliRenyuanId, String laorenXingming);

    void assignLaoren(Map<String, Object> data);

    void removeLaoren(Long huliRenyuanId, Long laorenId);
}


