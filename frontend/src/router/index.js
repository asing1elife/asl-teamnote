import Vue from 'vue'
import Router from 'vue-router'
import viewUI from 'view-design'

import index from 'views/index'

import organization from 'views/organization'

import home from 'views/home'

import project from 'views/project'

import daily from 'views/daily'

Vue.use(Router)

const router = new Router({
  routes: [
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

  if (path === '/') {
    // 处于根目录时，默认跳转到组织
    // next({
    //   name: 'organization'
    // })
  } else {
    next()
  }
})

router.afterEach(() => {
  viewUI.LoadingBar.finish()
})

export default router
