# oldman01

欧德曼养老院管理系统后端，包名 `com.neu`。

## 技术结构

- Spring Boot 4
- MyBatis XML Mapper
- MySQL 数据库 `oldman`
- 全局 CORS；静态资源 `/uploads/**`

## 数据库

**建库、导入、表关联详见仓库根目录 [`docs/database.md`](../docs/database.md)。**

1. 创建库 `oldman`（utf8mb4）
2. 导入仓库根目录的 `db/schema.sql`，需要演示数据再导入 `db/seed.sql`
3. 用环境变量提供连接信息（未设置时使用 `application.properties` 中的默认值）：

```bash
export OLDMAN_DB_USER=oldman
export OLDMAN_DB_PASSWORD=你的密码
# 可选：export OLDMAN_DB_URL='jdbc:mysql://127.0.0.1:3306/oldman?...'
```

## 运行

IntelliJ IDEA 打开本目录，运行 `com.neu.Application`（Working directory 建议为本模块目录）。

```bash
mvn spring-boot:run
# 或打包后运行
mvn -DskipTests package && java -jar target/oldman01-0.0.1-SNAPSHOT.jar
```

- 后端：`http://localhost:8081`
- 上传目录：默认使用仓库根下 `uploads/`，或设置环境变量 `OLDMAN_UPLOAD_DIR`

## 连通接口

- `GET /hello`：后端连通
- `GET /db/ping`：数据库连通
- `POST /denglu/login`：登录
- `GET /kanban/tongji`：首页统计
- `GET /laoren-xinxi/list`：老人列表（按角色控制数据范围）

## 数据范围

- 管理员：全部老人
- 护理员：`huli_renyuan.yonghu_id` → `renyuan_laoren` 服务中的老人
- 家属：`xitong_yonghu.shouji` = `jiashu_xinxi.shouji` → 绑定老人

演示账号见 [`docs/database.md`](../docs/database.md#23-演示账号)：`admin` / `admin123`，`huli001`、`jiashu001` / `123456`。

接口冒烟测试：`python3 ../scripts/smoke_test.py`。
