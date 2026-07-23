import request from './request'

export function getHuliyuanLaorenList(params) {
  return request.get('/huliyuan/laoren-list', { params })
}

export function getHuliyuanLaorenDetail(laorenId) {
  return request.get(`/huliyuan/laoren-detail/${laorenId}`)
}

export function getLaorenHuliXiangmuList(laorenId) {
  return request.get(`/huliyuan/${laorenId}/huli-xiangmu-list`)
}

export function getHuliyuanHuliJiluList(params) {
  return request.get('/huliyuan/huli-jilu/list', { params })
}

export function addHuliyuanHuliJilu(data) {
  return request.post('/huliyuan/huli-jilu/add', data)
}

export function deleteHuliyuanHuliJilu(id) {
  return request.delete(`/huliyuan/huli-jilu/delete/${id}`)
}

export function getHuliyuanJiankangJianceList(params) {
  return request.get('/huliyuan/jiankang-jiance/list', { params })
}

export function addHuliyuanJiankangJiance(data) {
  return request.post('/huliyuan/jiankang-jiance/add', data)
}

export function getHuliyuanYongyaoJihuaList(params) {
  return request.get('/huliyuan/yongyao-jihua/list', { params })
}

export function getHuliyuanFuyaoJiluList(params) {
  return request.get('/huliyuan/fuyao-jilu/list', { params })
}

export function addHuliyuanFuyaoJilu(data) {
  return request.post('/huliyuan/fuyao-jilu/add', data)
}

export function getHuliyuanWaichuList(params) {
  return request.get('/huliyuan/waichu/list', { params })
}

export function addHuliyuanWaichu(data) {
  return request.post('/huliyuan/waichu/add', data)
}

export function getHuliyuanTuizhuList(params) {
  return request.get('/huliyuan/tuizhu/list', { params })
}

export function addHuliyuanTuizhu(data) {
  return request.post('/huliyuan/tuizhu/add', data)
}
