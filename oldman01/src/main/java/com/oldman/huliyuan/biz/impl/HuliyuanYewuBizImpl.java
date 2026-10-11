package com.oldman.huliyuan.biz.impl;


import com.oldman.huliyuan.biz.HuliyuanYewuBiz;
import com.oldman.common.auth.DangqianYonghu;
import com.oldman.huliyuan.mapper.HuliyuanYewuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class HuliyuanYewuBizImpl implements HuliyuanYewuBiz {

    private final HuliyuanYewuMapper huliyuanYewuMapper;

    public HuliyuanYewuBizImpl(HuliyuanYewuMapper huliyuanYewuMapper) {
        this.huliyuanYewuMapper = huliyuanYewuMapper;
    }

    @Override
    public List<Map<String, Object>> laorenList(DangqianYonghu dangqianYonghu, String laorenXingming) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        return huliyuanYewuMapper.findLaorenList(huliRenyuanId, laorenXingming);
    }

    @Override
    public Map<String, Object> laorenDetail(DangqianYonghu dangqianYonghu, Long laorenId) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        requireFuwuLaoren(huliRenyuanId, laorenId);
        Map<String, Object> detail = huliyuanYewuMapper.findLaorenDetail(huliRenyuanId, laorenId);
        if (detail == null) {
            throw new IllegalArgumentException("老人信息不存在或不在服务范围内");
        }
        return detail;
    }

    @Override
    public Map<String, Integer> tongji(DangqianYonghu dangqianYonghu) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        Map<String, Integer> data = new LinkedHashMap<>();
        data.put("wodeLaoren", huliyuanYewuMapper.countWodeLaoren(huliRenyuanId));
        data.put("jinriHuliJilu", huliyuanYewuMapper.countJinriHuliJilu(huliRenyuanId));
        data.put("jinriJiankangJiance", huliyuanYewuMapper.countJinriJiankangJiance(huliRenyuanId));
        data.put("jinriFuyao", huliyuanYewuMapper.countJinriFuyao(huliRenyuanId));
        data.put("daishenpiWaichu", huliyuanYewuMapper.countWoTijiaoDaishenpiWaichu(huliRenyuanId));
        data.put("daishenpiTuizhu", huliyuanYewuMapper.countWoTijiaoDaishenpiTuizhu(huliRenyuanId));
        return data;
    }

    @Override
    public List<Map<String, Object>> huliXiangmuList(DangqianYonghu dangqianYonghu, Long laorenId) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        requireFuwuLaoren(huliRenyuanId, laorenId);
        return huliyuanYewuMapper.findLaorenHuliXiangmuList(huliRenyuanId, laorenId);
    }

    @Override
    public List<Map<String, Object>> huliJiluList(DangqianYonghu dangqianYonghu, Long laorenId) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        if (laorenId != null) {
            requireFuwuLaoren(huliRenyuanId, laorenId);
        }
        return huliyuanYewuMapper.findHuliJiluList(huliRenyuanId, laorenId);
    }

    @Override
    public void addHuliJilu(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        Long laorenId = requireLong(data, "laorenId");
        Long xiangmuId = requireLong(data, "xiangmuId");
        requireFuwuLaoren(huliRenyuanId, laorenId);
        if (huliyuanYewuMapper.countLaorenHuliXiangmu(huliRenyuanId, laorenId, xiangmuId) == 0) {
            throw new IllegalArgumentException("该护理项目不属于老人当前护理级别");
        }
        data.put("huliRenyuanId", huliRenyuanId);
        huliyuanYewuMapper.insertHuliJilu(data);
    }

    @Override
    public void deleteHuliJilu(DangqianYonghu dangqianYonghu, Long id) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        huliyuanYewuMapper.deleteHuliJilu(id, huliRenyuanId);
    }

    @Override
    public List<Map<String, Object>> jiankangJianceList(DangqianYonghu dangqianYonghu, Long laorenId) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        if (laorenId != null) {
            requireFuwuLaoren(huliRenyuanId, laorenId);
        }
        return huliyuanYewuMapper.findJiankangJianceList(huliRenyuanId, laorenId);
    }

    @Override
    public void addJiankangJiance(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        Long laorenId = requireLong(data, "laorenId");
        requireFuwuLaoren(huliRenyuanId, laorenId);
        data.put("huliRenyuanId", huliRenyuanId);
        huliyuanYewuMapper.insertJiankangJiance(data);
    }

    @Override
    public List<Map<String, Object>> yongyaoJihuaList(DangqianYonghu dangqianYonghu, Long laorenId) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        if (laorenId != null) {
            requireFuwuLaoren(huliRenyuanId, laorenId);
        }
        return huliyuanYewuMapper.findYongyaoJihuaList(huliRenyuanId, laorenId);
    }

    @Override
    public List<Map<String, Object>> fuyaoJiluList(DangqianYonghu dangqianYonghu, Long laorenId) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        if (laorenId != null) {
            requireFuwuLaoren(huliRenyuanId, laorenId);
        }
        return huliyuanYewuMapper.findFuyaoJiluList(huliRenyuanId, laorenId);
    }

    @Override
    public void addFuyaoJilu(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        Long jihuaId = requireLong(data, "jihuaId");
        Map<String, Object> jihua = huliyuanYewuMapper.findYongyaoJihuaForHuli(huliRenyuanId, jihuaId);
        if (jihua == null) {
            throw new IllegalArgumentException("只能执行自己负责老人对应的用药计划");
        }
        data.put("huliRenyuanId", huliRenyuanId);
        data.put("laorenId", jihua.get("laorenId"));
        data.put("yaopinId", jihua.get("yaopinId"));
        huliyuanYewuMapper.insertFuyaoJilu(data);
    }

    @Override
    public List<Map<String, Object>> waichuList(DangqianYonghu dangqianYonghu, Long laorenId) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        if (laorenId != null) {
            requireFuwuLaoren(huliRenyuanId, laorenId);
        }
        return huliyuanYewuMapper.findWaichuList(huliRenyuanId, laorenId);
    }

    @Override
    @Transactional
    public void addWaichu(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        Map<String, Object> huliRenyuan = requireHuliRenyuan(dangqianYonghu);
        Long huliRenyuanId = Long.valueOf(huliRenyuan.get("id").toString());
        Long laorenId = requireLong(data, "laorenId");
        requireFuwuLaoren(huliRenyuanId, laorenId);
        data.put("huliRenyuanId", huliRenyuanId);
        data.put("shenqingRenId", huliRenyuanId);
        data.put("shenqingRenLeixing", "护理员");
        data.put("shenqingRenXingming", huliRenyuan.get("xingming"));
        huliyuanYewuMapper.insertWaichu(data);
    }

    @Override
    public List<Map<String, Object>> tuizhuList(DangqianYonghu dangqianYonghu, Long laorenId) {
        Long huliRenyuanId = requireHuliRenyuanId(dangqianYonghu);
        if (laorenId != null) {
            requireFuwuLaoren(huliRenyuanId, laorenId);
        }
        return huliyuanYewuMapper.findTuizhuList(huliRenyuanId, laorenId);
    }

    @Override
    @Transactional
    public void addTuizhu(DangqianYonghu dangqianYonghu, Map<String, Object> data) {
        Map<String, Object> huliRenyuan = requireHuliRenyuan(dangqianYonghu);
        Long huliRenyuanId = Long.valueOf(huliRenyuan.get("id").toString());
        Long laorenId = requireLong(data, "laorenId");
        requireFuwuLaoren(huliRenyuanId, laorenId);
        data.put("huliRenyuanId", huliRenyuanId);
        data.put("shenqingRenId", huliRenyuanId);
        data.put("shenqingRenLeixing", "护理员");
        data.put("shenqingRenXingming", huliRenyuan.get("xingming"));
        huliyuanYewuMapper.insertTuizhu(data);
    }

    private Long requireHuliRenyuanId(DangqianYonghu dangqianYonghu) {
        Map<String, Object> huliRenyuan = requireHuliRenyuan(dangqianYonghu);
        return Long.valueOf(huliRenyuan.get("id").toString());
    }

    private Map<String, Object> requireHuliRenyuan(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("huli")) {
            throw new IllegalArgumentException("只有护理员可以访问护理员端业务");
        }
        Map<String, Object> huliRenyuan = huliyuanYewuMapper.findHuliRenyuanByYonghuId(dangqianYonghu.yonghuId());
        if (huliRenyuan == null) {
            throw new IllegalArgumentException("当前账号未绑定护理人员信息");
        }
        return huliRenyuan;
    }

    private void requireFuwuLaoren(Long huliRenyuanId, Long laorenId) {
        if (laorenId == null || huliyuanYewuMapper.countFuwuLaoren(huliRenyuanId, laorenId) == 0) {
            throw new IllegalArgumentException("只能操作自己负责的老人");
        }
    }

    private Long requireLong(Map<String, Object> data, String key) {
        Object value = data.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("缺少必要参数：" + key);
        }
        return Long.valueOf(value.toString());
    }
}


