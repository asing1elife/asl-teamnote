import * as fetch from 'assets/scripts/fetch'

export const baseUrl = '/api/dailies'

export function dailies (organizationId) {
  return fetch.post({
    url: baseUrl,
    params: {
      organizationId: organizationId
    }
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