package com.oldman.chuangwei.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ChuangweiMapper {

    List<Map<String, Object>> findFangjianChuangweiList();

    List<Map<String, Object>> findFangjianList();

    List<Map<String, Object>> findFangjianOptions();

    void insertFangjian(Map<String, Object> data);

    void updateFangjian(Map<String, Object> data);

    int countNormalChuangweiByFangjianId(@Param("fangjianId") Long fangjianId);

    void deleteFangjianLogic(@Param("id") Long id);

    List<Map<String, Object>> findChuangweiBaseList();

    List<Map<String, Object>> findKongxianOptions(@Param("fangjianId") Long fangjianId);

    int countKongxianChuangwei(@Param("id") Long id);

    Map<String, Object> findChuangweiBaseById(@Param("id") Long id);

    void insertChuangwei(Map<String, Object> data);

    void updateChuangwei(Map<String, Object> data);

    void deleteChuangweiLogic(@Param("id") Long id);

    void refreshFangjianChuangweiShu(@Param("fangjianId") Long fangjianId);

    void updateChuangweiZhuangtai(@Param("id") Long id, @Param("zhuangtai") String zhuangtai);

    void insertChuangweiShiyong(Map<String, Object> data);

    void endCurrentChuangweiShiyong(@Param("laorenId") Long laorenId, @Param("yuanyin") String yuanyin);

    Map<String, Object> findCurrentChuangweiByLaorenId(@Param("laorenId") Long laorenId);
}


