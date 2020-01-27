/** vuex所有状态值 */

import * as types from './mutation-types'
import { appName } from 'assets/scripts/config'
import { sessionStorage, cookieStorage } from 'assets/scripts/storage'

// 定义需要用到的状态常量
const state = {
  currentOrganization: sessionStorage.get(types.SET_CURRENT_ORGANIZATION) || appName,
  dictionaries: sessionStorage.get(types.SET_DICTIONARIES),
  currentUser: sessionStorage.get(types.SET_CURRENT_USER),
  token: cookieStorage.get(types.SET_TOKEN)
}

// 将状态常量暴露给外部
export default state
