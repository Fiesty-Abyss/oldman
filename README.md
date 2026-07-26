# 欧德曼养老院管理系统

前后端分离的养老院业务管理系统（课程实训项目）。

覆盖公开访客、管理员、护理员、家属四类角色，将入住申请与办理、床位管理、护理执行、家属申请与审批、费用登记、公益赞助等环节串成可演示的业务闭环，并在后端按角色限制可访问的数据范围。

---

## 1. 项目简介

养老院运营中，入住资料、床位状态、护理服务与家属协同若依赖表格或口头流程，容易出现审核与占床不分段、护理员数据范围不清、家属知情与档案修改边界模糊等问题。

本项目用 Web 系统完成上述主流程的演示实现：**前端按角色分端，后端在业务层校验角色与数据归属**（不仅依赖菜单隐藏）。

本仓库不宣称高并发、生产级安全或在线支付等能力。

---

## 2. 核心亮点

| 亮点 | 说明 | 代表实现 |
| --- | --- | --- |
| 多角色业务闭环 | 公开申请 → 管理审核/入住 → 护理执行 → 家属申请 → 管理审批 | 路由分端 + 各端业务包 |
| 后端数据范围控制 | 护理员仅服务对象；家属仅绑定老人 | `HuliyuanYewuBizImpl`、`JiashuYewuBizImpl` |
| 入住与占床分阶段 | 审核通过不占床；正式入住才选空闲床位并改状态 | `LaorenXinxiBizImpl.ruzhu` |
| 床位状态联动 | 入住写入使用记录，床位「有人」、老人「在住」 | 同上；床位分配/调换/释放见 `ChuangweiBizImpl` |
| 前后端分离 | Vue3 开发代理对接 Spring Boot；统一 `/uploads` | `vite.config.js`、`WebConfig` |

---

## 3. 技术栈

| 层级 | 技术 |
| --- | --- |
| 后端 | Spring Boot 4.0.6、MyBatis（XML）、MySQL、HikariCP |
| 语言 | Java 25（见 `oldman01/pom.xml`） |
| 前端 | Vue 3、Vue Router、Axios、Vite 7 |
| 鉴权 | 自定义 Token（`oldman-{用户ID}-{时间戳}`），**非 JWT**，未使用 Spring Security |

模块对应：

- `oldman01/`：后端 backend（默认端口 `8081`）
- `oldman1/`：前端 frontend（开发端口 `8090`）

---

## 4. 角色与功能

| 角色 | 编码 | 能做什么 |
| --- | --- | --- |
| 公开访客 | — | 首页、家属注册、入住申请、赞助公开信息/意向 |
| 管理员 | `admin` | 申请审核、正式入住、床位、护理员与护理项目、审批、费用、赞助后台、图片、系统用户等 |
| 护理员 | `huli` | 我的老人、护理记录、健康监测、服药记录、代办申请 |
| 家属 | `jiashu` | 绑定老人信息/护理/健康用药/费用查看；外出、退住、信息变更等申请 |

前端入口：`/` 公开端；登录后按角色进入 `/admin`、`/huliyuan`、`/jiashu`（`oldman1/src/router/index.js`）。

<details>
<summary>页面与模块对照（展开）</summary>

**公开端** `views/duiwai`：首页、注册、入住申请、赞助

**管理端** `views/admin`：入住审核/登记、床位、护理员/项目、审批、信息变更审核、健康用药、费用、赞助、图片、系统管理

**护理员** `views/huliyuan`：我的老人、护理/健康/服药、代办申请

**家属** `views/jiashu`：老人信息、护理、健康用药、费用、申请

后端业务包示例：`gongkai`、`admin`、`chuangwei`、`huli`、`huliguanli`、`huliyuan`、`jiashu`、`laoren`、`wenjian`、`xitong`、`zanzhu` 等。

</details>

---

## 5. 核心业务流程

### 入住

```text
公开提交申请 → 管理员审核（关联资料，不占床）
            → 正式入住（选空闲床位）
            → 入住登记 + 床位使用记录；床位「有人」；老人「在住」
```

### 护理

```text
管理员维护护理员并分配服务老人
    → 护理员仅在服务范围内录入护理 / 健康 / 用药相关记录
```

### 家属协同

```text
家属查看绑定老人信息与记录
    → 提交外出 / 退住 / 信息变更等申请
    → 管理员审批 → 更新申请状态，并按业务联动老人/床位等
```

---

## 6. 系统架构

