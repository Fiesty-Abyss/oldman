# 数据库说明：建库、导入与程序关联

本文说明 MySQL 库 `oldman` 如何准备，以及后端如何连接、业务表如何关联。

> 仓库内**不附带完整建表 SQL 转储**（体积与环境差异较大）。请从你实训时的 MySQL 导出，或按下方表清单自行建库后导入历史数据。

---

## 1. 环境要求

| 项 | 建议 |
| --- | --- |
| 数据库 | 建议使用 MySQL 8.x；其他版本未验证 |
| 字符集 | `utf8mb4` / `utf8mb4_general_ci` 或 `utf8mb4_unicode_ci` |
| 库名 | **`oldman`**（与配置一致，可改但需同步改 JDBC URL） |
| 后端 | `oldman01`，默认端口 `8081` |
| 连接配置 | `oldman01/src/main/resources/application.properties` |

---

## 2. 建库步骤

### 2.1 创建空库

在 MySQL 客户端（命令行 / Navicat / DataGrip / IDEA Database）执行：

```sql
CREATE DATABASE IF NOT EXISTS oldman
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

USE oldman;
```

### 2.2 导入表结构与数据（推荐）

若你本地已有完整库，用 mysqldump 导出再导入到新环境：

```bash
# 在「有数据的那台机器」导出（示例）
mysqldump -u root -p --databases oldman --default-character-set=utf8mb4 > oldman_full.sql

# 在目标机器导入
mysql -u root -p < oldman_full.sql
```

只导出结构：

```bash
mysqldump -u root -p --no-data --databases oldman > oldman_schema.sql
```

若老师/项目组提供了 `oldman_database.sql` 一类脚本，直接：

```bash
mysql -u root -p oldman < /path/to/oldman_database.sql
```

### 2.3 最小角色与演示账号（无全量数据时）

导入完整脚本后一般已有演示账号。若只有空表，至少需要：

1. 表 `xitong_juese` 中存在角色编码：
   - `admin`（管理员）
   - `huli`（护理员）
   - `jiashu`（家属）
2. 表 `xitong_yonghu` 中有对应用户
3. 表 `yonghu_juese` 绑定用户与角色
4. 护理员还需 `huli_renyuan.yonghu_id` 指向登录用户
5. 家属通过 **手机号** 与 `jiashu_xinxi.shouji` 对齐（见下文关联）

常见演示账号（以你库中实际数据为准，可在系统管理中重置）：

| 角色 | 登录账号（示例） | 说明 |
| --- | --- | --- |
| 管理员 | `admin` | `juese_bianma = admin` |
| 护理员 | `huli001` | 需有 `huli_renyuan` + `renyuan_laoren` |
| 家属 | `jiashu001` | 手机号需能关联到 `jiashu_xinxi` |

---

## 3. 与程序如何关联

### 3.1 JDBC 连接

文件：`oldman01/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/oldman?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=你的密码
```

| 配置项 | 含义 |
| --- | --- |
| 主机端口 | `127.0.0.1:3306` 改成你的 MySQL 地址（虚拟机则写虚拟机 IP） |
| 库名 | URL 路径中的 `oldman` |
| 用户名/密码 | MySQL 账号，**不要**把真实密码推到公开仓库 |
| 时区 | `serverTimezone=Asia/Shanghai`，避免时间差 8 小时 |
| MyBatis | `mapper/*.xml` + 下划线转驼峰 |

启动后端后可用：

- `GET http://localhost:8081/hello` — 服务是否起来  
- `GET http://localhost:8081/db/ping` — 数据库是否连通  

### 3.2 静态资源 uploads

- 磁盘目录：仓库根目录下的 `uploads/`
- URL 前缀：`http://localhost:8081/uploads/...`
- 代码：`WebConfig` 将 `/uploads/**` 映射到该目录  
- 也可用环境变量覆盖：`OLDMAN_UPLOAD_DIR=D:/path/to/uploads`
- 前端开发时 Vite 把 `/uploads` 代理到 `8081`（见 `oldman1/vite.config.js`）

### 3.3 前端如何打到后端

- 开发：浏览器访问 `http://localhost:8090`，接口走 `/api/...`，由 Vite 代理到 `8081` 并去掉 `/api` 前缀  
- 鉴权：登录后前端携带 token（自定义轻量 token，非 JWT）

---

## 4. 核心表与业务关联（ER 逻辑）

代码中实际用到的主要表（由 MyBatis XML 汇总）：

### 4.1 账号与角色

```
xitong_yonghu          登录用户（账号、密码、姓名、手机、状态…）
xitong_juese           角色（juese_bianma: admin / huli / jiashu）
yonghu_juese           用户 ↔ 角色（多对多）
```

- 登录：按 `denglu_zhanghao` 查用户，再带出角色列表  
- 权限：各 Biz 根据当前用户角色编码分支

### 4.2 老人、家属、护理员

```
laoren_xinxi                 老人主档（入住状态、联系方式、头像…）
jiashu_xinxi                 家属业务信息（laoren_id、手机、关系…）
huli_renyuan                 护理员业务信息（yonghu_id 关联登录用户）
renyuan_laoren               护理员服务老人（zhuangtai='服务中'）
```

**关联规则（实现要点）：**

| 关系 | 关联方式 |
| --- | --- |
| 用户 → 护理员 | `huli_renyuan.yonghu_id = xitong_yonghu.id` |
| 护理员 → 老人 | `renyuan_laoren`：`huli_renyuan_id` + `laoren_id`，状态 `服务中` |
| 用户 → 家属 | **`jiashu_xinxi.shouji = xitong_yonghu.shouji`**（手机号绑定，不是外键用户 ID） |
| 家属 → 老人 | `jiashu_xinxi.laoren_id = laoren_xinxi.id` |

