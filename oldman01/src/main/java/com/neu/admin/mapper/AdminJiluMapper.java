package com.neu.admin.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminJiluMapper {

    List<Map<String, Object>> findHuliList(@Param("laorenXingming") String laorenXingming,
                                           @Param("huliRenyuanXingming") String huliRenyuanXingming,
                                           @Param("xiangmuMingcheng") String xiangmuMingcheng,
                                           @Param("riqi") String riqi);

    void deleteHuliJilu(@Param("id") Long id);

    List<Map<String, Object>> findJiankangList(@Param("laorenXingming") String laorenXingming,
                                               @Param("riqi") String riqi);

    List<Map<String, Object>> findFuyaoList(@Param("laorenXingming") String laorenXingming,
                                            @Param("yaopinMingcheng") String yaopinMingcheng,
                                            @Param("zhixingZhuangtai") String zhixingZhuangtai,
                                            @Param("riqi") String riqi);
}
