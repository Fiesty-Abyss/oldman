package com.oldman.huliguanli.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HuliJibieMapper {

    List<Map<String, Object>> findList(@Param("jibieMingcheng") String jibieMingcheng,
                                       @Param("zhuangtai") String zhuangtai);

    Map<String, Object> findById(@Param("id") Long id);

    void insert(Map<String, Object> data);

    void update(Map<String, Object> data);

    void changeStatus(@Param("id") Long id, @Param("zhuangtai") String zhuangtai);

    void deleteLogic(@Param("id") Long id);

    void bindXiangmu(@Param("jibieId") Long jibieId, @Param("xiangmuId") Long xiangmuId);

    void unbindXiangmu(@Param("jibieId") Long jibieId, @Param("xiangmuId") Long xiangmuId);

    void deleteXiangmuBindings(@Param("jibieId") Long jibieId);
}


