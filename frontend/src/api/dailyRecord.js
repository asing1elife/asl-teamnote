import * as fetch from 'assets/scripts/fetch'

export const baseUrl = '/api/daily/records'

export function records (dailyId) {
  const url = `${baseUrl}`

  return fetch.post({
    url,
    params: {
      dailyId
    }
  })
}

export function record (id) {
  return fetch.getById({
    url: baseUrl,
    id
  })
}

/**
 * 更新加班标识
 */
export function extra (recordId, extra) {
  const url = `${baseUrl}/${recordId}/extra`

  return fetch.put({
    url: url,
    params: {
      extra
    }
  })
}

/**
 * 更新还班标识
 */
export function repay (recordId, repay) {
  const url = `${baseUrl}/${recordId}/repay`

  return fetch.put({
    url: url,
    params: {
      repay
    }
  })
}

/**
 * 更新休息标识
 */
export function rest (recordId, rest) {
  const url = `${baseUrl}/${recordId}/rest`

  return fetch.put({
    url: url,
    params: {
      rest
    }
  })
}

export function del (id) {
  return fetch.del({
    url: `${baseUrl}/${id}`
  })
}