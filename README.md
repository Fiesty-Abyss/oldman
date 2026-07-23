# 欧德曼养老院管理系统（oldman）

前后端分离的养老院入住、照护、费用、家属协同与后台运营管理系统（课程实训项目）。

## 项目结构

```
oldman/
├── oldman01/          # 后端 Spring Boot + MyBatis（端口 8081）
├── oldman1/           # 前端 Vue 3 + Vite（端口 8090）
├── uploads/           # 图片等静态资源（URL 前缀 /uploads）
├── docs/              # 说明文档、巡检报告
│   └── database.md    # 数据库建库、导入与表关联（必读）
└── materials/         # 报告、总结等提交材料
```

## 技术栈

| 端 | 技术 |
| --- | --- |
| 后端 | Spring Boot 4、MyBatis、MySQL、HikariCP |
| 前端 | Vue 3、Vite、Vue Router、Axios |
| 数据库 | MySQL，库名 `oldman` |

## 角色与主流程

- **公开端**：首页宣传、家属注册、在线入住申请、公益赞助  
- **管理员**：申请审核、正式入住、床位、护理员/护理项目、审批、费用、赞助、图片、系统用户  
- **护理员**：我的老人、护理记录、健康监测、用药执行、代办申请（仅服务范围内数据）  
- **家属**：绑定老人信息/护理/健康用药/费用查看，外出退住与信息变更申请  

业务闭环：公开申请/注册 → 管理员审核办理 → 护理员执行服务 → 家属查看与发起申请 → 管理员审批维护。

## 数据库（建库与关联）

**完整步骤、表清单、角色绑定规则见：[`docs/database.md`](docs/database.md)。**

摘要：

1. MySQL 中创建库：`CREATE DATABASE oldman DEFAULT CHARACTER SET utf8mb4;`  
2. 导入你本地的全量 SQL（`mysqldump` 或老师提供的脚本）  
3. 修改 `oldman01/src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/oldman?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=你的密码
```

4. 关键关联（实现层）：
   - 用户 ↔ 角色：`yonghu_juese`  
   - 护理员：`huli_renyuan.yonghu_id` → 用户；服务老人：`renyuan_laoren`  
   - 家属：`jiashu_xinxi.shouji` = `xitong_yonghu.shouji`，再通过 `laoren_id` 绑老人  

连通自检：`GET http://localhost:8081/db/ping`

## 快速启动

### 后端

```bash
cd oldman01
# 先改好 application.properties 中的数据源
mvn spring-boot:run
# 或 IDEA 运行 com.neu.Application（Working directory 建议为 oldman01）
```

后端：`http://localhost:8081`

上传目录默认解析为仓库下的 `uploads/`。也可设置环境变量：

```bash
# Windows PowerShell 示例
$env:OLDMAN_UPLOAD_DIR="E:\shixunkecheng\oldman\uploads"
```

### 前端

```bash
cd oldman1
npm install
npm run dev
```

前端：`http://localhost:8090`  
开发代理：`/api/*` → `http://127.0.0.1:8081/*`，`/uploads` 同源代理到后端。

### 演示账号

以你数据库中的实际账号为准。库中常见示例如下（密码以库为准，可在系统管理中重置）：

| 角色 | 账号示例 |
| --- | --- |
| 管理员 | `admin` |
| 护理员 | `huli001` |
| 家属 | `jiashu001` |

## 文档

- [`docs/database.md`](docs/database.md) — **建库、导入、JDBC、表关联、排错**  
- `docs/欧德曼养老院管理系统-答辩巡检报告.md` — 功能与流程巡检  
- `docs/shenqing-liucheng.md` — 外出/退住申请流程设计  
- `docs/audit-*.json` — 接口/权限巡检结果  
- `materials/` — 日报、实训报告、项目总结等  

## 说明

- 已忽略 `node_modules/`、`target/`、`.idea/` 及本地重复备份目录  
- 数据源密码请只写在本地 `application.properties`；仓库已改为**私有**  
- 配置模板可参考 `oldman01/src/main/resources/application.properties.example`  
