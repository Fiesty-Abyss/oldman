import request from './request'

export function getJiashuLaorenXinxi() {
  return request.get('/jiashu/laoren-xinxi')
}

export function uploadJiashuLaorenTouxiang(laorenId, file) {
  const data = new FormData()
  data.append('laorenId', laorenId)
  data.append('file', file)
  return request.post('/jiashu/laoren-touxiang-upload', data)
}

export function getJiashuLaorenXiugaiShenqingList() {
  return request.get('/jiashu/laoren-xiugai-shenqing-list')
}

export function addJiashuLaorenXiugaiShenqing(data) {
  return request.post('/jiashu/laoren-xiugai-shenqing-add', data)
}

export function uploadJiashuLaorenXiugaiTouxiang(laorenId, file) {
  const data = new FormData()
  data.append('laorenId', laorenId)
  data.append('file', file)
  return request.post('/jiashu/laoren-xiugai-touxiang-upload', data)
}

export function getJiashuChuangweiInfo(params) {
  return request.get('/jiashu/chuangwei-info', { params })
}

export function getJiashuHuliJiluList(params) {
  return request.get('/jiashu/huli-jilu-list', { params })
}

export function getJiashuJiankangJianceList(params) {
  return request.get('/jiashu/jiankang-jiance-list', { params })
}

export function getJiashuYongyaoJihuaList(params) {
  return request.get('/jiashu/yongyao-jihua-list', { params })
}

export function getJiashuFuyaoJiluList(params) {
  return request.get('/jiashu/fuyao-jilu-list', { params })
}

export function getJiashuFeiyongZhangdanList(params) {
  return request.get('/jiashu/feiyong-zhangdan-list', { params })
}

export function getJiashuJiaofeiJiluList(params) {
  return request.get('/jiashu/jiaofei-jilu-list', { params })
}

export function getJiashuWaichuList(params) {
  return request.get('/jiashu/waichu-list', { params })
}

export function addJiashuWaichu(data) {
  return request.post('/jiashu/waichu-add', data)
}

export function getJiashuTuizhuList(params) {
  return request.get('/jiashu/tuizhu-list', { params })
}

export function addJiashuTuizhu(data) {
  return request.post('/jiashu/tuizhu-add', data)
}
