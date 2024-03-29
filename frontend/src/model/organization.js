import Base from './base'

export default class Organization extends Base {
  _simple (id) {
    this.id = id
    this.name = ''
    this.description = ''
    this.star = false
    this.display = true
  }

  _complex (data) {
    this.id = data.id
    this.name = data.name
    this.description = data.description
    this.star = data.star
    this.display = data.display
  }
}