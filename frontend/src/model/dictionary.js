class Dictionary {
  constructor (code, name, color) {
    this.code = code
    this.name = name
    this.color = color || ''
  }

  /**
   * 获取子类所有静态属性
   */
  static getDictionaries () {
    let dictionaries = []

    for (let key in this) {
      dictionaries.push(this[key])
    }

    return dictionaries
  }

  /**
   * 获取子类对应实例
   */
  static getDictionary (code) {
    return this.getDictionaries().find((dictionary) => {
      return dictionary.code === code
    })
  }

  /**
   * 获取子类对应实例颜色
   */
  static getDictionaryColor (code) {
    return this.getDictionary(code).color
  }
}

/**
 * 任务级别
 */
class TaskLevel extends Dictionary {
  static normal = new TaskLevel('TALE_Normal', '普通', '#a6a6a6')
  static urgency = new TaskLevel('TALE_Urgency', '紧急', '#ffaf38')
  static very = new TaskLevel('TALE_Very', '非常紧急', '#ff4f3e')
}

/**
 * 任务状态
 */
class TaskStatus extends Dictionary {
  static init = new TaskStatus('TAST_Init', '初始化', 'default')
  static impl = new TaskStatus('TAST_Impl', '进行中', 'warning')
  static finish = new TaskStatus('TAST_Finish', '已完成', 'success')
}

export {
  TaskLevel,
  TaskStatus
}