import * as fetch from 'assets/scripts/fetch'

export const baseUrl = '/api/task/tags'

export function tags () {
  return fetch.get({
    url: baseUrl
  })
}

export function tag (tagId) {
  return fetch.getById({
    url: baseUrl,
    id: tagId
  })
}

export function del (tagId) {
  return fetch.del({
    url: `${baseUrl}/${tagId}`
  })
}