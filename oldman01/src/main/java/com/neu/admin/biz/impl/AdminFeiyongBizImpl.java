package com.neu.admin.biz.impl;

import com.neu.admin.biz.AdminFeiyongBiz;
import com.neu.admin.mapper.AdminFeiyongMapper;
import com.neu.common.auth.DangqianYonghu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class AdminFeiyongBizImpl implements AdminFeiyongBiz {

    private static final String ZHUANGTAI_DAIJIAO = "待缴费";
    private static final String ZHUANGTAI_BUFEN = "部分缴费";
    private static final String ZHUANGTAI_JIEQING = "已结清";
    private static final String ZHUANGTAI_QUXIAO = "已取消";

    private final AdminFeiyongMapper adminFeiyongMapper;

    public AdminFeiyongBizImpl(AdminFeiyongMapper adminFeiyongMapper) {
        this.adminFeiyongMapper = adminFeiyongMapper;
    }

    @Override
    public List<Map<String, Object>> zhangdanList(DangqianYonghu dangqianYonghu, String laorenXingming,
                                                   String zhangdanYuefen, String zhuangtai) {
        requireAdmin(dangqianYonghu);
        return adminFeiyongMapper.findZhangdanList(laorenXingming, zhangdanYuefen, zhuangtai);
    }

    @Override
    @Transactional
    public void zhangdanAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireLong(data, "laorenId");
        fillZongjine(data);
        data.put("zhuangtai", ZHUANGTAI_DAIJIAO);
        adminFeiyongMapper.insertZhangdan(data);
    }

    @Override
    @Transactional
    public void zhangdanUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        Map<String, Object> zhangdan = requireZhangdan(id);
        if (ZHUANGTAI_QUXIAO.equals(zhangdan.get("zhuangtai"))) {
            throw new IllegalArgumentException("已取消账单不能修改");
        }

        fillZongjine(data);
        BigDecimal zongjine = money(data.get("zongjine"));
        BigDecimal yijiao = sumJiaofei(id);
        if (yijiao.compareTo(zongjine) > 0) {
            throw new IllegalArgumentException("账单金额不能小于已缴金额");
        }
        data.put("zhuangtai", calcZhuangtai(zongjine, yijiao));
        adminFeiyongMapper.updateZhangdan(data);
    }

    @Override
    @Transactional
    public void zhangdanDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        requireZhangdan(id);
        adminFeiyongMapper.cancelZhangdan(id);
    }

    @Override
    public Map<String, Object> zhangdanDetail(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        return requireZhangdan(id);
    }

    @Override
    public List<Map<String, Object>> jiaofeiList(DangqianYonghu dangqianYonghu, Long zhangdanId,
                                                  String laorenXingming, String zhangdanYuefen) {
        requireAdmin(dangqianYonghu);
        return adminFeiyongMapper.findJiaofeiList(zhangdanId, laorenXingming, zhangdanYuefen);
    }

    @Override
    @Transactional
    public void jiaofeiAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long zhangdanId = requireLong(data, "zhangdanId");
        Map<String, Object> zhangdan = requireZhangdan(zhangdanId);
        if (ZHUANGTAI_QUXIAO.equals(zhangdan.get("zhuangtai"))) {
            throw new IllegalArgumentException("已取消账单不能登记缴费");
        }

        BigDecimal jiaofeiJine = money(data.get("jiaofeiJine"));
        if (jiaofeiJine.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("缴费金额必须大于0");
        }

        BigDecimal weiJiaoJine = money(zhangdan.get("weiJiaoJine"));
        if (jiaofeiJine.compareTo(weiJiaoJine) > 0) {
            throw new IllegalArgumentException("缴费金额不能大于未缴金额");
        }

        data.put("laorenId", zhangdan.get("laorenId"));
        data.put("caozuorenId", dangqianYonghu.yonghuId());
        adminFeiyongMapper.insertJiaofei(data);
        recalcZhangdanZhuangtai(zhangdanId);
    }

    @Override
    @Transactional
    public void jiaofeiDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        Map<String, Object> jiaofei = adminFeiyongMapper.findJiaofeiById(id);
        if (jiaofei == null) {
            throw new IllegalArgumentException("缴费记录不存在");
        }
        Long zhangdanId = Long.valueOf(jiaofei.get("zhangdanId").toString());
        adminFeiyongMapper.deleteJiaofei(id);
        recalcZhangdanZhuangtai(zhangdanId);
    }

    private void recalcZhangdanZhuangtai(Long zhangdanId) {
        Map<String, Object> zhangdan = requireZhangdan(zhangdanId);
        if (ZHUANGTAI_QUXIAO.equals(zhangdan.get("zhuangtai"))) {
            return;
        }
        BigDecimal zongjine = money(zhangdan.get("zongjine"));
        BigDecimal yijiao = sumJiaofei(zhangdanId);
        adminFeiyongMapper.updateZhangdanZhuangtai(zhangdanId, calcZhuangtai(zongjine, yijiao));
    }

    private void fillZongjine(Map<String, Object> data) {
        BigDecimal chuangweiFei = money(data.get("chuangweiFei"));
        BigDecimal huliFei = money(data.get("huliFei"));
        BigDecimal yaopinFei = money(data.get("yaopinFei"));
        BigDecimal qitaFei = money(data.get("qitaFei"));
        data.put("chuangweiFei", chuangweiFei);
        data.put("huliFei", huliFei);
        data.put("yaopinFei", yaopinFei);
        data.put("qitaFei", qitaFei);
        data.put("zongjine", chuangweiFei.add(huliFei).add(yaopinFei).add(qitaFei));
    }

    private String calcZhuangtai(BigDecimal zongjine, BigDecimal yijiao) {
        if (yijiao.compareTo(BigDecimal.ZERO) == 0) {
            return ZHUANGTAI_DAIJIAO;
        }
        if (yijiao.compareTo(zongjine) >= 0) {
            return ZHUANGTAI_JIEQING;
        }
        return ZHUANGTAI_BUFEN;
    }

    private Map<String, Object> requireZhangdan(Long id) {
        Map<String, Object> zhangdan = adminFeiyongMapper.findZhangdanById(id);
        if (zhangdan == null) {
            throw new IllegalArgumentException("费用账单不存在");
        }
        return zhangdan;
    }

    private BigDecimal sumJiaofei(Long zhangdanId) {
        BigDecimal value = adminFeiyongMapper.sumJiaofeiByZhangdanId(zhangdanId);
        return value == null ? BigDecimal.ZERO : value.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal money(Object value) {
        if (value == null || value.toString().isBlank()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal result = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        return result;
    }

    private Long requireLong(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("缺少必要参数：" + key);
        }
        return Long.valueOf(value.toString());
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以管理费用账单");
        }
    }
}
