import BaseApi from './baseApi'

class TaskTagApi extends BaseApi {
  constructor () {
    super('/task/tags')
  }
}

export default new TaskTagApi()