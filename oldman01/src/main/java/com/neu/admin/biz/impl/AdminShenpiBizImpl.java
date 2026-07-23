package com.neu.admin.biz.impl;

import com.neu.admin.biz.AdminShenpiBiz;
import com.neu.admin.mapper.AdminShenpiMapper;
import com.neu.common.auth.DangqianYonghu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AdminShenpiBizImpl implements AdminShenpiBiz {

    private final AdminShenpiMapper adminShenpiMapper;

    public AdminShenpiBizImpl(AdminShenpiMapper adminShenpiMapper) {
        this.adminShenpiMapper = adminShenpiMapper;
    }

    @Override
    public List<Map<String, Object>> waichuList(DangqianYonghu dangqianYonghu, String laorenXingming, String shenpiZhuangtai) {
        requireAdmin(dangqianYonghu);
        return adminShenpiMapper.findWaichuList(laorenXingming, shenpiZhuangtai);
    }

    @Override
    @Transactional
    public void waichuPass(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        Map<String, Object> shenqing = requireWaichu(id);
        adminShenpiMapper.updateWaichuShenpi(id, "通过", dangqianYonghu.yonghuId(), stringValue(data.get("shenpiYijian")));
        Long laorenId = Long.valueOf(shenqing.get("laorenId").toString());
        Map<String, Object> chuangwei = adminShenpiMapper.findCurrentChuangweiByLaorenId(laorenId);
        if (chuangwei != null) {
            adminShenpiMapper.updateChuangweiZhuangtai(Long.valueOf(chuangwei.get("chuangweiId").toString()), "外出");
        }
        adminShenpiMapper.updateLaorenRuzhuZhuangtai(laorenId, "外出");
    }

    @Override
    public void waichuReject(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        requireWaichu(id);
        adminShenpiMapper.updateWaichuShenpi(id, "不通过", dangqianYonghu.yonghuId(), stringValue(data.get("shenpiYijian")));
    }

    @Override
    @Transactional
    public void waichuHuiyuan(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        Map<String, Object> shenqing = requireWaichu(id);
        Long laorenId = Long.valueOf(shenqing.get("laorenId").toString());
        adminShenpiMapper.updateWaichuHuiyuan(id);
        Map<String, Object> chuangwei = adminShenpiMapper.findCurrentChuangweiByLaorenId(laorenId);
        if (chuangwei != null) {
            adminShenpiMapper.updateChuangweiZhuangtai(Long.valueOf(chuangwei.get("chuangweiId").toString()), "有人");
        }
        adminShenpiMapper.updateLaorenRuzhuZhuangtai(laorenId, "在住");
    }

    @Override
    public void waichuDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        Map<String, Object> shenqing = requireWaichu(id);
        if ("通过".equals(stringValue(shenqing.get("shenpiZhuangtai")))) {
            Long laorenId = Long.valueOf(shenqing.get("laorenId").toString());
            Map<String, Object> chuangwei = adminShenpiMapper.findCurrentChuangweiByLaorenId(laorenId);
            if (chuangwei != null && "外出".equals(String.valueOf(chuangwei.get("chuangweiZhuangtai")))) {
                throw new IllegalArgumentException("请先登记回院后再删除记录");
            }
        }
        adminShenpiMapper.deleteWaichuLogic(id);
    }

    @Override
    public List<Map<String, Object>> tuizhuList(DangqianYonghu dangqianYonghu, String laorenXingming, String shenpiZhuangtai) {
        requireAdmin(dangqianYonghu);
        return adminShenpiMapper.findTuizhuList(laorenXingming, shenpiZhuangtai);
    }

    @Override
    @Transactional
    public void tuizhuPass(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        Map<String, Object> shenqing = requireTuizhu(id);
        Long laorenId = Long.valueOf(shenqing.get("laorenId").toString());
        Map<String, Object> chuangwei = adminShenpiMapper.findCurrentChuangweiByLaorenId(laorenId);
        adminShenpiMapper.updateTuizhuShenpi(id, "通过", dangqianYonghu.yonghuId(), stringValue(data.get("shenpiYijian")));
        if (chuangwei != null) {
            adminShenpiMapper.updateChuangweiZhuangtai(Long.valueOf(chuangwei.get("chuangweiId").toString()), "空闲");
            adminShenpiMapper.endCurrentChuangweiShiyong(laorenId, "退住释放");
        }
        adminShenpiMapper.updateLaorenRuzhuZhuangtai(laorenId, "退住");
    }

    @Override
    public void tuizhuReject(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        requireTuizhu(id);
        adminShenpiMapper.updateTuizhuShenpi(id, "不通过", dangqianYonghu.yonghuId(), stringValue(data.get("shenpiYijian")));
    }

    @Override
    public void tuizhuDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        requireTuizhu(id);
        adminShenpiMapper.deleteTuizhuLogic(id);
    }

    private Map<String, Object> requireWaichu(Long id) {
        Map<String, Object> shenqing = adminShenpiMapper.findWaichuById(id);
        if (shenqing == null) {
            throw new IllegalArgumentException("外出申请不存在");
        }
        return shenqing;
    }

    private Map<String, Object> requireTuizhu(Long id) {
        Map<String, Object> shenqing = adminShenpiMapper.findTuizhuById(id);
        if (shenqing == null) {
            throw new IllegalArgumentException("退住申请不存在");
        }
        return shenqing;
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以审批申请");
        }
    }

    private Long requireLong(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("缺少必要参数：" + key);
        }
        return Long.valueOf(value.toString());
    }

    private String stringValue(Object value) {
        return value == null ? "" : value.toString();
    }
}
