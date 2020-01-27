import BaseApi from './baseApi'

class LoginApi extends BaseApi {
  constructor () {
    super('/users')
  }

  token (token) {
    const url = `${this.baseUrl}/token`
    
    return this.fetch.post({
      url,
      params: {
        token
      }
    })
  }

}

export default new LoginApi()