import Base from './base'

export default class Daily extends Base {
  _simple (id) {
    this.id = id
    this.year = 0
    this.month = 0
    this.expense = false
    this.expenseAmount = 0
    this.totalDay = 0
  }

  _complex (data) {
    this.id = data.id
    this.year = data.year
    this.month = data.month
    this.expense = data.expense
    this.expenseAmount = data.expenseAmount
    this.totalDay = data.totalDay
  }
}