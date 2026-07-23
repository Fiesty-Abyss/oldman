package com.neu.huli.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HuliRenyuanMapper {

    List<Map<String, Object>> findAll();

    void insert(Map<String, Object> data);

    void update(Map<String, Object> data);

    void deleteLogic(@Param("id") Long id);

    List<Map<String, Object>> findFuwuLaoren(@Param("huliRenyuanId") Long huliRenyuanId);

    List<Map<String, Object>> findUnassignedLaorenOptions(@Param("huliRenyuanId") Long huliRenyuanId,
                                                          @Param("laorenXingming") String laorenXingming);

    int countServingAssignment(@Param("huliRenyuanId") Long huliRenyuanId, @Param("laorenId") Long laorenId);

    void endLaorenAssignment(@Param("huliRenyuanId") Long huliRenyuanId, @Param("laorenId") Long laorenId);

    void assignLaoren(Map<String, Object> data);
}


