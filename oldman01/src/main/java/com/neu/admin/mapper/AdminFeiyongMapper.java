package com.neu.admin.mapper;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AdminFeiyongMapper {

    List<Map<String, Object>> findZhangdanList(@Param("laorenXingming") String laorenXingming,
                                               @Param("zhangdanYuefen") String zhangdanYuefen,
                                               @Param("zhuangtai") String zhuangtai);

    Map<String, Object> findZhangdanById(@Param("id") Long id);

    void insertZhangdan(Map<String, Object> data);

    void updateZhangdan(Map<String, Object> data);

    void cancelZhangdan(@Param("id") Long id);

    BigDecimal sumJiaofeiByZhangdanId(@Param("zhangdanId") Long zhangdanId);

    List<Map<String, Object>> findJiaofeiList(@Param("zhangdanId") Long zhangdanId,
                                              @Param("laorenXingming") String laorenXingming,
                                              @Param("zhangdanYuefen") String zhangdanYuefen);

    Map<String, Object> findJiaofeiById(@Param("id") Long id);

    void insertJiaofei(Map<String, Object> data);

    void deleteJiaofei(@Param("id") Long id);

    void updateZhangdanZhuangtai(@Param("id") Long id, @Param("zhuangtai") String zhuangtai);
}
