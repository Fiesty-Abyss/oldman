package com.neu.admin.biz.impl;

import com.neu.admin.biz.AdminJiluBiz;
import com.neu.admin.mapper.AdminJiluMapper;
import com.neu.common.auth.DangqianYonghu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminJiluBizImpl implements AdminJiluBiz {

    private final AdminJiluMapper adminJiluMapper;

    public AdminJiluBizImpl(AdminJiluMapper adminJiluMapper) {
        this.adminJiluMapper = adminJiluMapper;
    }

    @Override
    public List<Map<String, Object>> huliList(DangqianYonghu dangqianYonghu, String laorenXingming,
                                              String huliRenyuanXingming, String xiangmuMingcheng, String riqi) {
        requireAdmin(dangqianYonghu);
        return adminJiluMapper.findHuliList(laorenXingming, huliRenyuanXingming, xiangmuMingcheng, riqi);
    }

    @Override
    public void huliDelete(DangqianYonghu dangqianYonghu, Long id) {
        requireAdmin(dangqianYonghu);
        adminJiluMapper.deleteHuliJilu(id);
    }

    @Override
    public List<Map<String, Object>> jiankangList(DangqianYonghu dangqianYonghu, String laorenXingming, String riqi) {
        requireAdmin(dangqianYonghu);
        return adminJiluMapper.findJiankangList(laorenXingming, riqi);
    }

    @Override
    public List<Map<String, Object>> fuyaoList(DangqianYonghu dangqianYonghu, String laorenXingming,
                                               String yaopinMingcheng, String zhixingZhuangtai, String riqi) {
        requireAdmin(dangqianYonghu);
        return adminJiluMapper.findFuyaoList(laorenXingming, yaopinMingcheng, zhixingZhuangtai, riqi);
    }

    private void requireAdmin(DangqianYonghu dangqianYonghu) {
        if (!dangqianYonghu.hasJuese("admin")) {
            throw new IllegalArgumentException("只有管理员可以查看监管记录");
        }
    }
}
