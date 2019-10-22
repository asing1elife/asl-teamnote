import * as common from './common'
import * as organization from './organization'
import * as project from './project'
import * as task from './task'
import * as taskTag from './taskTag'
import * as daily from './daily'
import * as dailyRecord from './dailyRecord'
import * as reimburse from './reimburse'

const apiObj = {
  common,
  organization,
  project,
  task,
  taskTag,
  daily,
  dailyRecord,
  reimburse
}

export default function install (Vue) {
  if (install.installed) return
  install.installed = true

  Object.defineProperties(Vue.prototype, {
    $api: {
      get () {
        return apiObj
      }
    }
  })
}
