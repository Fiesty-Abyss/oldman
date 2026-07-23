package com.neu.huliguanli.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HuliXiangmuMapper {

    List<Map<String, Object>> findList(@Param("xiangmuMingcheng") String xiangmuMingcheng,
                                       @Param("zhuangtai") String zhuangtai);

    List<Map<String, Object>> findBoundByJibieId(@Param("jibieId") Long jibieId);

    List<Map<String, Object>> findUnboundEnabledByJibieId(@Param("jibieId") Long jibieId);

    void insert(Map<String, Object> data);

    void update(Map<String, Object> data);

    void changeStatus(@Param("id") Long id, @Param("zhuangtai") String zhuangtai);

    void deleteLogic(@Param("id") Long id);

    void deleteJibieBindings(@Param("xiangmuId") Long xiangmuId);
}


