import BaseApi from './baseApi'

class DailyRecordApi extends BaseApi {
  constructor () {
    super('/daily/records')
  }

  /**
   * 更新加班标识
   */
  extra (recordId, extra) {
    const url = `${this.baseUrl}/${recordId}/extra`

    return this.fetch.put({
      url,
      params: {
        extra
      }
    })
  }

  /**
   * 更新还班标识
   */
  repay (recordId, repay) {
    const url = `${this.baseUrl}/${recordId}/repay`

    return this.fetch.put({
      url,
      params: {
        repay
      }
    })
  }

  /**
   * 更新休息标识
   */
  rest (recordId, rest) {
    const url = `${this.baseUrl}/${recordId}/rest`

    return this.fetch.put({
      url: url,
      params: {
        rest
      }
    })
  }
}

export default new DailyRecordApi()