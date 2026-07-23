package com.neu.jiashu.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JiashuYewuMapper {

    int countBangdingLaoren(@Param("yonghuId") Long yonghuId, @Param("laorenId") Long laorenId);

    int countBangdingLaorenByYonghuId(@Param("yonghuId") Long yonghuId);

    int countZaizhuLaorenByYonghuId(@Param("yonghuId") Long yonghuId);

    int countZuijinHuliJilu(@Param("yonghuId") Long yonghuId);

    int countZuijinJiankangJiance(@Param("yonghuId") Long yonghuId);

    int countDaishenpiWaichu(@Param("yonghuId") Long yonghuId);

    int countDaishenpiTuizhu(@Param("yonghuId") Long yonghuId);

    Map<String, Object> findJiashuByYonghuIdAndLaorenId(@Param("yonghuId") Long yonghuId,
                                                        @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findLaorenXinxi(@Param("yonghuId") Long yonghuId);

    List<Map<String, Object>> findLaorenXiugaiShenqingList(@Param("yonghuId") Long yonghuId);

    Map<String, Object> findLaorenXiugaiBase(@Param("yonghuId") Long yonghuId,
                                             @Param("laorenId") Long laorenId);

    void insertLaorenXiugaiShenqing(Map<String, Object> data);

    List<Map<String, Object>> findChuangweiInfo(@Param("yonghuId") Long yonghuId,
                                                @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findHuliJiluList(@Param("yonghuId") Long yonghuId,
                                               @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findJiankangJianceList(@Param("yonghuId") Long yonghuId,
                                                     @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findYongyaoJihuaList(@Param("yonghuId") Long yonghuId,
                                                  @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findFuyaoJiluList(@Param("yonghuId") Long yonghuId,
                                                @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findFeiyongZhangdanList(@Param("yonghuId") Long yonghuId,
                                                      @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findJiaofeiJiluList(@Param("yonghuId") Long yonghuId,
                                                  @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findWaichuList(@Param("yonghuId") Long yonghuId,
                                             @Param("laorenId") Long laorenId);

    void insertWaichu(Map<String, Object> data);

    List<Map<String, Object>> findTuizhuList(@Param("yonghuId") Long yonghuId,
                                             @Param("laorenId") Long laorenId);

    void insertTuizhu(Map<String, Object> data);

    void updateLaorenTouxiang(@Param("laorenId") Long laorenId, @Param("touxiang") String touxiang);
}
