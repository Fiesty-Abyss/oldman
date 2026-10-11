-- 欧德曼养老院管理系统：数据库结构（MySQL 8.x / MariaDB 10.6+）
-- 由 MyBatis Mapper 中实际使用的表与字段整理而来。

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `chuangwei_shiyong`;
CREATE TABLE `chuangwei_shiyong` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `chuangwei_id` BIGINT NULL,
  `fangjian_id` BIGINT NULL,
  `jieshu_riqi` DATE NULL,
  `kaishi_riqi` DATE NULL,
  `laoren_id` BIGINT NULL,
  `shiyong_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '正在使用',
  `yuanyin` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_chuangwei_shiyong_chuangwei_id` (`chuangwei_id`),
  KEY `idx_chuangwei_shiyong_fangjian_id` (`fangjian_id`),
  KEY `idx_chuangwei_shiyong_laoren_id` (`laoren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='床位使用记录';

DROP TABLE IF EXISTS `chuangwei_xinxi`;
CREATE TABLE `chuangwei_xinxi` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `chuangwei_tupian` VARCHAR(500) NULL,
  `chuangweihao` VARCHAR(100) NULL,
  `fangjian_id` BIGINT NULL,
  `shanchu_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '正常',
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_chuangwei_xinxi_fangjian_id` (`fangjian_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='床位';

DROP TABLE IF EXISTS `fangjian_xinxi`;
CREATE TABLE `fangjian_xinxi` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `chaoxiang` VARCHAR(100) NULL,
  `chuangwei_shu` INT NULL,
  `fangjian_leixing` VARCHAR(100) NULL,
  `fangjianhao` VARCHAR(100) NULL,
  `louceng` INT NULL,
  `loudong` VARCHAR(100) NULL,
  `tupian` VARCHAR(500) NULL,
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间';

DROP TABLE IF EXISTS `feiyong_zhangdan`;
CREATE TABLE `feiyong_zhangdan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `chuangwei_fei` DECIMAL(12,2) NULL,
  `huli_fei` DECIMAL(12,2) NULL,
  `laoren_id` BIGINT NULL,
  `qita_fei` DECIMAL(12,2) NULL,
  `shengcheng_shijian` DATETIME NULL,
  `yaopin_fei` DECIMAL(12,2) NULL,
  `zhangdan_yuefen` VARCHAR(100) NULL,
  `zhuangtai` VARCHAR(20) NULL,
  `zongjine` DECIMAL(12,2) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_feiyong_zhangdan_laoren_id` (`laoren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='费用账单';

DROP TABLE IF EXISTS `fuyao_jilu`;
CREATE TABLE `fuyao_jilu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `huli_renyuan_id` BIGINT NULL,
  `jihua_id` BIGINT NULL,
  `jihua_shijian` DATETIME NULL,
  `laoren_id` BIGINT NULL,
  `shiji_shijian` DATETIME NULL,
  `yaopin_id` BIGINT NULL,
  `zhixing_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '待执行',
  PRIMARY KEY (`id`),
  KEY `idx_fuyao_jilu_huli_renyuan_id` (`huli_renyuan_id`),
  KEY `idx_fuyao_jilu_jihua_id` (`jihua_id`),
  KEY `idx_fuyao_jilu_laoren_id` (`laoren_id`),
  KEY `idx_fuyao_jilu_yaopin_id` (`yaopin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服药记录';

DROP TABLE IF EXISTS `huli_jibie`;
CREATE TABLE `huli_jibie` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `jibie_bianhao` VARCHAR(100) NULL,
  `jibie_mingcheng` VARCHAR(100) NULL,
  `miaoshu` VARCHAR(1000) NULL,
  `yue_feiyong` DECIMAL(12,2) NULL,
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理级别';

DROP TABLE IF EXISTS `huli_jilu`;
CREATE TABLE `huli_jilu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `huli_neirong` VARCHAR(1000) NULL,
  `huli_renyuan_id` BIGINT NULL,
  `huli_shijian` DATETIME NULL,
  `huli_shuliang` INT NULL,
  `laoren_fankui` VARCHAR(1000) NULL,
  `laoren_id` BIGINT NULL,
  `xiangmu_id` BIGINT NULL,
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_huli_jilu_huli_renyuan_id` (`huli_renyuan_id`),
  KEY `idx_huli_jilu_laoren_id` (`laoren_id`),
  KEY `idx_huli_jilu_xiangmu_id` (`xiangmu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理记录';

DROP TABLE IF EXISTS `huli_renyuan`;
CREATE TABLE `huli_renyuan` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `gonghao` VARCHAR(100) NULL,
  `ruzhi_riqi` DATE NULL,
  `shouji` VARCHAR(100) NULL,
  `xingbie` VARCHAR(100) NULL,
  `xingming` VARCHAR(100) NULL,
  `yonghu_id` BIGINT NULL,
  `zhicheng` VARCHAR(100) NULL,
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_huli_renyuan_yonghu_id` (`yonghu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理员';

DROP TABLE IF EXISTS `huli_xiangmu`;
CREATE TABLE `huli_xiangmu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `danwei` VARCHAR(100) NULL,
  `jiage` DECIMAL(12,2) NULL,
  `miaoshu` VARCHAR(1000) NULL,
  `xiangmu_bianhao` VARCHAR(100) NULL,
  `xiangmu_mingcheng` VARCHAR(100) NULL,
  `zhixing_cishu` INT NULL,
  `zhixing_zhouqi` VARCHAR(100) NULL,
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理项目';

DROP TABLE IF EXISTS `jiankang_jiance`;
CREATE TABLE `jiankang_jiance` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `diya` INT NULL,
  `gaoya` INT NULL,
  `huli_renyuan_id` BIGINT NULL,
  `jiance_shijian` DATETIME NULL,
  `laoren_id` BIGINT NULL,
  `qingkuang` VARCHAR(1000) NULL,
  `tiwen` DECIMAL(6,2) NULL,
  `xinlv` INT NULL,
  `xuetang` DECIMAL(6,2) NULL,
  `xueyang` DECIMAL(6,2) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_jiankang_jiance_huli_renyuan_id` (`huli_renyuan_id`),
  KEY `idx_jiankang_jiance_laoren_id` (`laoren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康监测';

DROP TABLE IF EXISTS `jiaofei_jilu`;
CREATE TABLE `jiaofei_jilu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `caozuoren_id` BIGINT NULL,
  `jiaofei_fangshi` VARCHAR(100) NULL,
  `jiaofei_jine` DECIMAL(12,2) NULL,
  `jiaofei_shijian` DATETIME NULL,
  `laoren_id` BIGINT NULL,
  `zhangdan_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_jiaofei_jilu_caozuoren_id` (`caozuoren_id`),
  KEY `idx_jiaofei_jilu_laoren_id` (`laoren_id`),
  KEY `idx_jiaofei_jilu_zhangdan_id` (`zhangdan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='缴费记录';

DROP TABLE IF EXISTS `jiashu_xinxi`;
CREATE TABLE `jiashu_xinxi` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `dizhi` VARCHAR(1000) NULL,
  `guanxi` VARCHAR(100) NULL,
  `laoren_id` BIGINT NULL,
  `shenfenzheng` VARCHAR(100) NULL,
  `shi_moren` TINYINT NOT NULL DEFAULT 0,
  `shouji` VARCHAR(100) NULL,
  `xingming` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_jiashu_xinxi_laoren_id` (`laoren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家属信息';

DROP TABLE IF EXISTS `jibie_xiangmu`;
CREATE TABLE `jibie_xiangmu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `jibie_id` BIGINT NULL,
  `xiangmu_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_jibie_xiangmu_jibie_id` (`jibie_id`),
  KEY `idx_jibie_xiangmu_xiangmu_id` (`xiangmu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='级别包含项目';

DROP TABLE IF EXISTS `laoren_huli`;
CREATE TABLE `laoren_huli` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `daoqi_riqi` DATE NULL,
  `jibie_id` BIGINT NULL,
  `laoren_id` BIGINT NULL,
  `shezhi_riqi` DATE NULL,
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_laoren_huli_jibie_id` (`jibie_id`),
  KEY `idx_laoren_huli_laoren_id` (`laoren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老人护理级别设置';

DROP TABLE IF EXISTS `laoren_xinxi`;
CREATE TABLE `laoren_xinxi` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `chusheng_riqi` DATE NULL,
  `gengxin_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `hunyin_zhuangkuang` VARCHAR(100) NULL,
  `jiating_zhuzhi` VARCHAR(1000) NULL,
  `laoren_bianhao` VARCHAR(100) NULL,
  `laoren_leixing` VARCHAR(100) NULL,
  `lianxi_dianhua` VARCHAR(100) NULL,
  `nianling` INT NULL,
  `ruzhu_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '未入住',
  `shenfenzheng` VARCHAR(100) NULL,
  `touxiang` VARCHAR(500) NULL,
  `xingbie` VARCHAR(100) NULL,
  `xingming` VARCHAR(100) NULL,
  `xuexing` VARCHAR(100) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老人档案';

DROP TABLE IF EXISTS `laoren_xinxi_xiugai_shenqing`;
CREATE TABLE `laoren_xinxi_xiugai_shenqing` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gengxin_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `jiashu_id` BIGINT NULL,
  `jiashu_xingming` VARCHAR(100) NULL,
  `laoren_id` BIGINT NULL,
  `laoren_xingming` VARCHAR(100) NULL,
  `shanchu_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '正常',
  `shenhe_ren_id` BIGINT NULL,
  `shenhe_ren_xingming` VARCHAR(100) NULL,
  `shenhe_shijian` DATETIME NULL,
  `shenhe_yijian` VARCHAR(1000) NULL,
  `shenqing_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '待审核',
  `xin_beizhu` VARCHAR(1000) NULL,
  `xin_jiating_zhuzhi` VARCHAR(1000) NULL,
  `xin_lianxi_dianhua` VARCHAR(100) NULL,
  `xin_touxiang` VARCHAR(500) NULL,
  `xiugai_shuoming` VARCHAR(1000) NULL,
  `yuan_beizhu` VARCHAR(1000) NULL,
  `yuan_jiating_zhuzhi` VARCHAR(1000) NULL,
  `yuan_lianxi_dianhua` VARCHAR(100) NULL,
  `yuan_touxiang` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_laoren_xinxi_xiugai_shenqing_jiashu_id` (`jiashu_id`),
  KEY `idx_laoren_xinxi_xiugai_shenqing_laoren_id` (`laoren_id`),
  KEY `idx_laoren_xinxi_xiugai_shenqing_shenhe_ren_id` (`shenhe_ren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老人信息变更申请';

DROP TABLE IF EXISTS `renyuan_laoren`;
CREATE TABLE `renyuan_laoren` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `huli_renyuan_id` BIGINT NULL,
  `jieshu_riqi` DATE NULL,
  `kaishi_riqi` DATE NULL,
  `laoren_id` BIGINT NULL,
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_renyuan_laoren_huli_renyuan_id` (`huli_renyuan_id`),
  KEY `idx_renyuan_laoren_laoren_id` (`laoren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理员服务老人';

DROP TABLE IF EXISTS `ruzhu_dengji`;
CREATE TABLE `ruzhu_dengji` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `chuangwei_id` BIGINT NULL,
  `fangjian_id` BIGINT NULL,
  `hetong_daoqi` DATETIME NULL,
  `jingbanren_id` BIGINT NULL,
  `laoren_id` BIGINT NULL,
  `ruzhu_leixing` VARCHAR(100) NULL,
  `ruzhu_shijian` DATETIME NULL,
  `yajin` DECIMAL(12,2) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ruzhu_dengji_chuangwei_id` (`chuangwei_id`),
  KEY `idx_ruzhu_dengji_fangjian_id` (`fangjian_id`),
  KEY `idx_ruzhu_dengji_jingbanren_id` (`jingbanren_id`),
  KEY `idx_ruzhu_dengji_laoren_id` (`laoren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入住登记';

DROP TABLE IF EXISTS `ruzhu_shenqing`;
CREATE TABLE `ruzhu_shenqing` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `banli_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '未办理',
  `beizhu` VARCHAR(1000) NULL,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `chusheng_riqi` DATE NULL,
  `gengxin_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `guominshi` VARCHAR(1000) NULL,
  `huli_xuqiu` VARCHAR(1000) NULL,
  `jiashu_shouji` VARCHAR(100) NULL,
  `jiashu_xingming` VARCHAR(100) NULL,
  `jiating_zhuzhi` VARCHAR(1000) NULL,
  `jiwangbingshi` VARCHAR(1000) NULL,
  `laoren_id` BIGINT NULL,
  `laoren_xingming` VARCHAR(100) NULL,
  `lianxi_dianhua` VARCHAR(100) NULL,
  `nianling` INT NULL,
  `qiwang_ruzhu_shijian` DATETIME NULL,
  `shanchu_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '正常',
  `shenfenzhenghao` VARCHAR(100) NULL,
  `shenhe_ren_id` BIGINT NULL,
  `shenhe_ren_xingming` VARCHAR(100) NULL,
  `shenhe_shijian` DATETIME NULL,
  `shenhe_yijian` VARCHAR(1000) NULL,
  `shenqing_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '待审核',
  `xingbie` VARCHAR(100) NULL,
  `xuexing` VARCHAR(100) NULL,
  `yu_laoren_guanxi` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ruzhu_shenqing_laoren_id` (`laoren_id`),
  KEY `idx_ruzhu_shenqing_shenhe_ren_id` (`shenhe_ren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入住申请';

DROP TABLE IF EXISTS `tuizhu_shenqing`;
CREATE TABLE `tuizhu_shenqing` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `huli_renyuan_id` BIGINT NULL,
  `laoren_id` BIGINT NULL,
  `shanchu_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '正常',
  `shenpi_ren_id` BIGINT NULL,
  `shenpi_shijian` DATETIME NULL,
  `shenpi_yijian` VARCHAR(1000) NULL,
  `shenpi_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '待审批',
  `shenqing_ren_id` BIGINT NULL,
  `shenqing_ren_leixing` VARCHAR(100) NULL,
  `shenqing_ren_xingming` VARCHAR(100) NULL,
  `tuizhu_leixing` VARCHAR(100) NULL,
  `tuizhu_shijian` DATETIME NULL,
  `tuizhu_yuanyin` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_tuizhu_shenqing_huli_renyuan_id` (`huli_renyuan_id`),
  KEY `idx_tuizhu_shenqing_laoren_id` (`laoren_id`),
  KEY `idx_tuizhu_shenqing_shenpi_ren_id` (`shenpi_ren_id`),
  KEY `idx_tuizhu_shenqing_shenqing_ren_id` (`shenqing_ren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退住申请';

DROP TABLE IF EXISTS `waichu_shenqing`;
CREATE TABLE `waichu_shenqing` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `huli_renyuan_id` BIGINT NULL,
  `laoren_id` BIGINT NULL,
  `peitong_dianhua` VARCHAR(100) NULL,
  `peitong_ren` VARCHAR(100) NULL,
  `shanchu_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '正常',
  `shenpi_ren_id` BIGINT NULL,
  `shenpi_shijian` DATETIME NULL,
  `shenpi_yijian` VARCHAR(1000) NULL,
  `shenpi_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '待审批',
  `shenqing_ren_id` BIGINT NULL,
  `shenqing_ren_leixing` VARCHAR(100) NULL,
  `shenqing_ren_xingming` VARCHAR(100) NULL,
  `shiji_huiyuan_shijian` DATETIME NULL,
  `waichu_shijian` DATETIME NULL,
  `waichu_shiyou` VARCHAR(1000) NULL,
  `yuji_huiyuan_shijian` DATETIME NULL,
  PRIMARY KEY (`id`),
  KEY `idx_waichu_shenqing_huli_renyuan_id` (`huli_renyuan_id`),
  KEY `idx_waichu_shenqing_laoren_id` (`laoren_id`),
  KEY `idx_waichu_shenqing_shenpi_ren_id` (`shenpi_ren_id`),
  KEY `idx_waichu_shenqing_shenqing_ren_id` (`shenqing_ren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外出申请';

DROP TABLE IF EXISTS `wuzi_juanzeng`;
CREATE TABLE `wuzi_juanzeng` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `baozhiqi` DATE NULL,
  `beizhu` VARCHAR(1000) NULL,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `danwei` VARCHAR(100) NULL,
  `gengxin_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `guji_jiazhi` DECIMAL(12,2) NULL,
  `ruku_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '已入库',
  `shanchu_zhuangtai` TINYINT NOT NULL DEFAULT 0,
  `shuliang` INT NULL,
  `tupian_url` VARCHAR(500) NULL,
  `wuzi_leibie` VARCHAR(100) NULL,
  `wuzi_mingcheng` VARCHAR(100) NULL,
  `zanzhu_jilu_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_wuzi_juanzeng_zanzhu_jilu_id` (`zanzhu_jilu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物资捐赠';

DROP TABLE IF EXISTS `xitong_juese`;
CREATE TABLE `xitong_juese` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `juese_bianma` VARCHAR(100) NULL,
  `juese_mingcheng` VARCHAR(100) NULL,
  `miaoshu` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_juese_bianma` (`juese_bianma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

DROP TABLE IF EXISTS `xitong_yonghu`;
CREATE TABLE `xitong_yonghu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `denglu_zhanghao` VARCHAR(100) NULL,
  `mima` VARCHAR(100) NULL,
  `shouji` VARCHAR(100) NULL,
  `touxiang` VARCHAR(500) NULL,
  `xingbie` VARCHAR(100) NULL,
  `xingming` VARCHAR(100) NULL,
  `youxiang` VARCHAR(100) NULL,
  `zhuangtai` VARCHAR(20) NULL,
  `zuihou_denglu_shijian` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_denglu_zhanghao` (`denglu_zhanghao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

DROP TABLE IF EXISTS `yaopin_xinxi`;
CREATE TABLE `yaopin_xinxi` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `changjia` VARCHAR(100) NULL,
  `danwei` VARCHAR(100) NULL,
  `guige` VARCHAR(100) NULL,
  `kucun` INT NULL,
  `yaopin_bianhao` VARCHAR(100) NULL,
  `yaopin_mingcheng` VARCHAR(100) NULL,
  `zhuangtai` VARCHAR(20) NULL,
  `zhuyi_shixiang` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='药品';

DROP TABLE IF EXISTS `yonghu_juese`;
CREATE TABLE `yonghu_juese` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `juese_id` BIGINT NULL,
  `yonghu_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_yonghu_juese_juese_id` (`juese_id`),
  KEY `idx_yonghu_juese_yonghu_id` (`yonghu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联';

DROP TABLE IF EXISTS `yongyao_jihua`;
CREATE TABLE `yongyao_jihua` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `jieshu_riqi` DATE NULL,
  `kaishi_riqi` DATE NULL,
  `laoren_id` BIGINT NULL,
  `meici_yongliang` VARCHAR(100) NULL,
  `meiri_cishu` INT NULL,
  `yaopin_id` BIGINT NULL,
  `yizhu` VARCHAR(1000) NULL,
  `yongfa` VARCHAR(100) NULL,
  `yongyao_shijian` VARCHAR(100) NULL,
  `zhuangtai` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_yongyao_jihua_laoren_id` (`laoren_id`),
  KEY `idx_yongyao_jihua_yaopin_id` (`yaopin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用药计划';

DROP TABLE IF EXISTS `zanzhu_jilu`;
CREATE TABLE `zanzhu_jilu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gengxin_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gongkai_zhuangtai` TINYINT NOT NULL DEFAULT 1,
  `pingzheng_tupian` VARCHAR(500) NULL,
  `shanchu_zhuangtai` TINYINT NOT NULL DEFAULT 0,
  `shenhe_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '已确认',
  `zanzhu_biaoti` VARCHAR(100) NULL,
  `zanzhu_fangshi` VARCHAR(100) NULL,
  `zanzhu_jine` DECIMAL(12,2) NULL,
  `zanzhu_ren_id` BIGINT NULL,
  `zanzhu_riqi` DATE NULL,
  `zanzhu_shuoming` VARCHAR(1000) NULL,
  `zanzhu_yongtu` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  KEY `idx_zanzhu_jilu_zanzhu_ren_id` (`zanzhu_ren_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赞助记录';

DROP TABLE IF EXISTS `zanzhu_ren`;
CREATE TABLE `zanzhu_ren` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dizhi` VARCHAR(1000) NULL,
  `gengxin_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gongkai_zhuangtai` TINYINT NOT NULL DEFAULT 1,
  `jianjie` VARCHAR(1000) NULL,
  `lianxi_dianhua` VARCHAR(100) NULL,
  `lianxi_ren` VARCHAR(100) NULL,
  `shanchu_zhuangtai` TINYINT NOT NULL DEFAULT 0,
  `touxiang_url` VARCHAR(500) NULL,
  `youxiang` VARCHAR(100) NULL,
  `zanzhu_leixing` VARCHAR(100) NULL,
  `zanzhu_renming` VARCHAR(100) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赞助人';

DROP TABLE IF EXISTS `zanzhu_yixiang`;
CREATE TABLE `zanzhu_yixiang` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `beizhu` VARCHAR(1000) NULL,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `chuli_beizhu` VARCHAR(1000) NULL,
  `chuli_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '待处理',
  `gengxin_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lianxi_dianhua` VARCHAR(100) NULL,
  `shanchu_zhuangtai` VARCHAR(20) NOT NULL DEFAULT '正常',
  `xingming` VARCHAR(100) NULL,
  `zanzhu_fangshi` VARCHAR(100) NULL,
  `zanzhu_shuoming` VARCHAR(1000) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赞助意向';

DROP TABLE IF EXISTS `zanzhu_yongtu`;
CREATE TABLE `zanzhu_yongtu` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `chuangjian_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gengxin_shijian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gongkai_zhuangtai` TINYINT NOT NULL DEFAULT 1,
  `pingzheng_tupian` VARCHAR(500) NULL,
  `shanchu_zhuangtai` TINYINT NOT NULL DEFAULT 0,
  `shiyong_jine` DECIMAL(12,2) NULL,
  `shiyong_riqi` DATE NULL,
  `shiyong_shuoming` VARCHAR(1000) NULL,
  `yongtu_leibie` VARCHAR(100) NULL,
  `yongtu_mingcheng` VARCHAR(100) NULL,
  `zanzhu_jilu_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_zanzhu_yongtu_zanzhu_jilu_id` (`zanzhu_jilu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赞助资金用途';

-- 公开赞助页使用的视图
CREATE OR REPLACE VIEW v_zanzhu_gongkai AS
SELECT zj.id AS zanzhu_jilu_id, zr.zanzhu_renming, zr.zanzhu_leixing, zr.touxiang_url,
       zj.zanzhu_biaoti, zj.zanzhu_fangshi, zj.zanzhu_jine, zj.zanzhu_riqi, zj.zanzhu_yongtu,
       zj.zanzhu_shuoming, zj.shenhe_zhuangtai
FROM zanzhu_jilu zj
JOIN zanzhu_ren zr ON zj.zanzhu_ren_id = zr.id
WHERE zj.shanchu_zhuangtai = 0 AND zr.shanchu_zhuangtai = 0
  AND zj.gongkai_zhuangtai = 1 AND zr.gongkai_zhuangtai = 1;

CREATE OR REPLACE VIEW v_zanzhu_yongtu_gongkai AS
SELECT zy.id AS yongtu_id, zj.zanzhu_biaoti, zr.zanzhu_renming, zy.yongtu_mingcheng, zy.yongtu_leibie,
       zy.shiyong_jine, zy.shiyong_riqi, zy.shiyong_shuoming
FROM zanzhu_yongtu zy
JOIN zanzhu_jilu zj ON zy.zanzhu_jilu_id = zj.id
JOIN zanzhu_ren zr ON zj.zanzhu_ren_id = zr.id
WHERE zy.shanchu_zhuangtai = 0 AND zj.shanchu_zhuangtai = 0 AND zr.shanchu_zhuangtai = 0
  AND zy.gongkai_zhuangtai = 1 AND zj.gongkai_zhuangtai = 1 AND zr.gongkai_zhuangtai = 1;

SET FOREIGN_KEY_CHECKS = 1;
