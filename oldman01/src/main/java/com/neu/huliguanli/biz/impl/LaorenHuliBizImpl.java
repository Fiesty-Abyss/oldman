package com.neu.huliguanli.biz.impl;


import com.neu.huliguanli.biz.LaorenHuliBiz;
import com.neu.huliguanli.mapper.HuliJibieMapper;
import com.neu.huliguanli.mapper.LaorenHuliMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LaorenHuliBizImpl implements LaorenHuliBiz {

    private final LaorenHuliMapper laorenHuliMapper;
    private final HuliJibieMapper huliJibieMapper;

    public LaorenHuliBizImpl(LaorenHuliMapper laorenHuliMapper, HuliJibieMapper huliJibieMapper) {
        this.laorenHuliMapper = laorenHuliMapper;
        this.huliJibieMapper = huliJibieMapper;
    }

    @Override
    public List<Map<String, Object>> laorenList() {
        return laorenHuliMapper.findLaorenList();
    }

    @Override
    public Map<String, Object> current(Long laorenId) {
        return laorenHuliMapper.findCurrentByLaorenId(laorenId);
    }

    @Override
    @Transactional
    public void setJibie(Map<String, Object> data) {
        Long laorenId = Long.valueOf(data.get("laorenId").toString());
        Long jibieId = Long.valueOf(data.get("jibieId").toString());
        Map<String, Object> jibie = huliJibieMapper.findById(jibieId);
        if (jibie == null || !"启用".equals(String.valueOf(jibie.get("zhuangtai")))) {
            throw new IllegalArgumentException("只能给老人设置启用状态的护理级别");
        }
        if (laorenHuliMapper.findCurrentByLaorenId(laorenId) != null) {
            throw new IllegalArgumentException("老人已有护理级别，请先移除旧级别后再设置");
        }
        laorenHuliMapper.insert(data);
        laorenHuliMapper.updateLaorenLeixing(laorenId, "护理老人");
    }

    @Override
    @Transactional
    public void remove(Long laorenId) {
        laorenHuliMapper.endCurrentByLaorenId(laorenId);
        laorenHuliMapper.updateLaorenLeixing(laorenId, "自理老人");
    }
}


