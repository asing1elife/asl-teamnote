import BaseApi from './baseApi'

class ReportApi extends BaseApi {
  constructor () {
    super('/reports')
  }

  year (dailyId) {
    const url = `${this.baseUrl}/year/${dailyId}`

    return this.fetch.get({
      url
    })
  }
}

export default new ReportApi()