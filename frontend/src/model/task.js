import Base from './base'
import TaskTag from './taskTag'
import Project from './project'
import dictionary from './dictionary'

export default class Task extends Base {
  _simple (id) {
    this.id = id
    this.name = ''
    this.description = ''
    this.taskTag = new TaskTag(-1)
    this.project = new Project(-1)
    this.level = dictionary.taskLevel.normal
    this.status = dictionary.taskStatus.init
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
    this.beginDate = data.beginDate
    this.finishDate = data.finishDate
    this.records = data.records
  }

  get levelCode () {
    return this.level.code
  }

  set levelCode (code) {
    this.level = dictionary.taskLevel.get(code)
  }

  get dateRange () {
    let dateRange = null

    if (this.beginDate) {
      dateRange = ''
      dateRange += `${this.beginDate} ~ `
    }

    if (this.finishDate) {
      dateRange += this.finishDate
    }

    return dateRange
  }
}