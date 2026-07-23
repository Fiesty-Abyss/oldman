package com.neu.zanzhu.biz.impl;

import com.neu.common.auth.DangqianYonghu;
import com.neu.zanzhu.biz.AdminZanzhuBiz;
import com.neu.zanzhu.mapper.AdminZanzhuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class AdminZanzhuBizImpl implements AdminZanzhuBiz {

    private final AdminZanzhuMapper adminZanzhuMapper;

    public AdminZanzhuBizImpl(AdminZanzhuMapper adminZanzhuMapper) {
        this.adminZanzhuMapper = adminZanzhuMapper;
    }

    @Override
    public List<Map<String, Object>> renList(DangqianYonghu dangqianYonghu, String keyword) {
        requireAdmin(dangqianYonghu);
        return adminZanzhuMapper.findRenList(keyword);
    }

    @Override
    public void renAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireText(data, "zanzhuRenming", "赞助人名称不能为空");
        requireText(data, "zanzhuLeixing", "赞助人类型不能为空");
        data.putIfAbsent("gongkaiZhuangtai", 1);
        adminZanzhuMapper.insertRen(data);
    }

    @Override
    public void renUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "id");
        requireText(data, "zanzhuRenming", "赞助人名称不能为空");
        requireText(data, "zanzhuLeixing", "赞助人类型不能为空");
        adminZanzhuMapper.updateRen(data);
    }

    @Override
    public void renDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        adminZanzhuMapper.deleteRen(id);
    }

    @Override
    public List<Map<String, Object>> jiluList(DangqianYonghu dangqianYonghu, String keyword) {
        requireAdmin(dangqianYonghu);
        return adminZanzhuMapper.findJiluList(keyword);
    }

    @Override
    public void jiluAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "zanzhuRenId");
        requireText(data, "zanzhuBiaoti", "赞助标题不能为空");
        requireText(data, "zanzhuFangshi", "赞助方式不能为空");
        requireText(data, "zanzhuRiqi", "赞助日期不能为空");
        normalizeDate(data, "zanzhuRiqi");
        nonNegative(data.get("zanzhuJine"), "赞助金额不能为负数");
        data.putIfAbsent("gongkaiZhuangtai", 1);
        data.putIfAbsent("shenheZhuangtai", "已确认");
        adminZanzhuMapper.insertJilu(data);
    }

    @Override
    public void jiluUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "id");
        requireLong(data, "zanzhuRenId");
        requireText(data, "zanzhuBiaoti", "赞助标题不能为空");
        requireText(data, "zanzhuFangshi", "赞助方式不能为空");
        requireText(data, "zanzhuRiqi", "赞助日期不能为空");
        normalizeDate(data, "zanzhuRiqi");
        nonNegative(data.get("zanzhuJine"), "赞助金额不能为负数");
        adminZanzhuMapper.updateJilu(data);
    }

    @Override
    public void jiluDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        adminZanzhuMapper.deleteJilu(id);
    }

    @Override
    public List<Map<String, Object>> wuziList(DangqianYonghu dangqianYonghu, String keyword) {
        requireAdmin(dangqianYonghu);
        return adminZanzhuMapper.findWuziList(keyword);
    }

    @Override
    public void wuziAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "zanzhuJiluId");
        requireText(data, "wuziMingcheng", "物资名称不能为空");
        requireText(data, "wuziLeibie", "物资类别不能为空");
        normalizeDate(data, "baozhiqi");
        nonNegative(data.get("shuliang"), "物资数量不能为负数");
        nonNegative(data.get("gujiJiazhi"), "估计价值不能为负数");
        adminZanzhuMapper.insertWuzi(data);
    }

    @Override
    public void wuziUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "id");
        requireLong(data, "zanzhuJiluId");
        requireText(data, "wuziMingcheng", "物资名称不能为空");
        requireText(data, "wuziLeibie", "物资类别不能为空");
        normalizeDate(data, "baozhiqi");
        nonNegative(data.get("shuliang"), "物资数量不能为负数");
        nonNegative(data.get("gujiJiazhi"), "估计价值不能为负数");
        adminZanzhuMapper.updateWuzi(data);
    }

    @Override
    public void wuziDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        adminZanzhuMapper.deleteWuzi(id);
    }

    @Override
    public List<Map<String, Object>> yongtuList(DangqianYonghu dangqianYonghu, String keyword) {
        requireAdmin(dangqianYonghu);
        return adminZanzhuMapper.findYongtuList(keyword);
    }

    @Override
    public void yongtuAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "zanzhuJiluId");
        requireText(data, "yongtuMingcheng", "用途名称不能为空");
        requireText(data, "yongtuLeibie", "用途类别不能为空");
        requireText(data, "shiyongRiqi", "使用日期不能为空");
        normalizeDate(data, "shiyongRiqi");
        nonNegative(data.get("shiyongJine"), "使用金额不能为负数");
        data.putIfAbsent("gongkaiZhuangtai", 1);
        adminZanzhuMapper.insertYongtu(data);
    }

    @Override
    public void yongtuUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "id");
        requireLong(data, "zanzhuJiluId");
        requireText(data, "yongtuMingcheng", "用途名称不能为空");
        requireText(data, "yongtuLeibie", "用途类别不能为空");
        requireText(data, "shiyongRiqi", "使用日期不能为空");
        normalizeDate(data, "shiyongRiqi");
        nonNegative(data.get("shiyongJine"), "使用金额不能为负数");
        adminZanzhuMapper.updateYongtu(data);
    }

    @Override
    public void yongtuDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        adminZanzhuMapper.deleteYongtu(id);
    }

    @Override
    public List<Map<String, Object>> yixiangList(DangqianYonghu dangqianYonghu, String keyword, String chuliZhuangtai) {
        requireAdmin(dangqianYonghu);
        return adminZanzhuMapper.findYixiangList(keyword, chuliZhuangtai);
    }

    @Override
    public Map<String, Object> yixiangDetail(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        Map<String, Object> data = adminZanzhuMapper.findYixiangById(id);
        if (data == null) {
            throw new IllegalArgumentException("赞助意向不存在");
        }
        return data;
    }

    @Override
    public void yixiangUpdateStatus(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "id");
        String status = requireText(data, "chuliZhuangtai", "处理状态不能为空");
        if (!List.of("待联系", "已联系", "已确认", "已取消").contains(status)) {
            throw new IllegalArgumentException("处理状态不正确");
        }
        adminZanzhuMapper.updateYixiangStatus(data);
    }

    @Override
    public void yixiangDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        adminZanzhuMapper.deleteYixiang(id);
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以访问公益赞助管理");
        }
    }

    private Long requireLong(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("缺少必要参数：" + key);
        }
        return Long.valueOf(value.toString());
    }

    private String requireText(Map<String, Object> data, String key, String message) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.toString().trim();
    }

    private void nonNegative(Object value, String message) {
        if (value == null || value.toString().isBlank()) {
            return;
        }
        if (new BigDecimal(value.toString()).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    private void normalizeDate(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            data.put(key, null);
            return;
        }
        String text = value.toString();
        data.put(key, text.length() >= 10 ? text.substring(0, 10) : text);
    }
}
