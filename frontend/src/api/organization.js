import * as fetch from 'assets/scripts/fetch'

export const baseUrl = '/api/organizations'

export function organizations () {
  return fetch.get({
    url: baseUrl
  })
}

export function organization (id) {
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