import Base from './base'

export default class User extends Base {
  _simple (id) {
    this.username = id
    this.nickname = 'guest'
    this.gender = '男'
    this.birthday = '1993-03-17'
  }

  _complex (data) {
    this.username = data.username
    this.nickname = data.nickname
    this.gender = data.gender
    this.birthday = data.birthday
  }

}