# oldman1

欧德曼养老院管理系统前端。

## 技术结构

- Vue 3
- Vite
- Vue Router
- Axios

## 运行

```bash
npm install
npm run dev
```

前端：`http://localhost:8090`

## 前后端连接

开发环境通过 Vite 代理连接后端：

```js
/api/* -> http://127.0.0.1:8081/*
/uploads -> http://127.0.0.1:8081
```

请先启动后端 `oldman01`，并按 [`docs/database.md`](../docs/database.md) 配好数据库。

## 页面

- `/`：对外宣传首页
- `/denglu`：登录
- `/zhuce`：家属注册
- `/ruzhu-shenqing`：在线入住申请
- `/admin`：管理员后台
- `/huliyuan`：护理员后台
- `/jiashu`：家属后台

演示账号以数据库为准。
