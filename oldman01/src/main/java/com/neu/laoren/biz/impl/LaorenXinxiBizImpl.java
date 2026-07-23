package com.neu.laoren.biz.impl;


import com.neu.laoren.biz.LaorenXinxiBiz;
import com.neu.common.auth.DangqianYonghu;
import com.neu.chuangwei.mapper.ChuangweiMapper;
import com.neu.laoren.entity.LaorenXinxi;
import com.neu.laoren.mapper.LaorenXinxiMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class LaorenXinxiBizImpl implements LaorenXinxiBiz {

    private final LaorenXinxiMapper laorenXinxiMapper;
    private final ChuangweiMapper chuangweiMapper;

    public LaorenXinxiBizImpl(LaorenXinxiMapper laorenXinxiMapper, ChuangweiMapper chuangweiMapper) {
        this.laorenXinxiMapper = laorenXinxiMapper;
        this.chuangweiMapper = chuangweiMapper;
    }

    @Override
    public List<LaorenXinxi> list(DangqianYonghu dangqianYonghu) {
        if (dangqianYonghu.hasJuese("admin")) {
            return laorenXinxiMapper.findAll();
        }

        if (dangqianYonghu.hasJuese("huli")) {
            return laorenXinxiMapper.findByHuliYonghuId(dangqianYonghu.yonghuId());
        }

        if (dangqianYonghu.hasJuese("jiashu")) {
            return laorenXinxiMapper.findByJiashuYonghuId(dangqianYonghu.yonghuId());
        }

        throw new IllegalArgumentException("当前角色无权查看老人信息");
    }

    @Override
    public List<Map<String, Object>> options() {
        return laorenXinxiMapper.findOptions();
    }

    @Override
    @Transactional
    public void add(Map<String, Object> data) {
        laorenXinxiMapper.insert(data);
        Object jiashuXingming = data.get("jiashuXingming");
        if (jiashuXingming != null && !jiashuXingming.toString().isBlank()) {
            data.put("laorenId", data.get("id"));
            laorenXinxiMapper.insertJiashu(data);
        }
    }

    @Override
    public void update(Map<String, Object> data) {
        laorenXinxiMapper.update(data);
    }

    @Override
    public void delete(Long id) {
        laorenXinxiMapper.deleteLogic(id);
    }

    @Override
    @Transactional
    public void ruzhu(Map<String, Object> data, DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以办理入住登记");
        }
        requireLong(data, "laorenId");
        requireLong(data, "chuangweiId");
        requireText(data, "ruzhuShijian", "入住日期不能为空");
        Long shenqingId = toLong(data.get("shenqingId"));
        Long laorenId = Long.valueOf(data.get("laorenId").toString());
        Map<String, Object> laoren = laorenXinxiMapper.findBasicById(laorenId);
        if (laoren == null) {
            throw new IllegalArgumentException("老人不存在");
        }
        String ruzhuZhuangtai = String.valueOf(laoren.get("ruzhuZhuangtai"));
        if ("在住".equals(ruzhuZhuangtai) || "外出".equals(ruzhuZhuangtai) || "退住".equals(ruzhuZhuangtai) || "已退住".equals(ruzhuZhuangtai)) {
            throw new IllegalArgumentException("该老人当前状态不能重复办理入住");
        }
        Long chuangweiId = Long.valueOf(data.get("chuangweiId").toString());
        Map<String, Object> chuangwei = chuangweiMapper.findChuangweiBaseById(chuangweiId);
        if (chuangwei == null || !"空闲".equals(String.valueOf(chuangwei.get("zhuangtai")))) {
            throw new IllegalArgumentException("请选择空闲床位");
        }
        data.put("fangjianId", chuangwei.get("fangjianId"));
        if (shenqingId != null && laorenXinxiMapper.countKeBanliRuzhuShenqing(shenqingId, laorenId) == 0) {
            throw new IllegalArgumentException("该入住申请不可办理或已办理");
        }
        data.put("jingbanrenId", dangqianYonghu.yonghuId());
        laorenXinxiMapper.insertRuzhuDengji(data);
        data.put("kaishiRiqi", data.get("ruzhuShijian"));
        data.put("yuanyin", "入住分配");
        chuangweiMapper.insertChuangweiShiyong(data);
        chuangweiMapper.updateChuangweiZhuangtai(Long.valueOf(data.get("chuangweiId").toString()), "有人");
        laorenXinxiMapper.updateLaorenRuzhuZhuangtai(laorenId, "在住");
        if (shenqingId != null) {
            laorenXinxiMapper.updateRuzhuShenqingBanli(shenqingId);
        }
    }

    private Long toLong(Object value) {
        if (value == null || value.toString().isBlank()) {
            return null;
        }
        return Long.valueOf(value.toString());
    }

    private Long requireLong(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("缺少必要参数：" + key);
        }
        return Long.valueOf(value.toString());
    }

    private void requireText(Map<String, Object> data, String key, String message) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}


