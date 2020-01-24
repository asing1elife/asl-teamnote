export default class Base {
  // 父类定义构造函数通用实现，避免子类重复实现
  constructor (obj) {
    if (typeof obj === 'object') {
      this._complex(obj)
    } else {
      this._simple(obj)
    }
  }

  // 简易构造函数，等待子类实现
  _simple (id) {
    this.id = id
  }

  // 复杂构造函数，等待子类实现
  _complex (data) {
    this.data = data
  }
}