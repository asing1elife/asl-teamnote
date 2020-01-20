import BaseApi from './baseApi'

class ProjectApi extends BaseApi {
  constructor () {
    super('/projects')
  }

  sort (data) {
    const url = `${this.baseUrl}/sort`

    return this.fetch.post({
      url,
      data
    })
  }
}

export default new ProjectApi()