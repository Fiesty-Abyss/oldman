package com.neu.yaopin.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YaopinMapper {

    List<Map<String, Object>> findList(@Param("yaopinMingcheng") String yaopinMingcheng);

    void insert(Map<String, Object> data);

    void disable(@Param("id") Long id);
}
