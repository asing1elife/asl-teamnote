import Vue from 'vue'
import store from 'store'
import Router from 'vue-router'
import viewUI from 'view-design'
import _ from 'lodash'

import login from 'views/login'

import index from 'views/index'

import organization from 'views/organization'

import home from 'views/home'

import project from 'views/project'

import daily from 'views/daily'

// vue-router 3.1 后将 replace 和 push 的函数改为返回 Promise
// Promise 返回 Reject 后如果没有捕获异常就会抛出异常
// 所以这里对 push 函数进行了重写
const originalPush = Router.prototype.push
Router.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject) {
    return originalPush.call(this, location, onResolve, onReject)
  }

  return originalPush.call(this, location).catch(err => err)
}

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/',
      name: 'index',
      component: index,
      children: [
        {
          path: 'organizations',
          name: 'organization',
          component: organization
        },
        {
          path: 'organizations/:organizationId',
          name: 'home',
          component: home,
          props: true,
          children: [
            {
              path: 'projects',
              name: 'project',
              component: project
            },
            {
              path: 'dailies',
              name: 'daily',
              component: daily
            }
          ]
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  viewUI.LoadingBar.start()

  let path = to.path
  // 尝试获取登录标记
  let token = store.state.token

  if (_.isString(token) || path === '/login') {
    // 只有用户已登录或者访问登录页时，路由才能继续执行
    next()
  } else {
    viewUI.LoadingBar.finish()

    // 否则直接导向登录页
    next({
      name: 'login'
    })
  }
})

router.afterEach(() => {
  viewUI.LoadingBar.finish()
})

export default router
