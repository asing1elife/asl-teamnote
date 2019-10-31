/** vuex所有状态值 */

import * as types from './mutation-types'
import { appName } from 'assets/scripts/config'
import { localStorage } from 'assets/scripts/storage'

// 定义需要用到的状态常量
const state = {
  currentOrganization: localStorage.get(types.SET_CURRENT_ORGANIZATION) || appName,
  dictionaries: localStorage.get(types.SET_DICTIONARIES)
}

// 将状态常量暴露给外部
export default state
