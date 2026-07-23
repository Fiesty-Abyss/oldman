import request from './request'

export function getChuangweiList() {
  return request.get('/chuangwei/list')
}

export function getFangjianList() {
  return request.get('/fangjian/list')
}

export function getFangjianOptions() {
  return request.get('/fangjian/options')
}

export function addFangjian(data) {
  return request.post('/fangjian/add', data)
}

export function updateFangjian(data) {
  return request.post('/fangjian/update', data)
}

export function deleteFangjian(id) {
  return request.delete(`/fangjian/delete/${id}`)
}

export function getChuangweiBaseList() {
  return request.get('/chuangwei/base-list')
}

export function getKongxianChuangweiOptions(params) {
  return request.get('/chuangwei/kongxian-options', { params })
}

export function addChuangwei(data) {
  return request.post('/chuangwei/add', data)
}

export function updateChuangwei(data) {
  return request.post('/chuangwei/update', data)
}

export function deleteChuangwei(id) {
  return request.delete(`/chuangwei/delete/${id}`)
}

export function fenpeiChuangwei(data) {
  return request.post('/chuangwei/fenpei', data)
}

export function tiaohuanChuangwei(data) {
  return request.post('/chuangwei/tiaohuan', data)
}

export function shifangChuangwei(laorenId) {
  return request.post(`/chuangwei/shifang/${laorenId}`)
}
