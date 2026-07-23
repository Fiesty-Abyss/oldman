# 外出与退住申请流程设计

本文件用于约束后续审批接口和表结构扩展，当前阶段先完成入口、路由和占位页面。

## 申请来源

外出申请和退住申请支持两类来源：

- 家属提交：家属只能为 `jiashu_xinxi` 绑定的老人提交申请。
- 护理员代办：护理员只能为 `renyuan_laoren` 分配给自己的老人代提交申请。

## 建议补充字段

现有 `waichu_shenqing` 和 `tuizhu_shenqing` 已有老人、护理员、审批人、审批状态等字段。为了完整记录申请人，后续建议给两张表都补充：

```sql
shenqingren_id BIGINT COMMENT '申请人ID，家属或护理员对应记录ID',
shenqingren_leixing VARCHAR(20) COMMENT '申请人类型：家属/护理员',
shenqing_yuanyin VARCHAR(255) COMMENT '申请原因',
shenqing_shijian DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间'
```

其中：

- 家属提交时，`shenqingren_id` 对应 `jiashu_xinxi.id`，`shenqingren_leixing='家属'`。
- 护理员代办时，`shenqingren_id` 对应 `huli_renyuan.id`，`shenqingren_leixing='护理员'`。

## 状态流转

申请状态建议统一使用：

- `待审批`
- `通过`
- `不通过`
- 外出申请额外支持 `已回院`

## 管理员审批动作

外出审批通过：

- 更新 `waichu_shenqing.shenpi_zhuangtai='通过'`
- 写入 `shenpi_ren_id`、`shenpi_shijian`、`shenpi_yijian`
- 可同步更新 `laoren_xinxi.ruzhu_zhuangtai='外出'`
- 可同步更新当前床位 `chuangwei_xinxi.zhuangtai='外出'`

外出回院登记：

- 写入 `shiji_huiyuan_shijian`
- 更新老人状态为 `在住`
- 更新床位状态为 `有人`

退住审批通过：

- 更新 `tuizhu_shenqing.shenpi_zhuangtai='通过'`
- 写入 `shenpi_ren_id`、`shenpi_shijian`、`shenpi_yijian`
- 更新 `laoren_xinxi.ruzhu_zhuangtai='退住'`
- 终止当前 `chuangwei_shiyong` 有效记录
- 释放床位，更新 `chuangwei_xinxi.zhuangtai='空闲'`

退住审批不通过：

- 只写入审批状态、审批人、审批时间、审批意见，不改变老人和床位状态。
