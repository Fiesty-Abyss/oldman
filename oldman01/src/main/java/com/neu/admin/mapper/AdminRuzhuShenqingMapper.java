package com.neu.admin.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminRuzhuShenqingMapper {

    List<Map<String, Object>> findList(@Param("laorenXingming") String laorenXingming,
                                       @Param("jiashuShouji") String jiashuShouji,
                                       @Param("shenqingZhuangtai") String shenqingZhuangtai);

    Map<String, Object> findById(@Param("id") Long id);

    List<Map<String, Object>> findPassedUnbanliList(@Param("laorenXingming") String laorenXingming,
                                                    @Param("jiashuShouji") String jiashuShouji);

    String findYonghuXingming(@Param("id") Long id);

    void updateShenhe(@Param("id") Long id,
                      @Param("shenqingZhuangtai") String shenqingZhuangtai,
                      @Param("shenheRenId") Long shenheRenId,
                      @Param("shenheRenXingming") String shenheRenXingming,
                      @Param("shenheYijian") String shenheYijian);

    void updateLaorenId(@Param("id") Long id, @Param("laorenId") Long laorenId);

    Long findLaorenIdByShenfenzheng(@Param("shenfenzheng") String shenfenzheng);

    Long findLaorenIdByNameAndPhone(@Param("laorenXingming") String laorenXingming,
                                    @Param("lianxiDianhua") String lianxiDianhua);

    void insertLaoren(Map<String, Object> shenqing);

    void updateLaorenBase(@Param("laorenId") Long laorenId, @Param("shenqing") Map<String, Object> shenqing);

    Long findJiashuId(@Param("laorenId") Long laorenId, @Param("shouji") String shouji);

    void insertJiashu(@Param("laorenId") Long laorenId, @Param("shenqing") Map<String, Object> shenqing);

    void updateJiashu(@Param("jiashuId") Long jiashuId, @Param("shenqing") Map<String, Object> shenqing);

    void deleteLogic(@Param("id") Long id);
}
