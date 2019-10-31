import * as fetch from 'assets/scripts/fetch'

export default class BaseApi {

  /**
   * 构造函数
   * @param baseUrl 具体请求需要从子类传入
   */
  constructor (baseUrl) {
    this.baseUrl = baseUrl
    this.fetch = fetch
  }

  /**
   * 分页数据
   * 后端还没实现分页
   */
  page (params) {
    const url = `${this.baseUrl}/page`

    return fetch.post({
      url,
      data: params
    })
  }

  /**
   * 列表数据
   */
  list (params) {
    const url = `${this.baseUrl}`

    return fetch[params ? 'post' : 'get']({
      url,
      params
    })
  }

  /**
   * 单个数据
   */
  get (id) {
    const url = `${this.baseUrl}`

    return fetch.getById({
      url,
      id
    })
  }

  /**
   * 保存
   */
  save (data) {
    const url = `${this.baseUrl}`

    return fetch.put({
      url,
      data
    })
  }

  /**
   * 删除
   */
  del (id) {
    const url = `${this.baseUrl}/${id}`

    return fetch.del({
      url
    })
  }

}