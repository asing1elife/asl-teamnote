import Base from './base'
import TaskTag from './taskTag'
import Project from './project'
import { TaskLevel, TaskStatus } from './dictionary'

export default class Task extends Base {
  _simple (id) {
    this.id = id
    this.name = ''
    this.description = ''
    this.taskTag = new TaskTag(-1)
    this.project = new Project(-1)
    this.level = TaskLevel.normal
    this.status = TaskStatus.init
    this.planBeginDate = null
    this.planFinishDate = null
    this.beginDate = null
    this.finishDate = null
    this.records = []
  }

  _complex (data) {
    this.id = data.id
    this.name = data.name
    this.description = data.description
    this.taskTag = new TaskTag(data.taskTag)
    this.project = new Project(data.project)
    this.level = data.level
    this.status = data.status
    this.planBeginDate = data.planBeginDate
    this.planFinishDate = data.planFinishDate
    this.beginDate = data.beginDate
    this.finishDate = data.finishDate
    this.records = data.records
  }

  get levelCode () {
    return this.level.code
  }

  set levelCode (code) {
    this.level = TaskLevel.getDictionary(code)
  }

  get dateRange () {
    return [this.planBeginDate, this.planFinishDate]
  }

  set dateRange (range) {
    this.planBeginDate = range[0]
    this.planFinishDate = range[1]
  }
}