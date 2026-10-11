# Changelog

## Unreleased

- 新增 MIT 许可证
- Java 包名 `com.neu` 重命名为 `com.oldman`（含 Maven groupId、Mapper namespace、扫描配置）
- 删除 `uploads/avatar/` 中 3 张疑似真人照片和未使用的 `uploads/下载.jpg`

## 0.2.0 - 2026-10-11

### 新增

- `db/schema.sql`：根据 MyBatis Mapper 整理的完整建表脚本（31 张表 + 2 个公开赞助视图），克隆后即可建库运行
- `db/seed.sql`：虚构演示数据，覆盖三种角色、入住、床位、护理、健康用药、审批、费用与公益赞助
- 数据库连接支持环境变量 `OLDMAN_DB_URL` / `OLDMAN_DB_USER` / `OLDMAN_DB_PASSWORD`
- `scripts/smoke_test.py`：用演示账号登录三种角色，检查全部 GET 接口与角色隔离
- `docs/screenshots/`：20 张各角色主要页面的真实运行截图

### 修复

- Map 结果中的 `DATE` 列被序列化为 UTC 时间戳，家属端床位、用药计划、公益赞助等页面日期显示早一天
- 访问不存在的路径返回 500，改为 404
- 未预期异常把 SQL / 内部错误信息直接返回给前端，改为只记录日志并返回通用提示

### 变更

- 默认头像（`uploads/avatar/user-default.jpg`、`family-default.jpg`）由真人照片换成中性占位图
- README 重写为产品主页：亮点、适用场景、功能总览与截图、业务流程、对比、快速开始、FAQ、路线图
- `docs/database.md` 改为基于 `db/` 脚本的建库说明，补充演示账号；`docs/shenqing-liucheng.md` 按当前实现更新
- 移除一次性的巡检归档（`docs/audit/`）与仓库整理报告

## 0.1.0 - 2026-07-26

- 首个公开版本：Spring Boot + MyBatis 后端（`oldman01`）与 Vue 3 + Vite 前端（`oldman1`）
- 公开端、管理员、护理员、家属四端业务闭环
- 数据源配置占位、上传路径相对化、`.gitignore` / `.editorconfig` / `.gitattributes`
