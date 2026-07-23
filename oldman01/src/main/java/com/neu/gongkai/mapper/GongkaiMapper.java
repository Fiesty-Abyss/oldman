package com.neu.gongkai.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface GongkaiMapper {

    int countYonghuByZhanghao(@Param("dengluZhanghao") String dengluZhanghao);

    int countPhone(@Param("shouji") String shouji);

    Long findJueseIdByBianma(@Param("jueseBianma") String jueseBianma);

    void insertLaoren(Map<String, Object> data);

    void insertYonghu(Map<String, Object> data);

    void insertYonghuJuese(@Param("yonghuId") Long yonghuId, @Param("jueseId") Long jueseId);

    void insertJiashu(Map<String, Object> data);

    void insertRuzhuShenqing(Map<String, Object> data);

    void insertZanzhuYixiang(Map<String, Object> data);
}
