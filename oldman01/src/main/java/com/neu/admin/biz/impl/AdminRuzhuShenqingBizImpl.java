package com.neu.admin.biz.impl;

import com.neu.admin.biz.AdminRuzhuShenqingBiz;
import com.neu.admin.mapper.AdminRuzhuShenqingMapper;
import com.neu.common.auth.DangqianYonghu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class AdminRuzhuShenqingBizImpl implements AdminRuzhuShenqingBiz {

    private static final DateTimeFormatter BIANHAO_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final AdminRuzhuShenqingMapper adminRuzhuShenqingMapper;

    public AdminRuzhuShenqingBizImpl(AdminRuzhuShenqingMapper adminRuzhuShenqingMapper) {
        this.adminRuzhuShenqingMapper = adminRuzhuShenqingMapper;
    }

    @Override
    public List<Map<String, Object>> list(DangqianYonghu dangqianYonghu, String laorenXingming,
                                          String jiashuShouji, String shenqingZhuangtai) {
        requireAdmin(dangqianYonghu);
        return adminRuzhuShenqingMapper.findList(laorenXingming, jiashuShouji, shenqingZhuangtai);
    }

    @Override
    public Map<String, Object> detail(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        return requireShenqing(id);
    }

    @Override
    public List<Map<String, Object>> passedList(DangqianYonghu dangqianYonghu, String laorenXingming, String jiashuShouji) {
        requireAdmin(dangqianYonghu);
        return adminRuzhuShenqingMapper.findPassedUnbanliList(laorenXingming, jiashuShouji);
    }

    @Override
    public Map<String, Object> bring(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        Map<String, Object> shenqing = requireShenqing(id);
        if (!"已通过".equals(text(shenqing.get("shenqingZhuangtai"))) || !"未办理".equals(text(shenqing.get("banliZhuangtai")))) {
            throw new IllegalArgumentException("只能选择已通过且未办理的入住申请");
        }
        if (shenqing.get("laorenId") == null) {
            throw new IllegalArgumentException("申请未关联老人档案，请重新审核或手动办理");
        }
        return shenqing;
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

        String shenheRenXingming = adminRuzhuShenqingMapper.findYonghuXingming(dangqianYonghu.yonghuId());
        adminRuzhuShenqingMapper.updateShenhe(
                id, "已通过", dangqianYonghu.yonghuId(), shenheRenXingming, text(data.get("shenheYijian"))
        );

        Long laorenId = findOrCreateLaoren(shenqing);
        upsertJiashu(shenqing, laorenId);
        adminRuzhuShenqingMapper.updateLaorenId(id, laorenId);
    }

    @Override
    public void reject(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        Map<String, Object> shenqing = requireShenqing(id);
        if (!"待审核".equals(text(shenqing.get("shenqingZhuangtai")))) {
            throw new IllegalArgumentException("只有待审核申请可以驳回");
        }
        String shenheRenXingming = adminRuzhuShenqingMapper.findYonghuXingming(dangqianYonghu.yonghuId());
        adminRuzhuShenqingMapper.updateShenhe(
                id, "已驳回", dangqianYonghu.yonghuId(), shenheRenXingming, text(data.get("shenheYijian"))
        );
    }

    @Override
    public void delete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        Map<String, Object> shenqing = requireShenqing(id);
        if ("已通过".equals(text(shenqing.get("shenqingZhuangtai")))
                && "已办理".equals(text(shenqing.get("banliZhuangtai")))) {
            throw new IllegalArgumentException("该申请已办理正式入住，不能删除");
        }
        adminRuzhuShenqingMapper.deleteLogic(id);
    }

    private Long findOrCreateLaoren(Map<String, Object> shenqing) {
        String shenfenzhenghao = text(shenqing.get("shenfenzhenghao"));
        String lianxiDianhua = text(shenqing.get("lianxiDianhua"));
        Long laorenId = StringUtils.hasText(shenfenzhenghao)
                ? adminRuzhuShenqingMapper.findLaorenIdByShenfenzheng(shenfenzhenghao)
                : null;
        if (laorenId == null && StringUtils.hasText(lianxiDianhua)) {
            laorenId = adminRuzhuShenqingMapper.findLaorenIdByNameAndPhone(text(shenqing.get("laorenXingming")), lianxiDianhua);
        }

        if (laorenId != null) {
            adminRuzhuShenqingMapper.updateLaorenBase(laorenId, shenqing);
            return laorenId;
        }

        shenqing.put("laorenBianhao", "LR" + BIANHAO_FORMAT.format(LocalDateTime.now()));
        shenqing.put("laorenLeixing", StringUtils.hasText(text(shenqing.get("huliXuqiu"))) ? "护理老人" : "自理老人");
        adminRuzhuShenqingMapper.insertLaoren(shenqing);
        return Long.valueOf(shenqing.get("laorenId").toString());
    }

    private void upsertJiashu(Map<String, Object> shenqing, Long laorenId) {
        String jiashuShouji = text(shenqing.get("jiashuShouji"));
        if (!StringUtils.hasText(jiashuShouji)) {
            return;
        }
        Long jiashuId = adminRuzhuShenqingMapper.findJiashuId(laorenId, jiashuShouji);
        if (jiashuId == null) {
            adminRuzhuShenqingMapper.insertJiashu(laorenId, shenqing);
        } else {
            adminRuzhuShenqingMapper.updateJiashu(jiashuId, shenqing);
        }
    }

    private Map<String, Object> requireShenqing(Long id) {
        Map<String, Object> shenqing = adminRuzhuShenqingMapper.findById(id);
        if (shenqing == null) {
            throw new IllegalArgumentException("入住申请不存在");
        }
        return shenqing;
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以审核入住申请");
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
