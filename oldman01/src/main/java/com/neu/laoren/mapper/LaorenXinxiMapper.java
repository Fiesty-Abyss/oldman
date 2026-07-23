package com.neu.laoren.mapper;

import com.neu.laoren.entity.LaorenXinxi;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LaorenXinxiMapper {

    List<LaorenXinxi> findAll();

    List<LaorenXinxi> findByHuliYonghuId(@Param("yonghuId") Long yonghuId);

    List<LaorenXinxi> findByJiashuYonghuId(@Param("yonghuId") Long yonghuId);

    List<Map<String, Object>> findOptions();

    Map<String, Object> findBasicById(@Param("id") Long id);

    void insert(Map<String, Object> data);

    void update(Map<String, Object> data);

    void deleteLogic(@Param("id") Long id);

    void insertJiashu(Map<String, Object> data);

    void insertRuzhuDengji(Map<String, Object> data);

    void updateLaorenRuzhuZhuangtai(@Param("id") Long id, @Param("zhuangtai") String zhuangtai);

    int countKeBanliRuzhuShenqing(@Param("shenqingId") Long shenqingId, @Param("laorenId") Long laorenId);

    void updateRuzhuShenqingBanli(@Param("shenqingId") Long shenqingId);
}


