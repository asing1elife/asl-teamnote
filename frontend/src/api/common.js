import * as fetch from 'assets/scripts/fetch'

export function save ({url, data}) {
  return fetch.put({
    url,
    data
  })
}