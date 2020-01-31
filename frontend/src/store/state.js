/** vuex所有状态值 */

import * as types from './mutation-types'
import { appName } from 'assets/scripts/config'
import { sessionStorage, localStorage, cookieStorage } from 'assets/scripts/storage'

// 定义需要用到的状态常量
const state = {
  currentOrganization: sessionStorage.get(types.SET_CURRENT_ORGANIZATION) || appName,
  currentUser: sessionStorage.get(types.SET_CURRENT_USER),
  dictionaries: localStorage.get(types.SET_DICTIONARIES),
  token: cookieStorage.get(types.SET_TOKEN)
}

// 将状态常量暴露给外部
export default state
