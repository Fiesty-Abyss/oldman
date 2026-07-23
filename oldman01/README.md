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
2. 导入你的 SQL 转储或实训脚本
3. 编辑本模块配置：

`src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/oldman?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=你的密码
```

也可复制 `application.properties.example` 再改。

## 运行

IntelliJ IDEA 打开本目录，运行 `com.neu.Application`（Working directory 建议为本模块目录）。

```bash
mvn spring-boot:run
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

演示账号以数据库实际数据为准（常见如 `admin` / `huli001` / `jiashu001`）。
