import BaseApi from './baseApi'

class LoginApi extends BaseApi {
  constructor () {
    super('/login')
  }

  login (params) {
    const url = `${this.baseUrl}`

    return this.fetch.post({
      url,
      params
    })
  }
}

export default new LoginApi()