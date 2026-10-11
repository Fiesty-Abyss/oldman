package com.oldman.huli.biz.impl;


import com.oldman.huli.biz.HuliRenyuanBiz;
import com.oldman.huli.mapper.HuliRenyuanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class HuliRenyuanBizImpl implements HuliRenyuanBiz {

    private final HuliRenyuanMapper huliRenyuanMapper;

    public HuliRenyuanBizImpl(HuliRenyuanMapper huliRenyuanMapper) {
        this.huliRenyuanMapper = huliRenyuanMapper;
    }

    @Override
    public List<Map<String, Object>> list() {
        return huliRenyuanMapper.findAll();
    }

    @Override
    public void add(Map<String, Object> data) {
        huliRenyuanMapper.insert(data);
    }

    @Override
    public void update(Map<String, Object> data) {
        huliRenyuanMapper.update(data);
    }

    @Override
    public void delete(Long id) {
        huliRenyuanMapper.deleteLogic(id);
    }

    @Override
    public List<Map<String, Object>> fuwuLaoren(Long huliRenyuanId) {
        return huliRenyuanMapper.findFuwuLaoren(huliRenyuanId);
    }

    @Override
    public List<Map<String, Object>> unassignedLaorenOptions(Long huliRenyuanId, String laorenXingming) {
        return huliRenyuanMapper.findUnassignedLaorenOptions(huliRenyuanId, laorenXingming);
    }

    @Override
    @Transactional
    public void assignLaoren(Map<String, Object> data) {
        Long huliRenyuanId = Long.valueOf(data.get("huliRenyuanId").toString());
        Long laorenId = Long.valueOf(data.get("laorenId").toString());
        if (huliRenyuanMapper.countServingAssignment(huliRenyuanId, laorenId) > 0) {
            throw new IllegalArgumentException("该老人已经分配给当前护理员");
        }
        huliRenyuanMapper.assignLaoren(data);
    }

    @Override
    public void removeLaoren(Long huliRenyuanId, Long laorenId) {
        huliRenyuanMapper.endLaorenAssignment(huliRenyuanId, laorenId);
    }
}