```text
浏览器
  └─ oldman1（Vue3 + Vite :8090）
        │  /api/* 、 /uploads
        ▼
     oldman01（Spring Boot :8081）
        ├─ Controller → Biz → MyBatis XML
        ├─ MySQL（库名 oldman）
        └─ uploads 目录（静态资源 / 上传文件）
```

开发代理：`/api` → `http://127.0.0.1:8081`（去前缀）；`/uploads` → 后端（`oldman1/vite.config.js`）。

---

## 7. 仓库目录

```text
oldman/
├── README.md
├── oldman01/          # 后端 backend
├── oldman1/           # 前端 frontend
├── uploads/           # 图片等静态资源
├── docs/              # 数据库说明、流程与巡检材料等
└── materials/         # 课程提交材料（非运行必需）
```

`oldman01`、`oldman1` 为历史目录名，请以本说明中的 backend / frontend 含义阅读。

---

## 8. 运行环境

| 项 | 说明 |
| --- | --- |
| 主要开发环境 | Windows（本仓库主要在此环境下开发与演示） |
| JDK | 当前构建配置为 **Java 25**；建议使用对应版本 JDK。若降低 JDK 版本，需自行验证兼容性 |
| Maven | 用于构建/启动后端 |
| Node.js | 前端使用 Vite 7，要求 Node `^20.19.0` 或 `>=22.12.0` |
| MySQL | 建议使用 MySQL 8.x；其他版本未验证 |

---

## 9. 快速启动

### 数据库

1. 创建库 `oldman`（建议 `utf8mb4`）
2. 导入表结构与数据：**仓库不附带完整 SQL 转储**，需使用本地已有库或课程提供的脚本
3. 修改 `oldman01/src/main/resources/application.properties` 中的 URL、用户名、密码

可参考同目录 `application.properties.example`。  
表关联与排错见 [`docs/database.md`](docs/database.md)。

### 后端

```bash
cd oldman01
mvn spring-boot:run
# 或 IDE 运行 com.neu.Application（工作目录建议为 oldman01）
```

- 地址：`http://localhost:8081`
- 检查：`GET /hello`、`GET /db/ping`
- 上传目录默认识别仓库根下 `uploads/`，也可用环境变量 `OLDMAN_UPLOAD_DIR` 指定

### 前端

```bash
cd oldman1
npm install
npm run dev
```

- 地址：`http://localhost:8090`

### 登录

使用**你数据库中已有账号**登录（角色编码 `admin` / `huli` / `jiashu`）。  
请勿在公开文档中依赖或传播默认口令。

---

## 10. 项目截图

截图后续补充。计划覆盖：

1. 公开首页
2. 管理员工作台
3. 入住审核与床位管理
4. 护理员端
5. 家属端

图片建议放在 `docs/images/`。当前仓库尚无截图文件，故不嵌入图片链接。

---

## 11. 已知限制

1. **无随仓库提供的完整数据库 SQL**，仅克隆代码无法还原库表与演示数据。
2. **Token 为简化实现**（`oldman-用户ID-时间戳`），**非 JWT**，无完整过期/吊销体系。
3. **未使用 Spring Security**；接口保护主要依赖业务层校验与前端路由守卫。
4. **系统角色启停/删除不完整**（表字段不足时接口会明确失败）。
5. 家属与登录用户通过**手机号**关联，数据不一致会导致无法绑定。
6. 费用为**线下登记**视角，不含在线支付。
7. `materials/` 等为课程材料，非运行依赖；目录名 `oldman01` / `oldman1` 为历史命名。

---

## 12. 后续计划

- [ ] 补充 `docs/images` 运行截图
- [ ] 整理 docs，移出与运行无关的大包/课程原稿（保留本地备份）
- [ ] 在可分享前提下补充脱敏的库表初始化方式（**待确认**）
- [ ] 按需完善角色管理字段或登录说明

---

## 13. 相关文档

| 文档 | 说明 |
| --- | --- |
| [`docs/database.md`](docs/database.md) | 建库、关联、排错 |
| [`docs/shenqing-liucheng.md`](docs/shenqing-liucheng.md) | 外出/退住流程设计 |
| [`docs/欧德曼养老院管理系统-答辩巡检报告.md`](docs/欧德曼养老院管理系统-答辩巡检报告.md) | 较完整的巡检记录 |
| `oldman01/README.md`、`oldman1/README.md` | 子模块简要说明 |

配置中的数据库密码仅写在本地，勿提交真实密钥。未添加 License 文件。
