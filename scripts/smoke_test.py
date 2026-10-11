#!/usr/bin/env python3
"""接口冒烟测试：用演示账号登录三种角色，请求后端所有无副作用的 GET 接口。

用法（先导入 db/schema.sql 与 db/seed.sql 并启动后端）：
    python3 scripts/smoke_test.py [--base http://127.0.0.1:8081]

判定规则：
- 返回 code 200 视为通过；
- 返回 400 且提示“只有 xx 可以…”视为角色隔离生效（例如护理员访问管理端接口），同样通过；
- 其它情况（500、SQL 报错等）视为失败，脚本以非零状态退出。
只依赖 Python 标准库。
"""
import argparse
import json
import re
import sys
import urllib.error
import urllib.request
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
CONTROLLERS = ROOT / 'oldman01' / 'src' / 'main' / 'java'
ACCOUNTS = [('admin', 'admin123'), ('huli001', '123456'), ('jiashu001', '123456')]


def collect_get_endpoints():
    endpoints = set()
    for file in CONTROLLERS.rglob('*Controller.java'):
        source = file.read_text(encoding='utf-8')
        prefix_match = re.search(r'@RequestMapping\("([^"]*)"\)\s*public class', source)
        prefix = prefix_match.group(1) if prefix_match else ''
        for match in re.finditer(r'@GetMapping(?:\((?:value\s*=\s*)?"([^"]*)"\))?', source):
            path = re.sub(r'\{[^}]+\}', '1', match.group(1) or '')
            if prefix + path:
                endpoints.add(prefix + path)
    return sorted(endpoints)


def request(base, method, path, token=None, body=None):
    data = json.dumps(body).encode('utf-8') if body is not None else None
    req = urllib.request.Request(base + path, data=data, method=method, headers={
        'Content-Type': 'application/json',
        'Authorization': token or '',
    })
    try:
        with urllib.request.urlopen(req, timeout=15) as resp:
            return json.loads(resp.read().decode('utf-8'))
    except urllib.error.HTTPError as error:
        try:
            return json.loads(error.read().decode('utf-8'))
        except ValueError:
            return {'code': error.code, 'message': error.reason}


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--base', default='http://127.0.0.1:8081')
    args = parser.parse_args()

    endpoints = collect_get_endpoints()
    failures = []
    for account, password in ACCOUNTS:
        login = request(args.base, 'POST', '/denglu/login', body={'dengluZhanghao': account, 'mima': password})
        token = (login.get('data') or {}).get('token')
        if not token:
            failures.append(f'{account} 登录失败：{login.get("message")}')
            continue
        passed = denied = 0
        for path in endpoints:
            result = request(args.base, 'GET', path, token)
            code, message = result.get('code'), str(result.get('message') or '')
            if code == 200:
                passed += 1
            elif code == 400 and message.startswith('只有'):
                denied += 1
            else:
                failures.append(f'{account} GET {path} → {code} {message[:120]}')
        print(f'{account:<10} 通过 {passed:>3}，角色隔离拦截 {denied:>3}')

    if failures:
        print('\n失败：')
        for line in failures:
            print('  ' + line)
        sys.exit(1)
    print(f'\n全部通过（{len(endpoints)} 个 GET 接口 × {len(ACCOUNTS)} 个角色）')


if __name__ == '__main__':
    main()
