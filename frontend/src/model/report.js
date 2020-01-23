import Base from './base'

export default class Report extends Base {
  _simple (id) {
    this.id = id
    this.name = ''
    this.taskNum = 0
    this.taskFinishNum = 0
    this.projectNum = 0
    this.taskTagMemo = ''
    this.dayNum = 0
    this.dayExtraNum = 0
    this.monthMemo = ''
    this.dayMemo = ''
    this.type = ''
  }

  _complex (data) {
    this.id = data.id
    this.name = data.name
    this.taskNum = data.taskNum
    this.taskFinishNum = data.taskFinishNum
    this.projectNum = data.projectNum
    this.taskTagMemo = data.taskTagMemo
    this.dayNum = data.dayNum
    this.dayExtraNum = data.dayExtraNum
    this.monthMemo = data.monthMemo
    this.dayMemo = data.dayMemo
    this.type = data.type
  }
}