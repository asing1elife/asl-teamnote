import * as fetch from 'assets/scripts/fetch'

export const baseUrl = '/api/dailies'

export function dailies () {
  return fetch.get({
    url: baseUrl
  })
}

export function daily (id) {
  return fetch.getById({
    url: baseUrl,
    id
  })
}

export function del (id) {
  return fetch.del({
    url: `${baseUrl}/${id}`
  })
}