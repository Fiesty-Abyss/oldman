const YONGHU_KEY = 'oldman_yonghu'
const TOKEN_KEY = 'oldman_token'

export function getDangqianYonghu() {
  const raw = localStorage.getItem(YONGHU_KEY)
  if (!raw) {
    return null
  }

  try {
    return JSON.parse(raw)
  } catch {
    localStorage.removeItem(YONGHU_KEY)
    localStorage.removeItem(TOKEN_KEY)
    return null
  }
}

export function baocunDenglu(dengluXinxi) {
  localStorage.setItem(TOKEN_KEY, dengluXinxi.token)
  localStorage.setItem(YONGHU_KEY, JSON.stringify(dengluXinxi))
}

export function qingchuDenglu() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(YONGHU_KEY)
}

export function yiDenglu() {
  return Boolean(localStorage.getItem(TOKEN_KEY) && getDangqianYonghu())
}
