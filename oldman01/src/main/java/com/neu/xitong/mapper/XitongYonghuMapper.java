package com.neu.xitong.mapper;

import com.neu.xitong.entity.XitongYonghu;
import org.apache.ibatis.annotations.Param;

public interface XitongYonghuMapper {

    XitongYonghu findByDengluZhanghao(@Param("dengluZhanghao") String dengluZhanghao);

    XitongYonghu findById(@Param("id") Long id);

    void updateZuihouDengluShijian(@Param("id") Long id);
}


