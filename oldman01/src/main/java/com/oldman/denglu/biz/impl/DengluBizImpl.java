package com.oldman.denglu.biz.impl;


import com.oldman.denglu.biz.DengluBiz;
import com.oldman.denglu.dto.DengluQingqiu;
import com.oldman.denglu.dto.DengluXiangying;
import com.oldman.xitong.entity.XitongJuese;
import com.oldman.xitong.entity.XitongYonghu;
import com.oldman.xitong.mapper.XitongYonghuMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;

@Service
public class DengluBizImpl implements DengluBiz {

    private final XitongYonghuMapper xitongYonghuMapper;

    public DengluBizImpl(XitongYonghuMapper xitongYonghuMapper) {
        this.xitongYonghuMapper = xitongYonghuMapper;
    }

    @Override
    public DengluXiangying denglu(DengluQingqiu qingqiu) {
        if (qingqiu == null || !StringUtils.hasText(qingqiu.getDengluZhanghao())
                || !StringUtils.hasText(qingqiu.getMima())) {
            throw new IllegalArgumentException("请输入登录账号和密码");
        }

        XitongYonghu yonghu = xitongYonghuMapper.findByDengluZhanghao(qingqiu.getDengluZhanghao().trim());
        if (yonghu == null || !qingqiu.getMima().equals(yonghu.getMima())) {
            throw new IllegalArgumentException("账号或密码不正确");
        }

        if (!"启用".equals(yonghu.getZhuangtai())) {
            throw new IllegalArgumentException("账号已停用，请联系管理员");
        }

        xitongYonghuMapper.updateZuihouDengluShijian(yonghu.getId());

        List<XitongJuese> jueseList = yonghu.getJueseList() == null ? List.of() : yonghu.getJueseList();
        List<String> jueseBianmaList = jueseList.stream()
                .map(XitongJuese::getJueseBianma)
                .filter(StringUtils::hasText)
                .toList();
        List<String> jueseMingchengList = jueseList.stream()
                .map(XitongJuese::getJueseMingcheng)
                .filter(StringUtils::hasText)
                .toList();
        String token = "oldman-" + yonghu.getId() + "-" + Instant.now().toEpochMilli();

        return new DengluXiangying(
                token,
                yonghu.getId(),
                yonghu.getDengluZhanghao(),
                yonghu.getXingming(),
                yonghu.getTouxiang(),
                jueseBianmaList,
                jueseMingchengList
        );
    }
}


