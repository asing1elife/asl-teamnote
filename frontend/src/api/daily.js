import BaseApi from './baseApi'

class DailyApi extends BaseApi {
  constructor () {
    super('/dailies')
  }
}

export default new DailyApi()