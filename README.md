# 欧德曼养老院管理系统（oldman）

实训项目：养老院入住、照护、费用、家属协同与后台运营管理。

学号姓名：7230264137 · 苏铭

## 项目结构

```
oldman/
├── oldman01/     # 后端 Spring Boot + MyBatis（端口 8081）
├── oldman1/      # 前端 Vue 3 + Vite（端口 8090）
├── uploads/      # 图片等静态资源（由后端 /uploads 提供）
└── docs/         # 流程说明、巡检报告、成果物
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

## 快速启动

### 1. 数据库

1. 准备 MySQL，创建库 `oldman`
2. 导入建库脚本（本地路径以你机器为准，例如 `oldman_database.sql`）
3. 修改后端配置 `oldman01/src/main/resources/application.properties` 中的数据源地址、账号密码

当前示例配置指向内网虚拟机：

```properties
spring.datasource.url=jdbc:mysql://192.168.46.128:3306/oldman?...
spring.datasource.username=root
spring.datasource.password=******
```

### 2. 后端

```bash
cd oldman01
mvn spring-boot:run
# 或 IDEA 运行 com.neu.Application
```

后端：`http://localhost:8081`

### 3. 前端

```bash
cd oldman1
npm install
npm run dev
```

前端：`http://localhost:8090`  
开发代理：`/api/*` → `http://127.0.0.1:8081/*`，`/uploads` 同源代理到后端。

### 4. 测试账号

| 角色 | 账号 | 密码 |
| --- | --- | --- |
| 管理员 | admin | admin123 |
| 护理员 | huli001 | 123456 |
| 家属 | jiashu001 | 123456 |

## 文档

- `docs/欧德曼养老院管理系统-答辩巡检报告.md` — 功能与流程巡检
- `docs/shenqing-liucheng.md` — 申请流程
- `docs/audit-*.json` — 权限/接口巡检结果
- `ssssss/` — 个人日报、实训报告、项目总结等提交材料

## 说明

- 本仓库已忽略 `node_modules/`、`target/`、`.idea/` 及重复备份目录 `新建文件夹/`
- `uploads/` 含默认图与演示资源，运行时由后端对外提供
- 数据库密码与测试账号仅用于实训演示，请勿用于生产环境
