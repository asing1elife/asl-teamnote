import BaseApi from './baseApi'
import * as fetch from 'assets/scripts/fetch'

class OrganizationApi extends BaseApi {
  constructor () {
    super('/organizations')
  }

  list (params) {
    const url = `${this.baseUrl}`

    return fetch.get({
      url,
      params
    })
  }
}

export default new OrganizationApi()