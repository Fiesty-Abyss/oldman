package com.neu.xitong.biz.impl;

import com.neu.common.auth.DangqianYonghu;
import com.neu.xitong.biz.XitongGuanliBiz;
import com.neu.xitong.mapper.XitongGuanliMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class XitongGuanliBizImpl implements XitongGuanliBiz {

    private final XitongGuanliMapper xitongGuanliMapper;

    public XitongGuanliBizImpl(XitongGuanliMapper xitongGuanliMapper) {
        this.xitongGuanliMapper = xitongGuanliMapper;
    }

    @Override
    public List<Map<String, Object>> yonghuList(DangqianYonghu dangqianYonghu, String dengluZhanghao,
                                                 String xingming, String jueseBianma, String zhuangtai) {
        requireAdmin(dangqianYonghu);
        return xitongGuanliMapper.findYonghuList(dengluZhanghao, xingming, jueseBianma, zhuangtai);
    }

    @Override
    @Transactional
    public void yonghuAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireText(data, "dengluZhanghao");
        requireText(data, "mima");
        requireText(data, "xingming");
        if (xitongGuanliMapper.countYonghuByZhanghao(text(data.get("dengluZhanghao")), null) > 0) {
            throw new IllegalArgumentException("登录账号已存在");
        }
        fillJiashuPhone(data);
        data.put("zhuangtai", defaultText(data.get("zhuangtai"), "启用"));
        xitongGuanliMapper.insertYonghu(data);
        Long yonghuId = Long.valueOf(data.get("id").toString());
        assignRoles(yonghuId, roleIdList(data.get("jueseIds")));
        bindHuliRenyuanIfPresent(yonghuId, data);
    }

    @Override
    @Transactional
    public void yonghuUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        requireYonghu(id);
        requireText(data, "dengluZhanghao");
        requireText(data, "xingming");
        if (xitongGuanliMapper.countYonghuByZhanghao(text(data.get("dengluZhanghao")), id) > 0) {
            throw new IllegalArgumentException("登录账号已存在");
        }
        fillJiashuPhone(data);
        xitongGuanliMapper.updateYonghu(data);
        bindHuliRenyuanIfPresent(id, data);
    }

    @Override
    public void yonghuChangeStatus(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        if (dangqianYonghu.yonghuId().equals(id)) {
            throw new IllegalArgumentException("不能停用当前登录账号");
        }
        requireYonghu(id);
        String zhuangtai = defaultText(data.get("zhuangtai"), "启用");
        if (!"启用".equals(zhuangtai) && !"停用".equals(zhuangtai)) {
            throw new IllegalArgumentException("用户状态只能是启用或停用");
        }
        xitongGuanliMapper.updateYonghuStatus(id, zhuangtai);
    }

    @Override
    public void yonghuResetPassword(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        requireYonghu(id);
        String mima = requireText(data, "mima");
        xitongGuanliMapper.updateYonghuPassword(id, mima);
    }

    @Override
    public List<Map<String, Object>> yonghuRole(DangqianYonghu dangqianYonghu, Long yonghuId) {
        requireAdmin(dangqianYonghu);
        requireYonghu(yonghuId);
        return xitongGuanliMapper.findYonghuRole(yonghuId);
    }

    @Override
    @Transactional
    public void yonghuAssignRole(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long yonghuId = requireLong(data, "yonghuId");
        requireYonghu(yonghuId);
        assignRoles(yonghuId, roleIdList(data.get("jueseIds")));
    }

    @Override
    public List<Map<String, Object>> jueseList(DangqianYonghu dangqianYonghu) {
        requireAdmin(dangqianYonghu);
        return xitongGuanliMapper.findJueseList();
    }

    @Override
    public void jueseAdd(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        requireText(data, "jueseBianma");
        requireText(data, "jueseMingcheng");
        if (xitongGuanliMapper.countJueseByBianma(text(data.get("jueseBianma")), null) > 0) {
            throw new IllegalArgumentException("角色编码已存在");
        }
        xitongGuanliMapper.insertJuese(data);
    }

    @Override
    public void jueseUpdate(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        Long id = requireLong(data, "id");
        requireJuese(id);
        requireText(data, "jueseBianma");
        requireText(data, "jueseMingcheng");
        if (xitongGuanliMapper.countJueseByBianma(text(data.get("jueseBianma")), id) > 0) {
            throw new IllegalArgumentException("角色编码已存在");
        }
        xitongGuanliMapper.updateJuese(data);
    }

    @Override
    public void jueseChangeStatus(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        requireAdmin(dangqianYonghu);
        throw new IllegalArgumentException("当前 xitong_juese 表缺少 zhuangtai 字段，需新增后才能启用/停用角色");
    }

    @Override
    public void jueseDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        throw new IllegalArgumentException("当前 xitong_juese 表缺少逻辑删除字段，需新增后才能逻辑删除角色");
    }

    @Override
    public List<Map<String, Object>> huliRenyuanOptions(DangqianYonghu dangqianYonghu) {
        requireAdmin(dangqianYonghu);
        return xitongGuanliMapper.findHuliRenyuanOptions();
    }

    @Override
    public List<Map<String, Object>> jiashuOptions(DangqianYonghu dangqianYonghu) {
        requireAdmin(dangqianYonghu);
        return xitongGuanliMapper.findJiashuOptions();
    }

    private void assignRoles(Long yonghuId, List<Long> jueseIds) {
        xitongGuanliMapper.deleteYonghuRole(yonghuId);
        for (Long jueseId : jueseIds) {
            requireJuese(jueseId);
            xitongGuanliMapper.insertYonghuRole(yonghuId, jueseId);
        }
    }

    private void bindHuliRenyuanIfPresent(Long yonghuId, Map<String, Object> data) {
        Object value = data.get("huliRenyuanId");
        if (value == null || value.toString().isBlank()) {
            xitongGuanliMapper.unbindHuliRenyuanByYonghuId(yonghuId);
            return;
        }
        Long huliRenyuanId = Long.valueOf(value.toString());
        Map<String, Object> huliRenyuan = xitongGuanliMapper.findHuliRenyuanById(huliRenyuanId);
        if (huliRenyuan == null) {
            throw new IllegalArgumentException("护理人员不存在");
        }
        xitongGuanliMapper.unbindHuliRenyuanByYonghuId(yonghuId);
        xitongGuanliMapper.bindHuliRenyuan(huliRenyuanId, yonghuId);
    }

    private void fillJiashuPhone(Map<String, Object> data) {
        Object value = data.get("jiashuId");
        if (value == null || value.toString().isBlank()) {
            return;
        }
        Map<String, Object> jiashu = xitongGuanliMapper.findJiashuById(Long.valueOf(value.toString()));
        if (jiashu == null) {
            throw new IllegalArgumentException("家属信息不存在");
        }
        data.put("shouji", jiashu.get("shouji"));
    }

    private Map<String, Object> requireYonghu(Long id) {
        Map<String, Object> yonghu = xitongGuanliMapper.findYonghuById(id);
        if (yonghu == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return yonghu;
    }

    private Map<String, Object> requireJuese(Long id) {
        Map<String, Object> juese = xitongGuanliMapper.findJueseById(id);
        if (juese == null) {
            throw new IllegalArgumentException("角色不存在");
        }
        return juese;
    }

    private List<Long> roleIdList(Object value) {
        List<Long> result = new ArrayList<>();
        if (value instanceof Iterable<?> iterable) {
            for (Object item : iterable) {
                if (item != null && !item.toString().isBlank()) {
                    result.add(Long.valueOf(item.toString()));
                }
            }
        } else if (value != null && !value.toString().isBlank()) {
            for (String item : value.toString().split(",")) {
                if (!item.isBlank()) {
                    result.add(Long.valueOf(item.trim()));
                }
            }
        }
        if (result.isEmpty()) {
            throw new IllegalArgumentException("请至少分配一个角色");
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

    private String requireText(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("缺少必要参数：" + key);
        }
        return value.toString().trim();
    }

    private String defaultText(Object value, String defaultValue) {
        return value == null || value.toString().isBlank() ? defaultValue : value.toString().trim();
    }

    private String text(Object value) {
        return value == null ? "" : value.toString().trim();
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以访问系统管理");
        }
    }
}
