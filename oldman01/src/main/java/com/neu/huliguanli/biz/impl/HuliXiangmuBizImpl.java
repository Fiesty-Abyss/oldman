package com.neu.huliguanli.biz.impl;


import com.neu.huliguanli.biz.HuliXiangmuBiz;
import com.neu.huliguanli.mapper.HuliXiangmuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class HuliXiangmuBizImpl implements HuliXiangmuBiz {

    private final HuliXiangmuMapper huliXiangmuMapper;

    public HuliXiangmuBizImpl(HuliXiangmuMapper huliXiangmuMapper) {
        this.huliXiangmuMapper = huliXiangmuMapper;
    }

    @Override
    public List<Map<String, Object>> list(String xiangmuMingcheng, String zhuangtai) {
        return huliXiangmuMapper.findList(xiangmuMingcheng, zhuangtai);
    }

    @Override
    public void add(Map<String, Object> data) {
        huliXiangmuMapper.insert(data);
    }

    @Override
    public void update(Map<String, Object> data) {
        huliXiangmuMapper.update(data);
    }

    @Override
    public void changeStatus(Map<String, Object> data) {
        Long id = Long.valueOf(data.get("id").toString());
        String zhuangtai = String.valueOf(data.get("zhuangtai"));
        huliXiangmuMapper.changeStatus(id, zhuangtai);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        huliXiangmuMapper.deleteJibieBindings(id);
        huliXiangmuMapper.deleteLogic(id);
    }
}


