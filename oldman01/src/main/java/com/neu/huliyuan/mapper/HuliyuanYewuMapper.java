package com.neu.huliyuan.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HuliyuanYewuMapper {

    Map<String, Object> findHuliRenyuanByYonghuId(@Param("yonghuId") Long yonghuId);

    int countFuwuLaoren(@Param("huliRenyuanId") Long huliRenyuanId, @Param("laorenId") Long laorenId);

    int countWodeLaoren(@Param("huliRenyuanId") Long huliRenyuanId);

    int countJinriHuliJilu(@Param("huliRenyuanId") Long huliRenyuanId);

    int countJinriJiankangJiance(@Param("huliRenyuanId") Long huliRenyuanId);

    int countJinriFuyao(@Param("huliRenyuanId") Long huliRenyuanId);

    int countWoTijiaoDaishenpiWaichu(@Param("huliRenyuanId") Long huliRenyuanId);

    int countWoTijiaoDaishenpiTuizhu(@Param("huliRenyuanId") Long huliRenyuanId);

    List<Map<String, Object>> findLaorenList(@Param("huliRenyuanId") Long huliRenyuanId,
                                             @Param("laorenXingming") String laorenXingming);

    Map<String, Object> findLaorenDetail(@Param("huliRenyuanId") Long huliRenyuanId,
                                         @Param("laorenId") Long laorenId);

    List<Map<String, Object>> findLaorenHuliXiangmuList(@Param("huliRenyuanId") Long huliRenyuanId,
                                                       @Param("laorenId") Long laorenId);

    int countLaorenHuliXiangmu(@Param("huliRenyuanId") Long huliRenyuanId,
                               @Param("laorenId") Long laorenId,
                               @Param("xiangmuId") Long xiangmuId);

    List<Map<String, Object>> findHuliJiluList(@Param("huliRenyuanId") Long huliRenyuanId,
                                               @Param("laorenId") Long laorenId);

    void insertHuliJilu(Map<String, Object> data);

    void deleteHuliJilu(@Param("id") Long id, @Param("huliRenyuanId") Long huliRenyuanId);

    List<Map<String, Object>> findJiankangJianceList(@Param("huliRenyuanId") Long huliRenyuanId,
                                                     @Param("laorenId") Long laorenId);

    void insertJiankangJiance(Map<String, Object> data);

    List<Map<String, Object>> findYongyaoJihuaList(@Param("huliRenyuanId") Long huliRenyuanId,
                                                  @Param("laorenId") Long laorenId);

    Map<String, Object> findYongyaoJihuaForHuli(@Param("huliRenyuanId") Long huliRenyuanId,
                                               @Param("jihuaId") Long jihuaId);

    List<Map<String, Object>> findFuyaoJiluList(@Param("huliRenyuanId") Long huliRenyuanId,
                                                @Param("laorenId") Long laorenId);

    void insertFuyaoJilu(Map<String, Object> data);

    List<Map<String, Object>> findWaichuList(@Param("huliRenyuanId") Long huliRenyuanId,
                                             @Param("laorenId") Long laorenId);

    void insertWaichu(Map<String, Object> data);

    List<Map<String, Object>> findTuizhuList(@Param("huliRenyuanId") Long huliRenyuanId,
                                             @Param("laorenId") Long laorenId);

    void insertTuizhu(Map<String, Object> data);
}


