import request from './request'

export function getHuliXiangmuList(params) {
  return request.get('/huli-xiangmu/list', { params })
}

export function addHuliXiangmu(data) {
  return request.post('/huli-xiangmu/add', data)
}

export function updateHuliXiangmu(data) {
  return request.post('/huli-xiangmu/update', data)
}

export function deleteHuliXiangmu(id) {
  return request.delete(`/huli-xiangmu/delete/${id}`)
}

export function changeHuliXiangmuStatus(data) {
  return request.post('/huli-xiangmu/change-status', data)
}

export function getHuliJibieList(params) {
  return request.get('/huli-jibie/list', { params })
}

export function addHuliJibie(data) {
  return request.post('/huli-jibie/add', data)
}

export function updateHuliJibie(data) {
  return request.post('/huli-jibie/update', data)
}

export function deleteHuliJibie(id) {
  return request.delete(`/huli-jibie/delete/${id}`)
}

export function changeHuliJibieStatus(data) {
  return request.post('/huli-jibie/change-status', data)
}

export function getJibieXiangmuList(id) {
  return request.get(`/huli-jibie/${id}/xiangmu-list`)
}

export function getJibieUnboundXiangmuList(id) {
  return request.get(`/huli-jibie/${id}/unbound-xiangmu-list`)
}

export function bindJibieXiangmu(data) {
  return request.post('/huli-jibie/bind-xiangmu', data)
}

export function unbindJibieXiangmu(jibieId, xiangmuId) {
  return request.delete('/huli-jibie/unbind-xiangmu', { params: { jibieId, xiangmuId } })
}

export function getLaorenHuliList() {
  return request.get('/laoren-huli/laoren-list')
}

export function getLaorenDangqianHuli(laorenId) {
  return request.get(`/laoren-huli/${laorenId}`)
}

export function setLaorenHuliJibie(data) {
  return request.post('/laoren-huli/set-jibie', data)
}

export function removeLaorenHuliJibie(laorenId) {
  return request.delete(`/laoren-huli/remove/${laorenId}`)
}
