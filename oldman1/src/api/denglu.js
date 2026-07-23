import request from './request'

export function denglu(data) {
  return request.post('/denglu/login', data)
}
