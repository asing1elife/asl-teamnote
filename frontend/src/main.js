// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
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

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
