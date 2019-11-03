import BaseApi from './baseApi'

class TaskApi extends BaseApi {
  constructor () {
    super('/tasks')
  }

  /**
   * 获取未完成任务列表
   */
  unfinish (projectId) {
    const url = `${this.baseUrl}/${projectId}/unfinish`

    return this.fetch.get({
      url
    })
  }

  /**
   * 获取已完成任务列表
   */
  finish (projectId) {
    const url = `${this.baseUrl}/${projectId}/finish`

    return this.fetch.get({
      url
    })
  }

  /**
   * 状态
   */
  status (id, params) {
    const url = `${this.baseUrl}/${id}/status`

    return this.fetch.put({
      url,
      params
    })
  }
}

export default new TaskApi()