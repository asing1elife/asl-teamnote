/** vuex入口文件 */

// 引入Vue实例
import Vue from 'vue'
// 引入Vuex
import Vuex from 'vuex'
// 引入Vuex内部的日志，在通过Vuex进行修改时，控制台会输出相应日志信息
import createLogger from 'vuex/dist/logger'
// * as actions表示引入actions内部所有方法，属于ES6语法
import * as actions from './actions'
import * as getters from './getters'
import state from './state'
import mutations from './mutations'

// 注册Vuex插件
Vue.use(Vuex)

// 使用Vuex的debug模式来判断对状态的修改是否经过mutations，没有经过则是违法的
// 该模式只在开发环境使用，生产时会为false
const debug = process.env.NODE_ENV !== 'production'

// 创建一个Vuex实例并暴露给外部
// 在其Store方法中注册需要用到的方法
export default new Vuex.Store({
  actions,
  getters,
  state,
  mutations,
  // 使用严格模式
  strict: debug,
  // 引入日志插件，但只在debug开启的时候
  plugins: debug ? [createLogger()] : []
})
