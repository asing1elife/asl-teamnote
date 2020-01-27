/** vuex所有的mutation */

// 引入mutations-types
import * as types from './mutation-types'
import { appName } from 'assets/scripts/config'
import { sessionStorage, cookieStorage } from 'assets/scripts/storage'

// 定义mutation，其内部是一些修改方法
const mutations = {
  // 第一个参数是状态值
  // 第二个参数为提交状态修改是传入的对象参数
  [types.SET_CURRENT_ORGANIZATION] (state, currentOrganization) {
    state.currentOrganization = currentOrganization || appName

    sessionStorage.set(types.SET_CURRENT_ORGANIZATION, currentOrganization)
  },
  [types.SET_DICTIONARIES] (state, dictionaries) {
    state.dictionaries = dictionaries

    sessionStorage.set(types.SET_DICTIONARIES, dictionaries)
  },
  [types.SET_CURRENT_USER] (state, currentUser) {
    state.currentUser = currentUser

    sessionStorage.set(types.SET_CURRENT_USER, currentUser)
  },
  [types.SET_TOKEN] (state, token) {
    state.token = token

    cookieStorage.set(types.SET_TOKEN, token, {
      expires: 30
    })
  }
}

// 暴露给外部
export default mutations
