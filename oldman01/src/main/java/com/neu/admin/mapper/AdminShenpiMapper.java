package com.neu.admin.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminShenpiMapper {

    List<Map<String, Object>> findWaichuList(@Param("laorenXingming") String laorenXingming,
                                             @Param("shenpiZhuangtai") String shenpiZhuangtai);

    Map<String, Object> findWaichuById(@Param("id") Long id);

    void updateWaichuShenpi(@Param("id") Long id,
                            @Param("shenpiZhuangtai") String shenpiZhuangtai,
                            @Param("shenpiRenId") Long shenpiRenId,
                            @Param("shenpiYijian") String shenpiYijian);

    void updateWaichuHuiyuan(@Param("id") Long id);

    void deleteWaichuLogic(@Param("id") Long id);

    List<Map<String, Object>> findTuizhuList(@Param("laorenXingming") String laorenXingming,
                                             @Param("shenpiZhuangtai") String shenpiZhuangtai);

    Map<String, Object> findTuizhuById(@Param("id") Long id);

    void updateTuizhuShenpi(@Param("id") Long id,
                            @Param("shenpiZhuangtai") String shenpiZhuangtai,
                            @Param("shenpiRenId") Long shenpiRenId,
                            @Param("shenpiYijian") String shenpiYijian);

    void deleteTuizhuLogic(@Param("id") Long id);

    Map<String, Object> findCurrentChuangweiByLaorenId(@Param("laorenId") Long laorenId);

    void updateChuangweiZhuangtai(@Param("chuangweiId") Long chuangweiId,
                                  @Param("zhuangtai") String zhuangtai);

    void endCurrentChuangweiShiyong(@Param("laorenId") Long laorenId,
                                    @Param("yuanyin") String yuanyin);

    void updateLaorenRuzhuZhuangtai(@Param("laorenId") Long laorenId,
                                    @Param("ruzhuZhuangtai") String ruzhuZhuangtai);
}
