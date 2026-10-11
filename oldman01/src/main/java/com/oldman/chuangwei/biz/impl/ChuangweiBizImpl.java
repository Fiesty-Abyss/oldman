package com.oldman.chuangwei.biz.impl;


import com.oldman.chuangwei.biz.ChuangweiBiz;
import com.oldman.chuangwei.mapper.ChuangweiMapper;
import com.oldman.laoren.mapper.LaorenXinxiMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ChuangweiBizImpl implements ChuangweiBiz {

    private final ChuangweiMapper chuangweiMapper;
    private final LaorenXinxiMapper laorenXinxiMapper;

    public ChuangweiBizImpl(ChuangweiMapper chuangweiMapper, LaorenXinxiMapper laorenXinxiMapper) {
        this.chuangweiMapper = chuangweiMapper;
        this.laorenXinxiMapper = laorenXinxiMapper;
    }

    @Override
    public List<Map<String, Object>> list() {
        return chuangweiMapper.findFangjianChuangweiList();
    }

    @Override
    public List<Map<String, Object>> fangjianList() {
        return chuangweiMapper.findFangjianList();
    }

    @Override
    public List<Map<String, Object>> fangjianOptions() {
        return chuangweiMapper.findFangjianOptions();
    }

    @Override
    public void fangjianAdd(Map<String, Object> data) {
        requireText(data, "louceng", "楼层不能为空");
        requireText(data, "fangjianhao", "房间号不能为空");
        chuangweiMapper.insertFangjian(data);
    }

    @Override
    public void fangjianUpdate(Map<String, Object> data) {
        requireLong(data, "id");
        requireText(data, "louceng", "楼层不能为空");
        requireText(data, "fangjianhao", "房间号不能为空");
        chuangweiMapper.updateFangjian(data);
    }

    @Override
    public void fangjianDelete(Long id) {
        if (chuangweiMapper.countNormalChuangweiByFangjianId(id) > 0) {
            throw new IllegalArgumentException("房间下还有未删除床位，不能删除房间");
        }
        chuangweiMapper.deleteFangjianLogic(id);
    }

    @Override
    public List<Map<String, Object>> chuangweiBaseList() {
        return chuangweiMapper.findChuangweiBaseList();
    }

    @Override
    public List<Map<String, Object>> kongxianOptions(Long fangjianId) {
        return chuangweiMapper.findKongxianOptions(fangjianId);
    }

    @Override
    public void chuangweiAdd(Map<String, Object> data) {
        requireLong(data, "fangjianId");
        requireText(data, "chuangweihao", "床位号不能为空");
        data.putIfAbsent("zhuangtai", "空闲");
        chuangweiMapper.insertChuangwei(data);
        chuangweiMapper.refreshFangjianChuangweiShu(toLong(data.get("fangjianId")));
    }

    @Override
    public void chuangweiUpdate(Map<String, Object> data) {
        requireLong(data, "id");
        requireLong(data, "fangjianId");
        requireText(data, "chuangweihao", "床位号不能为空");
        chuangweiMapper.updateChuangwei(data);
    }

    @Override
    public void chuangweiDelete(Long id) {
        Map<String, Object> chuangwei = chuangweiMapper.findChuangweiBaseById(id);
        if (chuangwei == null) {
            throw new IllegalArgumentException("床位不存在");
        }
        String zhuangtai = String.valueOf(chuangwei.get("zhuangtai"));
        if ("有人".equals(zhuangtai) || "外出".equals(zhuangtai)) {
            throw new IllegalArgumentException("有人或外出状态的床位不能删除");
        }
        if (!"空闲".equals(zhuangtai) && !"维修".equals(zhuangtai)) {
            throw new IllegalArgumentException("只有空闲或维修床位可以删除");
        }
        Long fangjianId = toLong(chuangwei.get("fangjianId"));
        chuangweiMapper.deleteChuangweiLogic(id);
        chuangweiMapper.refreshFangjianChuangweiShu(fangjianId);
    }

    @Override
    @Transactional
    public void fenpei(Map<String, Object> data) {
        Long chuangweiId = requireLong(data, "chuangweiId");
        Long laorenId = requireLong(data, "laorenId");
        if (chuangweiMapper.findCurrentChuangweiByLaorenId(laorenId) != null) {
            throw new IllegalArgumentException("该老人已有正在使用的床位，请使用调换床位");
        }
        if (chuangweiMapper.countKongxianChuangwei(chuangweiId) == 0) {
            throw new IllegalArgumentException("只能选择空闲床位");
        }
        Map<String, Object> chuangwei = chuangweiMapper.findChuangweiBaseById(chuangweiId);
        data.put("fangjianId", chuangwei.get("fangjianId"));
        data.put("yuanyin", "入住分配");
        chuangweiMapper.insertChuangweiShiyong(data);
        chuangweiMapper.updateChuangweiZhuangtai(chuangweiId, "有人");
        laorenXinxiMapper.updateLaorenRuzhuZhuangtai(laorenId, "在住");
    }

    @Override
    @Transactional
    public void tiaohuan(Map<String, Object> data) {
        Long laorenId = toLong(data.get("laorenId"));
        Long chuangweiId = requireLong(data, "chuangweiId");
        if (chuangweiMapper.countKongxianChuangwei(chuangweiId) == 0) {
            throw new IllegalArgumentException("只能选择空闲床位");
        }
        Map<String, Object> target = chuangweiMapper.findChuangweiBaseById(chuangweiId);
        Map<String, Object> current = chuangweiMapper.findCurrentChuangweiByLaorenId(laorenId);
        if (current != null && current.get("chuangweiId") != null) {
            chuangweiMapper.updateChuangweiZhuangtai(toLong(current.get("chuangweiId")), "空闲");
        }
        chuangweiMapper.endCurrentChuangweiShiyong(laorenId, "床位调换");
        data.put("fangjianId", target.get("fangjianId"));
        data.put("yuanyin", "床位调换");
        chuangweiMapper.insertChuangweiShiyong(data);
        chuangweiMapper.updateChuangweiZhuangtai(chuangweiId, "有人");
    }

    @Override
    @Transactional
    public void shifang(Long laorenId) {
        Map<String, Object> current = chuangweiMapper.findCurrentChuangweiByLaorenId(laorenId);
        if (current != null && current.get("chuangweiId") != null) {
            chuangweiMapper.updateChuangweiZhuangtai(toLong(current.get("chuangweiId")), "空闲");
        }
        chuangweiMapper.endCurrentChuangweiShiyong(laorenId, "退住释放");
        laorenXinxiMapper.updateLaorenRuzhuZhuangtai(laorenId, "退住");
    }

    private Long toLong(Object value) {
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


