import Base from './base'
import Daily from './daily'

export default class DailyRecord extends Base {
  _simple (id) {
    this.id = id
    this.daily = new Daily(-1)
    this.day = 0
    this.extra = false
    this.repay = false
    this.rest = false
    this.tasks = []
  }

  _complex (data) {
    this.id = data.id
    this.daily = new Daily(data.daily)
    this.day = data.day
    this.extra = data.extra
    this.repay = data.repay
    this.rest = data.rest
    this.tasks = data.tasks
  }
}