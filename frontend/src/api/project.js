import * as fetch from 'assets/scripts/fetch'

export const baseUrl = '/api/projects'

/**
 * 获取指定组织所有项目
 */
export function projects (organizationId) {
  let url = `${baseUrl}?organizationId=${organizationId}`

  return fetch.post({
    url
  })
}

export function project (projectId) {
  return fetch.getById({
    url: baseUrl,
    id: projectId
  })
}

export function del (projectId) {
  return fetch.del({
    url: `${baseUrl}/${projectId}`
  })
}