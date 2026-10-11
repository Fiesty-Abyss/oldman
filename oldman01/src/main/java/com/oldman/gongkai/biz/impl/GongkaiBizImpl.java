package com.oldman.gongkai.biz.impl;

import com.oldman.gongkai.biz.GongkaiBiz;
import com.oldman.gongkai.mapper.GongkaiMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class GongkaiBizImpl implements GongkaiBiz {

    private static final DateTimeFormatter BIANHAO_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final GongkaiMapper gongkaiMapper;

    public GongkaiBizImpl(GongkaiMapper gongkaiMapper) {
        this.gongkaiMapper = gongkaiMapper;
    }

    @Override
    @Transactional
    public void jiashuZhuce(Map<String, Object> data) {
        requireText(data, "dengluZhanghao", "登录账号不能为空");
        requireText(data, "mima", "登录密码不能为空");
        requireText(data, "jiashuXingming", "家属姓名不能为空");
        requireText(data, "shouji", "手机号不能为空");
        requireText(data, "guanxi", "与老人关系不能为空");
        requireText(data, "laorenXingming", "老人姓名不能为空");

        String zhanghao = text(data.get("dengluZhanghao"));
        String shouji = text(data.get("shouji"));
        if (gongkaiMapper.countYonghuByZhanghao(zhanghao) > 0) {
            throw new IllegalArgumentException("登录账号已存在");
        }
        if (gongkaiMapper.countPhone(shouji) > 0) {
            throw new IllegalArgumentException("手机号已存在");
        }

        data.put("laorenBianhao", buildLaorenBianhao());
        data.putIfAbsent("xingbie", "未填写");
        data.put("laorenLeixing", "待完善");
        data.put("ruzhuZhuangtai", "未入住");
        blankToNull(data, List.of("chushengRiqi", "nianling", "shenfenzhenghao", "xuexing", "lianxiDianhua", "jiatingZhuzhi", "beizhu"));
        gongkaiMapper.insertLaoren(data);
        Long laorenId = toLong(data.get("id"));

        gongkaiMapper.insertYonghu(data);
        Long yonghuId = toLong(data.get("yonghuId"));
        Long jiashuJueseId = gongkaiMapper.findJueseIdByBianma("jiashu");
        if (jiashuJueseId == null) {
            throw new IllegalArgumentException("系统缺少家属角色，请先维护角色");
        }
        gongkaiMapper.insertYonghuJuese(yonghuId, jiashuJueseId);

        data.put("laorenId", laorenId);
        data.putIfAbsent("dizhi", data.get("lianxiDizhi"));
        gongkaiMapper.insertJiashu(data);
    }

    @Override
    public void ruzhuShenqingAdd(Map<String, Object> data) {
        requireText(data, "laorenXingming", "老人姓名不能为空");
        requireText(data, "jiashuXingming", "家属姓名不能为空");
        requireText(data, "jiashuShouji", "家属手机号不能为空");
        blankToNull(data, List.of(
                "xingbie", "chushengRiqi", "nianling", "shenfenzhenghao", "lianxiDianhua", "jiatingZhuzhi",
                "xuexing", "guominshi", "jiwangbingshi", "huliXuqiu", "qiwangRuzhuShijian",
                "yuLaorenGuanxi", "beizhu"
        ));
        gongkaiMapper.insertRuzhuShenqing(data);
    }

    @Override
    public void zanzhuYixiangAdd(Map<String, Object> data) {
        requireText(data, "xingming", "赞助人姓名不能为空");
        requireText(data, "lianxiDianhua", "联系电话不能为空");
        requireText(data, "zanzhuFangshi", "赞助方式不能为空");
        blankToNull(data, List.of("zanzhuShuoming", "beizhu"));
        gongkaiMapper.insertZanzhuYixiang(data);
    }

    private String buildLaorenBianhao() {
        return "LR" + BIANHAO_FORMAT.format(LocalDateTime.now());
    }

    private void requireText(Map<String, Object> data, String key, String message) {
        if (!StringUtils.hasText(text(data.get(key)))) {
            throw new IllegalArgumentException(message);
        }
    }

    private void blankToNull(Map<String, Object> data, List<String> keys) {
        for (String key : keys) {
            if (data.containsKey(key) && !StringUtils.hasText(text(data.get(key)))) {
                data.put(key, null);
            }
        }
    }

    private String text(Object value) {
        return value == null ? "" : value.toString().trim();
    }

    private Long toLong(Object value) {
        if (value == null || value.toString().isBlank()) {
            return null;
        }
        return Long.valueOf(value.toString());
    }
}
