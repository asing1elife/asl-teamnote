import axios from 'axios'
import router from 'router'
import store from 'store'
import { Message } from 'view-design'
import * as config from 'assets/scripts/config'
import _ from 'lodash'

axios.defaults.withCredentials = true

// 清空Token
function _clearToken () {
  store.dispatch('setToken', undefined)
}

// 头文件
const headers = function () {
  let token = store.state.token

  // 没有Token就不传入
  if (!_.isString(token)) {
    return undefined
  }

  return {
    'Authorization': token
  }
}

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
    return new Promise((resolve) => {
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

    // 获取头文件
    options.headers = headers()

    instance(options).then((response) => {
      let result = response.data
      let message = result.data

      if (!result) {
        return false
      }

      // 全局异常捕获
      if (!result.success) {
        // 处理授权异常
        if (!_authorizationExceptionHandler(message)) {
          // 不是授权异常就输出正常错误数据
          Message.error(message)
        }

        return false
      }

      resolve(result)
    }).catch((error) => {
      // 处理授权异常
      if (!_authorizationExceptionHandler(error.response.data.trace)) {
        reject(error)
      }
    })
  })
}

/**
 * 处理授权异常
 */
function _authorizationExceptionHandler (message) {
  if (message.indexOf('Unauthenticated') !== -1) {
    Message.error('没有检测到登录信息')
  } else if (message.indexOf('UnknownAccount') !== -1) {
    Message.error('没有注册的用户名')
  } else if (message.indexOf('DisabledAccount') !== -1) {
    Message.error('被禁用的用户，请联系管理员')
  } else if (message.indexOf('IncorrectCredentials') !== -1) {
    Message.error('用户名和密码不匹配')
  } else {
    // 不是授权异常
    return false
  }

  // 清空Token
  _clearToken()

  // 跳转到登录界面
  router.replace({
    name: 'login'
  })

  return true
}