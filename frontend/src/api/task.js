import BaseApi from './baseApi'

class TaskApi extends BaseApi {
  constructor () {
    super('/tasks')
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