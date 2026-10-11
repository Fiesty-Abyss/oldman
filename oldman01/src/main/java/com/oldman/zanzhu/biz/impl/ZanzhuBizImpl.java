package com.oldman.zanzhu.biz.impl;


import com.oldman.zanzhu.biz.ZanzhuBiz;
import com.oldman.zanzhu.mapper.ZanzhuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ZanzhuBizImpl implements ZanzhuBiz {

    private final ZanzhuMapper zanzhuMapper;

    public ZanzhuBizImpl(ZanzhuMapper zanzhuMapper) {
        this.zanzhuMapper = zanzhuMapper;
    }

    @Override
    public List<Map<String, Object>> gongkaiList() {
        return zanzhuMapper.gongkaiList();
    }

    @Override
    public List<Map<String, Object>> yongtuList() {
        return zanzhuMapper.yongtuList();
    }

    @Override
    public List<Map<String, Object>> wuziList() {
        return zanzhuMapper.wuziList();
    }
}


