package com.neu.xitong.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface XitongGuanliMapper {

    List<Map<String, Object>> findYonghuList(@Param("dengluZhanghao") String dengluZhanghao,
                                             @Param("xingming") String xingming,
                                             @Param("jueseBianma") String jueseBianma,
                                             @Param("zhuangtai") String zhuangtai);

    Map<String, Object> findYonghuById(@Param("id") Long id);

    int countYonghuByZhanghao(@Param("dengluZhanghao") String dengluZhanghao,
                              @Param("excludeId") Long excludeId);

    void insertYonghu(Map<String, Object> data);

    void updateYonghu(Map<String, Object> data);

    void updateYonghuStatus(@Param("id") Long id, @Param("zhuangtai") String zhuangtai);

    void updateYonghuPassword(@Param("id") Long id, @Param("mima") String mima);

    List<Map<String, Object>> findYonghuRole(@Param("yonghuId") Long yonghuId);

    void deleteYonghuRole(@Param("yonghuId") Long yonghuId);

    void insertYonghuRole(@Param("yonghuId") Long yonghuId, @Param("jueseId") Long jueseId);

    List<Map<String, Object>> findJueseList();

    Map<String, Object> findJueseById(@Param("id") Long id);

    int countJueseByBianma(@Param("jueseBianma") String jueseBianma,
                           @Param("excludeId") Long excludeId);

    void insertJuese(Map<String, Object> data);

    void updateJuese(Map<String, Object> data);

    List<Map<String, Object>> findHuliRenyuanOptions();

    List<Map<String, Object>> findJiashuOptions();

    Map<String, Object> findHuliRenyuanById(@Param("id") Long id);

    Map<String, Object> findJiashuById(@Param("id") Long id);

    void unbindHuliRenyuanByYonghuId(@Param("yonghuId") Long yonghuId);

    void bindHuliRenyuan(@Param("id") Long id, @Param("yonghuId") Long yonghuId);
}
