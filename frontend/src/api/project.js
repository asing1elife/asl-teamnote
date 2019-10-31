import BaseApi from './baseApi'

class ProjectApi extends BaseApi {
  constructor () {
    super('/projects')
  }
}

export default new ProjectApi()