# oldman01

欧德曼养老院管理系统后端，包名 `com.neu`。

## 技术结构

- Spring Boot 4
- MyBatis XML Mapper
- MySQL 数据库 `oldman`
- 全局 CORS 配置，支持前端跨域访问

## 数据库

连接信息来自旧后端项目，数据库名改为 `oldman`：

```properties
spring.datasource.url=jdbc:mysql://192.168.46.128:3306/oldman
spring.datasource.username=root
spring.datasource.password=654321
```

请先在 MySQL 中执行 `E:/2026shixun/uploads/oldman_database.sql`。

## 运行

IntelliJ IDEA 打开本目录，运行 `com.neu.Application`。

也可以使用命令：

```bash
mvn spring-boot:run
```

后端地址：`http://localhost:8081`

## 当前接口

- `GET /hello`：后端连通测试
- `GET /db/ping`：数据库连通测试
- `POST /denglu/login`：登录
- `GET /kanban/tongji`：首页统计
- `GET /laoren-xinxi/list`：老人列表，按当前登录用户角色控制数据范围

## 数据范围

- 管理员：查看全部老人。
- 护理员：通过 `huli_renyuan.yonghu_id -> renyuan_laoren` 查看自己服务的老人。
- 家属：通过 `xitong_yonghu.shouji -> jiashu_xinxi.shouji -> laoren_xinxi` 查看绑定老人。

测试账号：

- 管理员：`admin / admin123`
- 护理员：`huli001 / 123456`
- 家属：`jiashu001 / 123456`
