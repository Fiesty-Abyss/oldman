import request from './request'

export function getXitongYonghuList(params) {
  return request.get('/admin/xitong/yonghu-list', { params })
}

export function addXitongYonghu(data) {
  return request.post('/admin/xitong/yonghu-add', data)
}

export function updateXitongYonghu(data) {
  return request.post('/admin/xitong/yonghu-update', data)
}

export function changeXitongYonghuStatus(data) {
  return request.post('/admin/xitong/yonghu-change-status', data)
}

export function resetXitongYonghuPassword(data) {
  return request.post('/admin/xitong/yonghu-reset-password', data)
}

export function getXitongYonghuRole(yonghuId) {
  return request.get(`/admin/xitong/yonghu-role/${yonghuId}`)
}

export function assignXitongYonghuRole(data) {
  return request.post('/admin/xitong/yonghu-assign-role', data)
}

export function getXitongJueseList() {
  return request.get('/admin/xitong/juese-list')
}

export function addXitongJuese(data) {
  return request.post('/admin/xitong/juese-add', data)
}

export function updateXitongJuese(data) {
  return request.post('/admin/xitong/juese-update', data)
}

export function changeXitongJueseStatus(data) {
  return request.post('/admin/xitong/juese-change-status', data)
}

export function deleteXitongJuese(id) {
  return request.delete(`/admin/xitong/juese-delete/${id}`)
}

export function getHuliRenyuanOptions() {
  return request.get('/admin/xitong/huli-renyuan-options')
}

export function getJiashuOptions() {
  return request.get('/admin/xitong/jiashu-options')
}
