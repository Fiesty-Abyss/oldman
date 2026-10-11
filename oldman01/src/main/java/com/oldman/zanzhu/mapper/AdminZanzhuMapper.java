package com.oldman.zanzhu.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminZanzhuMapper {

    List<Map<String, Object>> findRenList(@Param("keyword") String keyword);

    void insertRen(Map<String, Object> data);

    void updateRen(Map<String, Object> data);

    void deleteRen(@Param("id") Long id);

    List<Map<String, Object>> findJiluList(@Param("keyword") String keyword);

    void insertJilu(Map<String, Object> data);

    void updateJilu(Map<String, Object> data);

    void deleteJilu(@Param("id") Long id);

    List<Map<String, Object>> findWuziList(@Param("keyword") String keyword);

    void insertWuzi(Map<String, Object> data);

    void updateWuzi(Map<String, Object> data);

    void deleteWuzi(@Param("id") Long id);

    List<Map<String, Object>> findYongtuList(@Param("keyword") String keyword);

    void insertYongtu(Map<String, Object> data);

    void updateYongtu(Map<String, Object> data);

    void deleteYongtu(@Param("id") Long id);

    List<Map<String, Object>> findYixiangList(@Param("keyword") String keyword,
                                              @Param("chuliZhuangtai") String chuliZhuangtai);

    Map<String, Object> findYixiangById(@Param("id") Long id);

    void updateYixiangStatus(Map<String, Object> data);

    void deleteYixiang(@Param("id") Long id);
}
