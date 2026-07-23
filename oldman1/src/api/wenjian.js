import request from './request'

export function getImageList(params) {
  return request.get('/admin/wenjian/image-list', { params })
}

export function uploadImage(file, type) {
  const data = new FormData()
  data.append('file', file)
  data.append('type', type)
  return request.post('/admin/wenjian/upload-image', data)
}

export function replaceImage(file, type, oldName) {
  const data = new FormData()
  data.append('file', file)
  data.append('type', type)
  data.append('oldName', oldName)
  return request.post('/admin/wenjian/replace-image', data)
}

export function deleteImage(type, name) {
  return request.delete('/admin/wenjian/delete-image', { params: { type, name } })
}
