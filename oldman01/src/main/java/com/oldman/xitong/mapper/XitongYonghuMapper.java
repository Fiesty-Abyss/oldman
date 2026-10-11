package com.oldman.xitong.mapper;

import com.oldman.xitong.entity.XitongYonghu;
import org.apache.ibatis.annotations.Param;

public interface XitongYonghuMapper {

    XitongYonghu findByDengluZhanghao(@Param("dengluZhanghao") String dengluZhanghao);

    XitongYonghu findById(@Param("id") Long id);

    void updateZuihouDengluShijian(@Param("id") Long id);
}


