import request from './request'

export function getLaorenXinxiList() {
  return request.get('/laoren-xinxi/list')
}

export function getLaorenOptions() {
  return request.get('/laoren-xinxi/options')
}

export function addLaorenXinxi(data) {
  return request.post('/laoren-xinxi/add', data)
}

export function updateLaorenXinxi(data) {
  return request.post('/laoren-xinxi/update', data)
}

export function deleteLaorenXinxi(id) {
  return request.post(`/laoren-xinxi/delete/${id}`)
}

export function ruzhuDengji(data) {
  return request.post('/laoren-xinxi/ruzhu', data)
}
