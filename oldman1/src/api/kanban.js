import request from './request'

export function getKanbanTongji() {
  return request.get('/kanban/tongji')
}

export function getHuliyuanTongji() {
  return request.get('/huliyuan/tongji')
}

export function getJiashuTongji() {
  return request.get('/jiashu/tongji')
}
