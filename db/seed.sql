-- 欧德曼养老院管理系统：演示数据（虚构人物与数据，仅用于本地体验）
-- 演示账号：admin / admin123，huli001、huli002、jiashu001、jiashu002 / 123456，部署到任何可访问的环境前请修改或删除。
SET NAMES utf8mb4;

INSERT INTO xitong_juese (id, juese_bianma, juese_mingcheng, miaoshu) VALUES
 (1,'admin','管理员','入住审核、床位、护理、审批、费用、赞助与系统管理'),
 (2,'huli','护理员','负责服务老人的护理、健康监测与服药记录'),
 (3,'jiashu','家属','查看绑定老人的信息、记录与费用，提交外出/退住/信息变更申请');

INSERT INTO xitong_yonghu (id, denglu_zhanghao, mima, xingming, xingbie, shouji, youxiang, zhuangtai) VALUES
 (1,'admin','admin123','系统管理员','男','13800000001','admin@example.com','启用'),
 (2,'huli001','123456','周敏','女','13800000011','huli001@example.com','启用'),
 (3,'huli002','123456','陈立','男','13800000012','huli002@example.com','启用'),
 (4,'jiashu001','123456','王晓雯','女','13900000021','jiashu001@example.com','启用'),
 (5,'jiashu002','123456','李建国','男','13900000022','jiashu002@example.com','启用');

INSERT INTO yonghu_juese (yonghu_id, juese_id) VALUES (1,1),(2,2),(3,2),(4,3),(5,3);

INSERT INTO huli_renyuan (id, yonghu_id, gonghao, xingming, xingbie, shouji, zhicheng, ruzhi_riqi, zhuangtai, beizhu) VALUES
 (1,2,'HL001','周敏','女','13800000011','高级护理员','2023-03-01','在职','负责 A 栋 2 层'),
 (2,3,'HL002','陈立','男','13800000012','护理员','2024-06-15','在职','负责 A 栋 3 层');

INSERT INTO fangjian_xinxi (id, fangjianhao, loudong, louceng, fangjian_leixing, chaoxiang, chuangwei_shu, zhuangtai, tupian, beizhu) VALUES
 (1,'A201','A栋',2,'双人间','南',2,'正常','/uploads/default/room-default.jpg','靠近护士站'),
 (2,'A202','A栋',2,'双人间','南',2,'正常','/uploads/default/room-default.jpg',''),
 (3,'A301','A栋',3,'单人间','东南',1,'正常','/uploads/default/room-default.jpg','带独立卫浴'),
 (4,'A302','A栋',3,'三人间','南',3,'正常','/uploads/default/room-default.jpg','');

INSERT INTO chuangwei_xinxi (id, fangjian_id, chuangweihao, zhuangtai, chuangwei_tupian, beizhu) VALUES
 (1,1,'A201-1','有人','/uploads/default/bed-default.jpg',''),
 (2,1,'A201-2','有人','/uploads/default/bed-default.jpg',''),
 (3,2,'A202-1','有人','/uploads/default/bed-default.jpg',''),
 (4,2,'A202-2','空闲','/uploads/default/bed-default.jpg',''),
 (5,3,'A301-1','外出','/uploads/default/bed-default.jpg',''),
 (6,4,'A302-1','有人','/uploads/default/bed-default.jpg',''),
 (7,4,'A302-2','空闲','/uploads/default/bed-default.jpg',''),
 (8,4,'A302-3','空闲','/uploads/default/bed-default.jpg','');

