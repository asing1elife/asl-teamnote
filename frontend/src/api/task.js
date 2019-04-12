import * as fetch from 'assets/scripts/fetch'

export const baseUrl = '/api/tasks'

/**
 * 获取指定项目所有任务
 */
export function tasks (projectId) {
  return fetch.post({
    url: baseUrl,
    params: {
      projectId
    }
  })
}

export function task (taskId) {
  return fetch.getById({
    url: baseUrl,
    id: taskId
  })
}

export function del (taskId) {
  return fetch.del({
    url: `${baseUrl}/${taskId}`
  })
}

export function status (taskId, statusCode) {
  let url = `${baseUrl}/${taskId}/status`

  return fetch.put({
    url,
    params: {
      statusCode
    }
  })
}