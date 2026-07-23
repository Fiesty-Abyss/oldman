package com.neu.huliguanli.biz;

import java.util.List;
import java.util.Map;

public interface LaorenHuliBiz {

    List<Map<String, Object>> laorenList();

    Map<String, Object> current(Long laorenId);

    void setJibie(Map<String, Object> data);

    void remove(Long laorenId);
}


