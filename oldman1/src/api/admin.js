import request from './request'

export function getAdminWaichuList(params) {
  return request.get('/admin/shenpi/waichu-list', { params })
}

export function passAdminWaichu(data) {
  return request.post('/admin/shenpi/waichu-pass', data)
}

export function rejectAdminWaichu(data) {
  return request.post('/admin/shenpi/waichu-reject', data)
}

export function huiyuanAdminWaichu(data) {
  return request.post('/admin/shenpi/waichu-huiyuan', data)
}

export function deleteAdminWaichu(id) {
  return request.delete(`/admin/shenpi/waichu-delete/${id}`)
}

export function getAdminTuizhuList(params) {
  return request.get('/admin/shenpi/tuizhu-list', { params })
}

export function passAdminTuizhu(data) {
  return request.post('/admin/shenpi/tuizhu-pass', data)
}

export function rejectAdminTuizhu(data) {
  return request.post('/admin/shenpi/tuizhu-reject', data)
}

export function deleteAdminTuizhu(id) {
  return request.delete(`/admin/shenpi/tuizhu-delete/${id}`)
}

export function getAdminHuliJiluList(params) {
  return request.get('/admin/jilu/huli-list', { params })
}

export function deleteAdminHuliJilu(id) {
  return request.delete(`/admin/jilu/huli-delete/${id}`)
}

export function getAdminJiankangList(params) {
  return request.get('/admin/jilu/jiankang-list', { params })
}

export function getAdminFuyaoList(params) {
  return request.get('/admin/jilu/fuyao-list', { params })
}

export function getAdminFeiyongZhangdanList(params) {
  return request.get('/admin/feiyong/zhangdan-list', { params })
}

export function addAdminFeiyongZhangdan(data) {
  return request.post('/admin/feiyong/zhangdan-add', data)
}

export function updateAdminFeiyongZhangdan(data) {
  return request.post('/admin/feiyong/zhangdan-update', data)
}

export function deleteAdminFeiyongZhangdan(id) {
  return request.delete(`/admin/feiyong/zhangdan-delete/${id}`)
}

export function getAdminFeiyongZhangdanDetail(id) {
  return request.get(`/admin/feiyong/zhangdan-detail/${id}`)
}

export function getAdminJiaofeiList(params) {
  return request.get('/admin/feiyong/jiaofei-list', { params })
}

export function addAdminJiaofei(data) {
  return request.post('/admin/feiyong/jiaofei-add', data)
}

export function deleteAdminJiaofei(id) {
  return request.delete(`/admin/feiyong/jiaofei-delete/${id}`)
}

export function getAdminRuzhuShenqingList(params) {
  return request.get('/admin/ruzhu-shenqing/list', { params })
}

export function getAdminRuzhuShenqingDetail(id) {
  return request.get(`/admin/ruzhu-shenqing/detail/${id}`)
}

export function passAdminRuzhuShenqing(data) {
  return request.post('/admin/ruzhu-shenqing/pass', data)
}

export function rejectAdminRuzhuShenqing(data) {
  return request.post('/admin/ruzhu-shenqing/reject', data)
}

export function deleteAdminRuzhuShenqing(id) {
  return request.delete(`/admin/ruzhu-shenqing/delete/${id}`)
}

export function getAdminRuzhuShenqingPassedList(params) {
  return request.get('/admin/ruzhu-shenqing/passed-list', { params })
}

export function getAdminRuzhuShenqingBring(id) {
  return request.get(`/admin/ruzhu-shenqing/bring/${id}`)
}

export function getAdminYaopinList(params) {
  return request.get('/admin/yaopin/list', { params })
}

export function addAdminYaopin(data) {
  return request.post('/admin/yaopin/add', data)
}

export function deleteAdminYaopin(id) {
  return request.delete(`/admin/yaopin/delete/${id}`)
}

export function getAdminLaorenXiugaiShenqingList(params) {
  return request.get('/admin/laoren-xiugai-shenqing/list', { params })
}

export function getAdminLaorenXiugaiShenqingDetail(id) {
  return request.get(`/admin/laoren-xiugai-shenqing/detail/${id}`)
}

export function passAdminLaorenXiugaiShenqing(data) {
  return request.post('/admin/laoren-xiugai-shenqing/pass', data)
}

export function rejectAdminLaorenXiugaiShenqing(data) {
  return request.post('/admin/laoren-xiugai-shenqing/reject', data)
}

export function deleteAdminLaorenXiugaiShenqing(id) {
  return request.delete(`/admin/laoren-xiugai-shenqing/delete/${id}`)
}