因此：家属注册/改手机时，必须与 `jiashu_xinxi.shouji` 一致，否则家属端会提示未绑定老人。

### 4.3 入住闭环

```
ruzhu_shenqing     公开端入住申请（待审核 / 已通过…，banli_zhuangtai 办理状态）
ruzhu_dengji       正式入住登记
fangjian_xinxi     房间
chuangwei_xinxi    床位（空闲 / 有人 / 外出…）
chuangwei_shiyong  床位使用记录（正在使用 / 已结束）
```

流程简述：

1. 公开提交 → `ruzhu_shenqing`  
2. 管理员审核通过 → 创建/关联 `laoren_xinxi`、`jiashu_xinxi`（**不占床**）  
3. 正式入住 → `ruzhu_dengji` + 占用 `chuangwei_shiyong` + 床位改「有人」+ 老人「在住」

### 4.4 护理与健康

```
huli_xiangmu / huli_jibie / jibie_xiangmu / laoren_huli
huli_jilu
jiankang_jiance
yaopin_xinxi / yongyao_jihua / fuyao_jilu
```

护理员写记录前会校验：老人是否在自己的 `renyuan_laoren` 服务范围内；护理项目是否属于老人当前护理级别。

### 4.5 申请与审批

```
waichu_shenqing              外出
tuizhu_shenqing              退住
laoren_xinxi_xiugai_shenqing 老人信息变更（家属提交，管理员审过后写回 laoren_xinxi）
```

通过后会联动老人 `ruzhu_zhuangtai` 与床位状态（外出/退住释放等）。

### 4.6 费用

```
feiyong_zhangdan   账单
jiaofei_jilu       线下缴费登记
```

管理员可写；家属只读自己绑定老人的费用。

### 4.7 赞助（含视图）

```
zanzhu_ren / zanzhu_jilu / zanzhu_yongtu / zanzhu_yixiang / wuzi_juanzeng
v_zanzhu_gongkai / v_zanzhu_yongtu_gongkai   （公开页查询用视图）
```

若导入结构后公开赞助页报错，检查这两个 **VIEW** 是否存在。

---

## 5. 表清单（代码引用）

| 表/视图 | 用途 |
| --- | --- |
| `xitong_yonghu` | 系统用户 |
| `xitong_juese` | 角色 |
| `yonghu_juese` | 用户角色 |
| `laoren_xinxi` | 老人 |
| `jiashu_xinxi` | 家属 |
| `huli_renyuan` | 护理员 |
| `renyuan_laoren` | 护理分配 |
| `fangjian_xinxi` / `chuangwei_xinxi` / `chuangwei_shiyong` | 房床 |
| `ruzhu_shenqing` / `ruzhu_dengji` | 申请与入住 |
| `huli_xiangmu` / `huli_jibie` / `jibie_xiangmu` / `laoren_huli` | 护理配置 |
| `huli_jilu` / `jiankang_jiance` | 护理与健康 |
| `yaopin_xinxi` / `yongyao_jihua` / `fuyao_jilu` | 药品与服药 |
| `waichu_shenqing` / `tuizhu_shenqing` / `laoren_xinxi_xiugai_shenqing` | 申请 |
| `feiyong_zhangdan` / `jiaofei_jilu` | 费用 |
| `zanzhu_*` / `wuzi_juanzeng` / `v_zanzhu_*` | 赞助 |

字段以你库中实际定义为准；Mapper 的 `insert`/`select` 列名是最可靠的对照。

---

## 6. 联调检查清单

1. MySQL 已启动，库名 `oldman`，账号能远程/本地登录  
2. `application.properties` 中 URL、用户、密码正确  
3. `mvn spring-boot:run`（或 IDEA 运行 `Application`）无报连库失败  
4. `/db/ping` 成功  
5. `uploads` 目录存在且 `WebConfig` 能解析到（或设置 `OLDMAN_UPLOAD_DIR`）  
6. 前端 `npm run dev`，登录页用库中真实账号登录  
7. 管理员能看到老人列表；护理员/家属数据范围符合分配与绑定  

---

## 7. 常见问题

| 现象 | 处理 |
| --- | --- |
| `Communications link failure` | 检查 MySQL 是否启动、IP/端口、防火墙、bind-address |
| `Access denied` | 用户名密码或主机权限（`user@%` / `user@localhost`） |
| `Unknown database 'oldman'` | 先执行 `CREATE DATABASE` |
| 时区警告 / 时间不对 | URL 加 `serverTimezone=Asia/Shanghai` |
| 家属登录后无老人 | 检查 `jiashu_xinxi.shouji` 是否等于用户 `shouji` |
| 护理员提示只能操作自己负责的老人 | 在管理端分配 `renyuan_laoren` 且状态为「服务中」 |
| 图片 404 | 确认 `uploads` 路径与 `OLDMAN_UPLOAD_DIR`，URL 是否为 `/uploads/...` |
| 赞助公开接口报错 | 检查视图 `v_zanzhu_gongkai`、`v_zanzhu_yongtu_gongkai` |

---

## 8. 安全提示

- 数据库账号密码与演示账号仅限本地使用，勿写入公开文档或提交到 Git  
- 提交前检查 `application.properties` 是否仍为占位符，勿推送真实密码  
- 对外分享或长期部署时，请使用独立账号与强密码，并考虑配置外置（环境变量等）
