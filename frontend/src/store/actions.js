/** vuex的异步请求以及对mutation的封装 */

import * as types from './mutation-types'
import dictionary from 'api/dictionary'
import { sessionStorage } from 'assets/scripts/storage'

// 设置缓存中的数据字典
export const setDictionaries = ({commit}) => {
  // 从服务端获取数据字典
  dictionary.list().then((res) => {
    // 将获取到的结果存入缓存
    commit(types.SET_DICTIONARIES, res.data)
  })
}

// 设置Token
export const setToken = ({commit, state}, token) => {
  // 退出当前用户
  if (!token) {
    // 清除缓存中的Token
    commit(types.SET_TOKEN, undefined)

    // 清除缓存中的当前用户
    commit(types.SET_CURRENT_USER, undefined)
    sessionStorage.remove(types.SET_CURRENT_USER)

    // 清除缓存中的当前机构
    commit(types.SET_CURRENT_ORGANIZATION, undefined)
    sessionStorage.remove(types.SET_CURRENT_ORGANIZATION)

    // 清除缓存中的数据字典
    commit(types.SET_DICTIONARIES, undefined)
    sessionStorage.remove(types.SET_DICTIONARIES)

    return false
  }

  // 防止重复登录
  if (state.token && state.token.length) {
    return false
  }

  // 设置缓存中的数据字典
  setDictionaries({commit})

  commit(types.SET_TOKEN, token)
}