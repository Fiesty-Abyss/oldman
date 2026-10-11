package com.oldman.admin.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminLaorenXiugaiShenqingMapper {

    List<Map<String, Object>> findList(@Param("laorenXingming") String laorenXingming,
                                       @Param("jiashuXingming") String jiashuXingming,
                                       @Param("shenqingZhuangtai") String shenqingZhuangtai);

    Map<String, Object> findById(@Param("id") Long id);

    String findYonghuXingming(@Param("id") Long id);

    void updateLaorenXinxi(Map<String, Object> shenqing);

    void updateShenhe(@Param("id") Long id,
                      @Param("shenqingZhuangtai") String shenqingZhuangtai,
                      @Param("shenheRenId") Long shenheRenId,
                      @Param("shenheRenXingming") String shenheRenXingming,
                      @Param("shenheYijian") String shenheYijian);

    void deleteLogic(@Param("id") Long id);
}
