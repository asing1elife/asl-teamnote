import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import api from './api'

import viewUI from 'view-design'
import vueclipboard from 'vue-clipboard2'

import './assets/stylus/index.styl'
import 'view-design/dist/styles/iview.css'

Vue.config.productionTip = false

// 访问后端接口的统一入口
Vue.use(api)

Vue.use(viewUI)
Vue.use(vueclipboard)

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
