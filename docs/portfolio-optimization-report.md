# 校招作品集：GitHub 仓库优化报告

**仓库：** https://github.com/Fiesty-Abyss/oldman  
**本地路径：** `E:\shixunkecheng\oldman`  
**报告日期：** 2026-07-26  
**范围：** 展示向整理（README / docs / Git 忽略 / 元数据），**未改业务逻辑与依赖**

---

## 1. 目标回顾

将实训项目整理为适合校招浏览的公开 GitHub 仓库，使访客能够：

| 时间 | 预期收获 |
| --- | --- |
| 30 秒 | 知道这是养老院多角色业务系统 |
| 2 分钟 | 了解四类角色与主流程 |
| 5 分钟 | 看清技术栈与架构 |
| 10 分钟 | 从亮点与代码锚点判断能力点 |

---

## 2. 已完成工作

### 2.1 README（作品集首页）

- 重写为结构化展示版：简介、亮点、技术栈、角色、三流程、架构、目录、环境、启动、截图占位、限制、计划、文档
- 去掉：私有仓库过时表述、绝对路径、演示密码、未验证的 OS/MySQL/Node 夸张兼容声明
- 诚实写明：无完整 SQL、Token 非 JWT、未使用 Spring Security、角色管理部分能力不完整等
- 目录名 `oldman01` / `oldman1` 标注为 backend / frontend（未物理重命名）

### 2.2 文档整理

```text
docs/
├── database.md                 # 核心
├── shenqing-liucheng.md        # 核心
├── images/.gitkeep             # 截图占位
└── audit/
    ├── 欧德曼…答辩巡检报告.md  # 已脱敏
    ├── audit-test-results.json
    └── audit-permission-results.json
```

- 巡检报告：去除本机路径与明文口令，JSON 路径改为 `docs/audit/`
- `database.md`：MySQL 表述与公开仓库安全提示对齐
- **移出 Git 跟踪（本地磁盘可仍保留）：** `materials/`、`docs/成果物.zip`
- `.gitignore` 已忽略上述路径

### 2.3 仓库工程与 GitHub 展示

| 项 | 状态 |
| --- | --- |
| `.editorconfig` | 已添加 |
| `.gitattributes` | 已添加 |
| `CHANGELOG.md` | 已添加（整理向变更） |
| GitHub Description | 已更新为多角色/技术栈摘要 |
| GitHub Topics | spring-boot, vue3, mybatis, mysql, vite, java, fullstack, nursing-home, portfolio |
| License / Actions / Issue·PR 模板 | **按你此前决定未添加** |

### 2.4 关键提交（main）

| Commit | 说明 |
| --- | --- |
| `5f9e50c` | docs: rewrite README for portfolio presentation |
| `b1e139c` | docs: reorganize docs, desensitize audit report, drop course materials |
| （后续） | editorconfig / gitattributes / changelog / README 链到 CHANGELOG |

---

## 3. 当前仓库对 HR / 面试官的观感

**优点**

- 首页能快速说明「做什么、谁用、主流程、技术栈」
- 亮点落到真实类名（数据范围、入住占床、床位联动）
- 限制写清楚，降低「包装过度」风险
- 课程 junk / 异质 zip / 明文口令不再进入公开树

**仍弱于完整作品集的点**

- **无运行截图**（README 已留 5 类清单）
- **无随仓 SQL**，克隆者难一键跑通
- 目录名 `oldman01`/`oldman1` 仍不直观（有意不重命名）
- 提交历史偏「整理向」，业务演进故事弱
- 登录页代码中仍可能有演示填充（业务代码未动，属已知）

---

## 4. 建议你本地完成的收尾（需人工）

1. **截图**：按 README 五类画面截图，放入 `docs/images/`，再在 README「项目截图」嵌入  
   - `home.png` / `admin-dashboard.png` / `admin-bed.png` / `nurse.png` / `family.png`
2. **（可选）结构-only SQL**：若可分享，脱敏后放到 `docs/sql/` 并改 README 限制条目  
3. **确认本地** `application.properties` 仍为占位密码，勿再提交真实库密  
4. **（可选）** 删除本地已不需要的 `materials/`、`docs/成果物.zip` 磁盘副本  

---

## 5. 刻意未做事项（遵守约束）

- 不修改 Java / Vue / Mapper / 接口 / 依赖版本 / 数据库设计  
- 不重命名 `oldman01` / `oldman1`  
- 不添加 License、GitHub Actions、Issue/PR 模板、CONTRIBUTING（按你的阶段决定）  
- 不 rewrite Git 历史、不 force push  
- 不编造企业级/高并发等无法证明的描述  

---

## 6. 后续可优化优先级

| 优先级 | 项 |
| --- | --- |
| P0 | 补截图并写入 README |
| P1 | 脱敏 schema SQL（若有权限分享） |
| P2 | About 区 Homepage 可填演示视频/博客（若有） |
| P2 | 业务提交信息规范（仅新提交，不改写历史） |
| P3 | 目录改名 backend/frontend（影响面大，需单独评估） |

---

## 7. 结论

仓库已从「实训作业打包」调整为「可公开浏览的作品集骨架」：  
**首页可读、文档分区、敏感信息收敛、GitHub Topics/描述可用。**  

距离「完整可演示作品集」还差：**截图**与（可选）**可分享的建库脚本**。二者需你在本机跑系统后补齐。