INSERT INTO laoren_xinxi (id, laoren_bianhao, xingming, xingbie, chusheng_riqi, nianling, shenfenzheng, xuexing, hunyin_zhuangkuang, laoren_leixing, ruzhu_zhuangtai, lianxi_dianhua, jiating_zhuzhi, touxiang, beizhu) VALUES
 (1,'LR2026001','王秀兰','女','1945-04-12',81,'110101194504120000','A型','丧偶','护理老人','在住','13900000021','北京市朝阳区示例路 1 号','','高血压，需按时服药'),
 (2,'LR2026002','张德明','男','1948-09-03',78,'110101194809030000','O型','已婚','自理老人','在住','13900000031','北京市海淀区示例路 8 号','','喜欢下棋'),
 (3,'LR2026003','李淑珍','女','1942-01-20',84,'110101194201200000','B型','丧偶','重点关注','在住','13900000022','北京市西城区示例路 3 号','','糖尿病，控制饮食'),
 (4,'LR2026004','刘福生','男','1950-07-15',76,'110101195007150000','AB型','已婚','自理老人','外出','13900000041','北京市东城区示例路 5 号','','国庆随家人外出'),
 (5,'LR2026005','赵桂芳','女','1946-11-30',79,'110101194611300000','A型','已婚','护理老人','在住','13900000051','北京市丰台区示例路 9 号','',''),
 (6,'LR2026006','孙永平','男','1949-02-08',77,'110101194902080000','O型','已婚','待完善','未入住','13900000061','北京市通州区示例路 2 号','','入住申请已审核，待办理入住');

INSERT INTO jiashu_xinxi (laoren_id, xingming, guanxi, shouji, shenfenzheng, dizhi, shi_moren, beizhu) VALUES
 (1,'王晓雯','女儿','13900000021','','北京市朝阳区示例路 1 号',1,''),
 (3,'李建国','儿子','13900000022','','北京市西城区示例路 3 号',1,''),
 (2,'张伟','儿子','13900000031','','北京市海淀区示例路 8 号',1,''),
 (4,'刘洋','儿子','13900000041','','北京市东城区示例路 5 号',1,''),
 (5,'赵敏','女儿','13900000051','','北京市丰台区示例路 9 号',1,''),
 (6,'孙磊','儿子','13900000061','','北京市通州区示例路 2 号',1,'');

INSERT INTO renyuan_laoren (huli_renyuan_id, laoren_id, kaishi_riqi, zhuangtai, beizhu) VALUES
 (1,1,'2026-03-01','服务中',''),(1,2,'2026-04-10','服务中',''),(1,3,'2026-05-20','服务中',''),
 (2,4,'2026-06-01','服务中',''),(2,5,'2026-07-01','服务中','');

INSERT INTO ruzhu_dengji (laoren_id, fangjian_id, chuangwei_id, ruzhu_shijian, ruzhu_leixing, hetong_daoqi, yajin, jingbanren_id, beizhu) VALUES
 (1,1,1,'2026-03-01 09:30:00','长期入住','2027-02-28 00:00:00',3000,1,''),
 (2,1,2,'2026-04-10 10:00:00','长期入住','2027-04-09 00:00:00',3000,1,''),
 (3,2,3,'2026-05-20 14:00:00','长期入住','2027-05-19 00:00:00',3000,1,''),
 (4,3,5,'2026-06-01 09:00:00','长期入住','2027-05-31 00:00:00',5000,1,''),
 (5,4,6,'2026-07-01 15:00:00','短期托养','2026-12-31 00:00:00',2000,1,'');

INSERT INTO chuangwei_shiyong (chuangwei_id, fangjian_id, laoren_id, kaishi_riqi, shiyong_zhuangtai, yuanyin) VALUES
 (1,1,1,'2026-03-01','正在使用','入住'),(2,1,2,'2026-04-10','正在使用','入住'),(3,2,3,'2026-05-20','正在使用','入住'),
 (5,3,4,'2026-06-01','正在使用','入住'),(6,4,5,'2026-07-01','正在使用','入住');

