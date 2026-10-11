# 外出与退住申请流程设计

本文说明外出与退住申请的来源、字段、状态流转和审批联动，对应管理端“外出与退住审批”、家属端“外出退住申请”、护理员端“代办外出退住”三个页面。

## 申请来源

外出申请和退住申请支持两类来源：

- 家属提交：家属只能为 `jiashu_xinxi` 绑定的老人提交申请。
- 护理员代办：护理员只能为 `renyuan_laoren` 分配给自己的老人代提交申请。

## 申请人字段

`waichu_shenqing` 和 `tuizhu_shenqing` 除老人、护理员、审批人、审批状态外，还记录申请人：

| 字段 | 说明 |
| --- | --- |
| `shenqing_ren_id` | 家属提交时为 `jiashu_xinxi.id`；护理员代办时为 `huli_renyuan.id` |
| `shenqing_ren_leixing` | `家属` / `护理员` |
| `shenqing_ren_xingming` | 申请人姓名（冗余存储，便于列表展示） |

## 状态流转

审批状态（`shenpi_zhuangtai`）：

- `待审批`
- `通过`
- `不通过`
- 外出申请额外有 `已回院`

## 管理员审批动作

外出审批通过：

- 更新 `waichu_shenqing.shenpi_zhuangtai='通过'`
- 写入 `shenpi_ren_id`、`shenpi_shijian`、`shenpi_yijian`
- 同步更新 `laoren_xinxi.ruzhu_zhuangtai='外出'`
- 同步更新当前床位 `chuangwei_xinxi.zhuangtai='外出'`

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
