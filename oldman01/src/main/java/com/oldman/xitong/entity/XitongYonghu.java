package com.oldman.xitong.entity;

import java.time.LocalDateTime;
import java.util.List;

public class XitongYonghu {

    private Long id;
    private String dengluZhanghao;
    private String mima;
    private String xingming;
    private String xingbie;
    private String shouji;
    private String youxiang;
    private String touxiang;
    private String zhuangtai;
    private LocalDateTime zuihouDengluShijian;
    private List<XitongJuese> jueseList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDengluZhanghao() {
        return dengluZhanghao;
    }

    public void setDengluZhanghao(String dengluZhanghao) {
        this.dengluZhanghao = dengluZhanghao;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getXingbie() {
        return xingbie;
    }

    public void setXingbie(String xingbie) {
        this.xingbie = xingbie;
    }

    public String getShouji() {
        return shouji;
    }

    public void setShouji(String shouji) {
        this.shouji = shouji;
    }

    public String getYouxiang() {
        return youxiang;
    }

    public void setYouxiang(String youxiang) {
        this.youxiang = youxiang;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    public LocalDateTime getZuihouDengluShijian() {
        return zuihouDengluShijian;
    }

    public void setZuihouDengluShijian(LocalDateTime zuihouDengluShijian) {
        this.zuihouDengluShijian = zuihouDengluShijian;
    }

    public List<XitongJuese> getJueseList() {
        return jueseList;
    }

    public void setJueseList(List<XitongJuese> jueseList) {
        this.jueseList = jueseList;
    }
}