INSERT INTO ruzhu_shenqing (laoren_xingming, xingbie, nianling, chusheng_riqi, shenfenzhenghao, xuexing, lianxi_dianhua, jiating_zhuzhi, jiwangbingshi, guominshi, huli_xuqiu, qiwang_ruzhu_shijian, jiashu_xingming, jiashu_shouji, yu_laoren_guanxi, shenqing_zhuangtai, banli_zhuangtai, laoren_id, shenhe_ren_id, shenhe_ren_xingming, shenhe_shijian, shenhe_yijian, beizhu) VALUES
 ('孙永平','男',77,'1949-02-08','110101194902080000','O型','13900000061','北京市通州区示例路 2 号','轻度关节炎','无','日常起居协助','2026-10-15 00:00:00','孙磊','13900000061','儿子','已通过','未办理',6,1,'系统管理员','2026-10-09 10:20:00','资料齐全，待办理入住',''),
 ('周桂英','女',82,'1944-08-18','110101194408180000','A型','13900000071','北京市大兴区示例路 6 号','高血压','青霉素','需要夜间看护','2026-10-20 00:00:00','周强','13900000071','儿子','待审核','未办理',NULL,NULL,NULL,NULL,NULL,''),
 ('马国庆','男',75,'1951-10-01','110101195110010000','B型','13900000081','北京市昌平区示例路 7 号','无','无','自理，偏好单人间','2026-11-01 00:00:00','马丽','13900000081','女儿','待审核','未办理',NULL,NULL,NULL,NULL,NULL,'');

INSERT INTO huli_jibie (id, jibie_bianhao, jibie_mingcheng, yue_feiyong, miaoshu, zhuangtai) VALUES
 (1,'JB01','自理照护',800,'日常巡视、生活提醒','启用'),
 (2,'JB02','半护理',1800,'协助起居、服药提醒、定期测量','启用'),
 (3,'JB03','全护理',3200,'全天照护、翻身、助餐、重点监测','启用');

INSERT INTO huli_xiangmu (id, xiangmu_bianhao, xiangmu_mingcheng, jiage, danwei, zhixing_zhouqi, zhixing_cishu, miaoshu, zhuangtai) VALUES
 (1,'XM01','日常巡视',0,'次','每天',3,'早中晚各巡视一次','启用'),
 (2,'XM02','血压测量',10,'次','每天',1,'测量并记录血压','启用'),
 (3,'XM03','协助洗浴',30,'次','每周',2,'协助洗浴与更衣','启用'),
 (4,'XM04','康复训练',50,'次','每周',3,'肢体康复训练 30 分钟','启用'),
 (5,'XM05','助餐服务',15,'次','每天',3,'协助用餐','启用');

INSERT INTO jibie_xiangmu (jibie_id, xiangmu_id) VALUES (1,1),(2,1),(2,2),(2,3),(3,1),(3,2),(3,3),(3,4),(3,5);

INSERT INTO laoren_huli (laoren_id, jibie_id, shezhi_riqi, daoqi_riqi, zhuangtai, beizhu) VALUES
 (1,2,'2026-03-01','2027-02-28','生效',''),(2,1,'2026-04-10','2027-04-09','生效',''),(3,3,'2026-05-20','2027-05-19','生效',''),
 (4,1,'2026-06-01','2027-05-31','生效',''),(5,2,'2026-07-01','2026-12-31','生效','');

INSERT INTO huli_jilu (laoren_id, huli_renyuan_id, xiangmu_id, huli_shijian, huli_shuliang, huli_neirong, laoren_fankui, zhuangtai) VALUES
 (1,1,2,'2026-10-11 08:10:00',1,'晨间血压 132/84，状态平稳','精神不错','正常'),
 (1,1,3,'2026-10-10 16:00:00',1,'协助洗浴，更换衣物','满意','正常'),
 (3,1,4,'2026-10-11 09:30:00',1,'下肢康复训练 30 分钟','有些累','正常'),
 (3,1,5,'2026-10-11 12:00:00',1,'午餐协助，进食约八成','','正常'),
 (2,1,1,'2026-10-11 07:30:00',1,'早间巡视，已起床晨练','','正常'),
 (5,2,2,'2026-10-11 08:40:00',1,'血压 128/80','','正常');

INSERT INTO jiankang_jiance (laoren_id, huli_renyuan_id, jiance_shijian, tiwen, gaoya, diya, xinlv, xuetang, xueyang, qingkuang) VALUES
 (1,1,'2026-10-09 08:00:00',36.5,135,86,72,5.6,97,'平稳'),
 (1,1,'2026-10-10 08:00:00',36.6,138,88,75,5.8,96,'平稳'),
 (1,1,'2026-10-11 08:10:00',36.4,132,84,70,5.5,98,'平稳'),
 (3,1,'2026-10-11 07:50:00',36.7,126,78,78,8.2,96,'空腹血糖偏高，已告知医生'),
 (5,2,'2026-10-11 08:40:00',36.5,128,80,74,5.9,97,'正常');

