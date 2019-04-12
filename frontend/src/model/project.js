import Base from './base'
import Organization from './organization'

export default class Project extends Base {
  _simple (id) {
    this.id = id
    this.name = ''
    this.indexNo = 0
    this.organization = new Organization(-1)
  }

  _complex (data) {
    this.id = data.id
    this.name = data.name
    this.indexNo = data.indexNo
    this.organization = new Organization(data.organization)
  }
}