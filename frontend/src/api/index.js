import * as common from './common'
import organization from './organization'
import project from './project'
import task from './task'
import taskTag from './taskTag'
import daily from './daily'
import dailyRecord from './dailyRecord'
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
