# oldman1

欧德曼养老院管理系统前端。

## 技术结构

- Vue 3
- Vite
- Vue Router
- Axios

## 运行

WebStorm 打开本目录，先执行依赖安装：

```bash
npm install
```

启动前端：

```bash
npm run dev
```

前端地址：`http://localhost:8090`

## 前后端连接

开发环境通过 Vite 代理连接后端：

```js
/api/* -> http://localhost:8081/*
```

当前页面：

- `/`：对外宣传首页
- `/denglu`：登录页
- `/zhuce`：家属注册占位页
- `/ruzhu-shenqing`：在线入住申请占位页
- `/admin`：管理员后台
- `/huliyuan`：护理员后台
- `/jiashu`：家属后台

后台菜单路由：

- 管理员：`/admin/ruzhu-dengji`、`/admin/chuangwei-fenpei`、`/admin/huli-xiangmu`、`/admin/shenpi`、`/admin/jiankang-yongyao`、`/admin/feiyong`
- 护理员：`/huliyuan/wode-laoren`、`/huliyuan/huli-jilu`、`/huliyuan/jiankang-jiance`、`/huliyuan/fuyao-jilu`、`/huliyuan/daiban-shenqing`
- 家属：`/jiashu/laoren-xinxi`、`/jiashu/huli-jilu`、`/jiashu/jiankang-yongyao`、`/jiashu/feiyong`、`/jiashu/shenqing`

测试账号：

- 管理员：`admin / admin123`
- 护理员：`huli001 / 123456`
- 家属：`jiashu001 / 123456`
