import request from './request'

export function jiashuZhuce(data) {
  return request.post('/gongkai/jiashu-zhuce', data)
}

export function ruzhuShenqingAdd(data) {
  return request.post('/gongkai/ruzhu-shenqing-add', data)
}