INSERT INTO yaopin_xinxi (id, yaopin_bianhao, yaopin_mingcheng, guige, danwei, changjia, kucun, zhuyi_shixiang, zhuangtai) VALUES
 (1,'YP001','苯磺酸氨氯地平片','5mg×28片','盒','示例制药',40,'饭后服用','启用'),
 (2,'YP002','二甲双胍缓释片','0.5g×30片','盒','示例制药',35,'随餐服用','启用'),
 (3,'YP003','阿托伐他汀钙片','20mg×7片','盒','示例制药',20,'睡前服用','启用');

INSERT INTO yongyao_jihua (id, laoren_id, yaopin_id, meici_yongliang, meiri_cishu, yongfa, yongyao_shijian, kaishi_riqi, jieshu_riqi, yizhu, zhuangtai) VALUES
 (1,1,1,'1片',1,'口服','08:30','2026-09-01','2026-12-31','监测血压','生效'),
 (2,3,2,'1片',2,'口服','08:00,18:00','2026-09-01','2026-12-31','随餐','生效'),
 (3,3,3,'1片',1,'口服','21:00','2026-09-15','2026-12-31','','生效');

INSERT INTO fuyao_jilu (jihua_id, laoren_id, yaopin_id, huli_renyuan_id, jihua_shijian, shiji_shijian, zhixing_zhuangtai, beizhu) VALUES
 (1,1,1,1,'2026-10-11 08:30:00','2026-10-11 08:35:00','已服用',''),
 (2,3,2,1,'2026-10-11 08:00:00','2026-10-11 08:05:00','已服用',''),
 (3,3,3,1,'2026-10-10 21:00:00','2026-10-10 21:10:00','已服用','');

INSERT INTO feiyong_zhangdan (id, laoren_id, zhangdan_yuefen, chuangwei_fei, huli_fei, yaopin_fei, qita_fei, zongjine, zhuangtai, shengcheng_shijian, beizhu) VALUES
 (1,1,'2026-09',2500,1800,120,0,4420,'已结清','2026-09-01 09:00:00',''),
 (2,1,'2026-10',2500,1800,120,50,4470,'待缴费','2026-10-01 09:00:00',''),
 (3,3,'2026-10',2500,3200,260,0,5960,'部分缴费','2026-10-01 09:00:00',''),
 (4,2,'2026-10',2500,800,0,0,3300,'已结清','2026-10-01 09:00:00',''),
 (5,5,'2026-10',2500,1800,60,0,4360,'待缴费','2026-10-01 09:00:00','');

INSERT INTO jiaofei_jilu (zhangdan_id, laoren_id, jiaofei_jine, jiaofei_fangshi, jiaofei_shijian, caozuoren_id, beizhu) VALUES
 (1,1,4420,'银行转账','2026-09-05 10:00:00',1,''),
 (3,3,3000,'现金','2026-10-03 15:00:00',1,'剩余部分下周补交'),
 (4,2,3300,'微信','2026-10-02 11:00:00',1,'');

INSERT INTO waichu_shenqing (laoren_id, huli_renyuan_id, shenqing_ren_id, shenqing_ren_xingming, shenqing_ren_leixing, waichu_shiyou, waichu_shijian, yuji_huiyuan_shijian, peitong_ren, peitong_dianhua, shenpi_zhuangtai, shenpi_ren_id, shenpi_shijian, shenpi_yijian) VALUES
 (4,2,4,'刘洋','家属','国庆假期回家团聚','2026-10-01 09:00:00','2026-10-12 18:00:00','刘洋','13900000041','通过',1,'2026-09-29 10:00:00','注意按时服药'),
 (1,1,1,'王晓雯','家属','周末陪同去医院复查','2026-10-13 08:30:00','2026-10-13 16:00:00','王晓雯','13900000021','待审批',NULL,NULL,NULL),
 (2,1,1,'周敏','护理员','家属接老人参加婚宴','2026-10-18 10:00:00','2026-10-18 20:00:00','张伟','13900000031','待审批',NULL,NULL,NULL);

