import request from './request'

export function getZanzhuGongkaiList() {
  return request.get('/zanzhu/gongkai-list')
}

export function getZanzhuYongtuList() {
  return request.get('/zanzhu/yongtu-list')
}

export function getZanzhuWuziList() {
  return request.get('/zanzhu/wuzi-list')
}

export function addZanzhuYixiang(data) {
  return request.post('/gongkai/zanzhu-yixiang-add', data)
}

export function getAdminZanzhuRenList(params) {
  return request.get('/admin/zanzhu/ren-list', { params })
}

export function addAdminZanzhuRen(data) {
  return request.post('/admin/zanzhu/ren-add', data)
}

export function updateAdminZanzhuRen(data) {
  return request.post('/admin/zanzhu/ren-update', data)
}

export function deleteAdminZanzhuRen(id) {
  return request.delete(`/admin/zanzhu/ren-delete/${id}`)
}

export function getAdminZanzhuJiluList(params) {
  return request.get('/admin/zanzhu/jilu-list', { params })
}

export function addAdminZanzhuJilu(data) {
  return request.post('/admin/zanzhu/jilu-add', data)
}

export function updateAdminZanzhuJilu(data) {
  return request.post('/admin/zanzhu/jilu-update', data)
}

export function deleteAdminZanzhuJilu(id) {
  return request.delete(`/admin/zanzhu/jilu-delete/${id}`)
}

export function getAdminZanzhuWuziList(params) {
  return request.get('/admin/zanzhu/wuzi-list', { params })
}

export function addAdminZanzhuWuzi(data) {
  return request.post('/admin/zanzhu/wuzi-add', data)
}

export function updateAdminZanzhuWuzi(data) {
  return request.post('/admin/zanzhu/wuzi-update', data)
}

export function deleteAdminZanzhuWuzi(id) {
  return request.delete(`/admin/zanzhu/wuzi-delete/${id}`)
}

export function getAdminZanzhuYongtuList(params) {
  return request.get('/admin/zanzhu/yongtu-list', { params })
}

export function addAdminZanzhuYongtu(data) {
  return request.post('/admin/zanzhu/yongtu-add', data)
}

export function updateAdminZanzhuYongtu(data) {
  return request.post('/admin/zanzhu/yongtu-update', data)
}

export function deleteAdminZanzhuYongtu(id) {
  return request.delete(`/admin/zanzhu/yongtu-delete/${id}`)
}

export function getAdminZanzhuYixiangList(params) {
  return request.get('/admin/zanzhu/yixiang-list', { params })
}

export function getAdminZanzhuYixiangDetail(id) {
  return request.get(`/admin/zanzhu/yixiang-detail/${id}`)
}

export function updateAdminZanzhuYixiangStatus(data) {
  return request.post('/admin/zanzhu/yixiang-update-status', data)
}

export function deleteAdminZanzhuYixiang(id) {
  return request.delete(`/admin/zanzhu/yixiang-delete/${id}`)
}
