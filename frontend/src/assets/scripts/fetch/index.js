import axios from 'axios'
import { Message } from 'iview'
import * as config from 'assets/scripts/config'

axios.defaults.withCredentials = true

export function get (options) {
  options.method = config.GET

  return _promise(options)
}

export function post (options) {
  options.method = config.POST

  return _promise(options)
}

export function put (options) {
  options.method = config.PUT

  return _promise(options)
}

export function del (options) {
  options.method = config.DELETE

  return _promise(options)
}

export function getById ({url, id}) {
  if (id === -1) {
    // 返回一个空对象
    return new Promise((resolve, reject) => {
      resolve({
        data: -1
      })
    }).then((res) => {
      return res
    })
  }

  // 发起真实请求
  return get({
    url: `${url}/${id}`
  })
}

function _promise (options) {
  return new Promise((resolve, reject) => {
    const instance = axios.create({
      baseURL: config.serverBaseUrl,
      timeout: 10000,
      withCredentials: true,
      credentials: 'include'
    })

    instance(options).then((response) => {
      let result = response.data

      if (!result) {
        return false
      }

      // 全局异常捕获
      if (!result.success) {
        Message.error(result.data)
      }

      resolve(result)
      return false
    }).catch((error) => {
      reject(error)
    })
  })
}