INSERT INTO tuizhu_shenqing (laoren_id, huli_renyuan_id, shenqing_ren_id, shenqing_ren_xingming, shenqing_ren_leixing, tuizhu_leixing, tuizhu_shijian, tuizhu_yuanyin, shenpi_zhuangtai) VALUES
 (5,2,5,'赵敏','家属','正常退住','2026-12-31 10:00:00','短期托养到期，回家居住','待审批');

INSERT INTO laoren_xinxi_xiugai_shenqing (laoren_id, laoren_xingming, jiashu_id, jiashu_xingming, yuan_lianxi_dianhua, xin_lianxi_dianhua, yuan_jiating_zhuzhi, xin_jiating_zhuzhi, yuan_beizhu, xin_beizhu, xiugai_shuoming, shenqing_zhuangtai) VALUES
 (1,'王秀兰',1,'王晓雯','13900000021','13900000021','北京市朝阳区示例路 1 号','北京市朝阳区示例路 18 号','高血压，需按时服药','高血压，需按时服药；对海鲜过敏','家庭住址变更，补充过敏信息','待审核');

INSERT INTO zanzhu_ren (id, zanzhu_renming, zanzhu_leixing, lianxi_ren, lianxi_dianhua, youxiang, dizhi, jianjie, touxiang_url, gongkai_zhuangtai) VALUES
 (1,'晨光公益基金会','机构','林女士','01080000001','contact@example.org','北京市','长期关注社区养老服务','/uploads/zanzhu/donor-default.jpg',1),
 (2,'爱心企业示例科技','企业','赵先生','01080000002','csr@example.com','北京市','员工志愿服务与物资捐赠','/uploads/zanzhu/donor-default.jpg',1),
 (3,'匿名爱心人士','个人','','','','','','',1);

INSERT INTO zanzhu_jilu (id, zanzhu_ren_id, zanzhu_biaoti, zanzhu_fangshi, zanzhu_jine, zanzhu_riqi, zanzhu_yongtu, zanzhu_shuoming, pingzheng_tupian, gongkai_zhuangtai, shenhe_zhuangtai) VALUES
 (1,1,'2026 秋季康复设备捐助','资金',50000,'2026-09-20','康复设备采购','用于采购康复训练器材','/uploads/zanzhu/certificate-default.jpg',1,'已确认'),
 (2,2,'重阳节慰问物资','物资',0,'2026-10-08','节日慰问','捐赠牛奶、护膝等物资','/uploads/zanzhu/material-default.jpg',1,'已确认'),
 (3,3,'助老餐补','资金',2000,'2026-10-05','膳食改善','','',1,'已确认');

INSERT INTO zanzhu_yongtu (zanzhu_jilu_id, yongtu_mingcheng, yongtu_leibie, shiyong_jine, shiyong_riqi, shiyong_shuoming, pingzheng_tupian, gongkai_zhuangtai) VALUES
 (1,'上下肢康复训练器','设备采购',32000,'2026-09-28','采购 4 台，已投入使用','',1),
 (3,'重阳节加餐','膳食改善',1500,'2026-10-09','重阳节午餐加菜','',1);

INSERT INTO wuzi_juanzeng (zanzhu_jilu_id, wuzi_mingcheng, wuzi_leibie, shuliang, danwei, guji_jiazhi, baozhiqi, ruku_zhuangtai, tupian_url, beizhu) VALUES
 (2,'纯牛奶','食品',50,'箱',3000,'2027-03-01','已入库','',''),
 (2,'保暖护膝','生活用品',80,'副',2400,NULL,'已入库','','');

INSERT INTO zanzhu_yixiang (xingming, lianxi_dianhua, zanzhu_fangshi, zanzhu_shuoming, chuli_zhuangtai, beizhu) VALUES
 ('钱先生','13700000001','资金','想为老人们资助冬季取暖费用','待联系',''),
 ('示例书店','01080000003','物资','捐赠大字版图书 200 册','已联系','已约定 10 月下旬送达');
