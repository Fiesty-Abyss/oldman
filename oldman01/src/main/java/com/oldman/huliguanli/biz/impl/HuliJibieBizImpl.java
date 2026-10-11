package com.oldman.huliguanli.biz.impl;


import com.oldman.huliguanli.biz.HuliJibieBiz;
import com.oldman.huliguanli.mapper.HuliJibieMapper;
import com.oldman.huliguanli.mapper.HuliXiangmuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class HuliJibieBizImpl implements HuliJibieBiz {

    private final HuliJibieMapper huliJibieMapper;
    private final HuliXiangmuMapper huliXiangmuMapper;

    public HuliJibieBizImpl(HuliJibieMapper huliJibieMapper, HuliXiangmuMapper huliXiangmuMapper) {
        this.huliJibieMapper = huliJibieMapper;
        this.huliXiangmuMapper = huliXiangmuMapper;
    }

    @Override
    public List<Map<String, Object>> list(String jibieMingcheng, String zhuangtai) {
        return huliJibieMapper.findList(jibieMingcheng, zhuangtai);
    }

    @Override
    public void add(Map<String, Object> data) {
        huliJibieMapper.insert(data);
    }

    @Override
    public void update(Map<String, Object> data) {
        huliJibieMapper.update(data);
    }

    @Override
    public void changeStatus(Map<String, Object> data) {
        Long id = Long.valueOf(data.get("id").toString());
        String zhuangtai = String.valueOf(data.get("zhuangtai"));
        huliJibieMapper.changeStatus(id, zhuangtai);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        huliJibieMapper.deleteXiangmuBindings(id);
        huliJibieMapper.deleteLogic(id);
    }

    @Override
    public List<Map<String, Object>> xiangmuList(Long jibieId) {
        return huliXiangmuMapper.findBoundByJibieId(jibieId);
    }

    @Override
    public List<Map<String, Object>> unboundXiangmuList(Long jibieId) {
        return huliXiangmuMapper.findUnboundEnabledByJibieId(jibieId);
    }

    @Override
    public void bindXiangmu(Map<String, Object> data) {
        Long jibieId = Long.valueOf(data.get("jibieId").toString());
        Long xiangmuId = Long.valueOf(data.get("xiangmuId").toString());
        Map<String, Object> jibie = huliJibieMapper.findById(jibieId);
        if (jibie == null || !"启用".equals(String.valueOf(jibie.get("zhuangtai")))) {
            throw new IllegalArgumentException("只能给启用状态的护理级别绑定项目");
        }
        boolean xiangmuKeBangding = huliXiangmuMapper.findUnboundEnabledByJibieId(jibieId).stream()
                .anyMatch(item -> Objects.equals(String.valueOf(item.get("id")), String.valueOf(xiangmuId)));
        if (!xiangmuKeBangding) {
            throw new IllegalArgumentException("只能绑定启用且未绑定的护理项目");
        }
        huliJibieMapper.bindXiangmu(jibieId, xiangmuId);
    }

    @Override
    public void unbindXiangmu(Long jibieId, Long xiangmuId) {
        huliJibieMapper.unbindXiangmu(jibieId, xiangmuId);
    }
}


