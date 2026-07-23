package com.neu.admin.biz.impl;

import com.neu.admin.biz.AdminLaorenXiugaiShenqingBiz;
import com.neu.admin.mapper.AdminLaorenXiugaiShenqingMapper;
import com.neu.common.auth.DangqianYonghu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AdminLaorenXiugaiShenqingBizImpl implements AdminLaorenXiugaiShenqingBiz {

    private final AdminLaorenXiugaiShenqingMapper adminLaorenXiugaiShenqingMapper;

    public AdminLaorenXiugaiShenqingBizImpl(AdminLaorenXiugaiShenqingMapper adminLaorenXiugaiShenqingMapper) {
        this.adminLaorenXiugaiShenqingMapper = adminLaorenXiugaiShenqingMapper;
    }

    @Override
    public List<Map<String, Object>> list(DangqianYonghu dangqianYonghu, String laorenXingming,
                                          String jiashuXingming, String shenqingZhuangtai) {
        requireAdmin(dangqianYonghu);
        return adminLaorenXiugaiShenqingMapper.findList(laorenXingming, jiashuXingming, shenqingZhuangtai);
    }

    @Override
    public Map<String, Object> detail(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        return requireShenqing(id);
    }

    @Override
    @Transactional
    public void pass(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        Map<String, Object> shenqing = requireShenqing(id);
        if (!"待审核".equals(text(shenqing.get("shenqingZhuangtai")))) {
            throw new IllegalArgumentException("只有待审核申请可以审批通过");
        }
        adminLaorenXiugaiShenqingMapper.updateLaorenXinxi(shenqing);
        String shenheRenXingming = adminLaorenXiugaiShenqingMapper.findYonghuXingming(dangqianYonghu.yonghuId());
        adminLaorenXiugaiShenqingMapper.updateShenhe(
                id, "已通过", dangqianYonghu.yonghuId(), shenheRenXingming, text(data.get("shenheYijian"))
        );
    }

    @Override
    public void reject(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        Map<String, Object> shenqing = requireShenqing(id);
        if (!"待审核".equals(text(shenqing.get("shenqingZhuangtai")))) {
            throw new IllegalArgumentException("只有待审核申请可以驳回");
        }
        String shenheRenXingming = adminLaorenXiugaiShenqingMapper.findYonghuXingming(dangqianYonghu.yonghuId());
        adminLaorenXiugaiShenqingMapper.updateShenhe(
                id, "已驳回", dangqianYonghu.yonghuId(), shenheRenXingming, text(data.get("shenheYijian"))
        );
    }

    @Override
    public void delete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        requireShenqing(id);
        adminLaorenXiugaiShenqingMapper.deleteLogic(id);
    }

    private Map<String, Object> requireShenqing(Long id) {
        Map<String, Object> shenqing = adminLaorenXiugaiShenqingMapper.findById(id);
        if (shenqing == null) {
            throw new IllegalArgumentException("老人信息修改申请不存在");
        }
        return shenqing;
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以审核老人信息修改申请");
        }
    }

    private Long requireLong(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("缺少必要参数：" + key);
        }
        return Long.valueOf(value.toString());
    }

    private String text(Object value) {
        return value == null ? "" : value.toString().trim();
    }
}
