import BaseApi from './baseApi'

class OrganizationApi extends BaseApi {
  constructor () {
    super('/organizations')
  }
}

export default new OrganizationApi()