import * as fetch from 'assets/scripts/fetch'

export const baseUrl = '/api/reimburses'

/**
 * 检查月份是否存在报销单
 */
export function check (dailyId) {
  let url = `${baseUrl}/check/${dailyId}`

  return fetch.get({
    url
  })
}

/**
 * 生成报销单项目
 */
export function generate (dailyId) {
  let url = `${baseUrl}/generate/${dailyId}`

  return fetch.post({
    url
  })
}