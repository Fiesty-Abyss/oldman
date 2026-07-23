package com.neu.yaopin.biz.impl;

import com.neu.common.auth.DangqianYonghu;
import com.neu.yaopin.biz.YaopinBiz;
import com.neu.yaopin.mapper.YaopinMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class YaopinBizImpl implements YaopinBiz {

    private static final DateTimeFormatter BIANHAO_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final YaopinMapper yaopinMapper;

    public YaopinBizImpl(YaopinMapper yaopinMapper) {
        this.yaopinMapper = yaopinMapper;
    }

    @Override
    public List<Map<String, Object>> list(DangqianYonghu dangqianYonghu, String yaopinMingcheng) {
        requireAdmin(dangqianYonghu);
        return yaopinMapper.findList(yaopinMingcheng);
    }

    @Override
    public void add(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        String mingcheng = text(data.get("yaopinMingcheng"));
        if (mingcheng.isEmpty()) {
            throw new IllegalArgumentException("药品名称不能为空");
        }
        Integer kucun = integerValue(data.get("kucun"));
        if (kucun < 0) {
            throw new IllegalArgumentException("库存数量不能为负数");
        }
        data.put("yaopinBianhao", "YP" + BIANHAO_FORMAT.format(LocalDateTime.now()));
        data.put("yaopinMingcheng", mingcheng);
        data.put("danwei", text(data.get("danwei")).isEmpty() ? "盒" : text(data.get("danwei")));
        data.put("kucun", kucun);
        data.put("zhuangtai", "启用");
        yaopinMapper.insert(data);
    }

    @Override
    public void delete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        yaopinMapper.disable(id);
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以管理药品");
        }
    }

    private Integer integerValue(Object value) {
        if (value == null || value.toString().isBlank()) {
            return 0;
        }
        return Integer.valueOf(value.toString());
    }

    private String text(Object value) {
        return value == null ? "" : value.toString().trim();
    }
}
