import request from './request'

export function getHuliRenyuanList() {
  return request.get('/huli-renyuan/list')
}

export function addHuliRenyuan(data) {
  return request.post('/huli-renyuan/add', data)
}

export function updateHuliRenyuan(data) {
  return request.post('/huli-renyuan/update', data)
}

export function deleteHuliRenyuan(id) {
  return request.post(`/huli-renyuan/delete/${id}`)
}

export function assignLaoren(data) {
  return request.post('/huli-renyuan/assign-laoren', data)
}

export function getHuliFuwuLaoren(id) {
  return request.get(`/huli-renyuan/${id}/laoren-list`)
}

export function getUnassignedLaorenOptions(id, params) {
  return request.get(`/huli-renyuan/${id}/unassigned-laoren-options`, { params })
}
