package com.oldman.huliguanli.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LaorenHuliMapper {

    List<Map<String, Object>> findLaorenList();

    Map<String, Object> findCurrentByLaorenId(@Param("laorenId") Long laorenId);

    void endCurrentByLaorenId(@Param("laorenId") Long laorenId);

    void insert(Map<String, Object> data);

    void updateLaorenLeixing(@Param("laorenId") Long laorenId, @Param("laorenLeixing") String laorenLeixing);
}


