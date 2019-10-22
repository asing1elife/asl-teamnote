import Base from './base'
import Daily from './daily'
import { ReimburseStatus } from './dictionary'

export default class Reimburse extends Base {
  _simple (id) {
    this.id = id
    this.daily = new Daily(-1)
    this.status = ReimburseStatus.impl
    this.items = []
    this.invoices = []
  }

  _complex (data) {
    this.id = data.id
    this.daily = new Daily(data.daily)
    this.status = data.status
    this.items = data.items
    this.invoices = data.invoices
  }
}