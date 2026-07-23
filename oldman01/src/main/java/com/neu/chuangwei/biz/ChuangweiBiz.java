package com.neu.chuangwei.biz;

import java.util.List;
import java.util.Map;

public interface ChuangweiBiz {

    List<Map<String, Object>> list();

    List<Map<String, Object>> fangjianList();

    List<Map<String, Object>> fangjianOptions();

    void fangjianAdd(Map<String, Object> data);

    void fangjianUpdate(Map<String, Object> data);

    void fangjianDelete(Long id);

    List<Map<String, Object>> chuangweiBaseList();

    List<Map<String, Object>> kongxianOptions(Long fangjianId);

    void chuangweiAdd(Map<String, Object> data);

    void chuangweiUpdate(Map<String, Object> data);

    void chuangweiDelete(Long id);

    void fenpei(Map<String, Object> data);

    void tiaohuan(Map<String, Object> data);

    void shifang(Long laorenId);
}


