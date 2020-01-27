import store from 'store'
import _ from 'lodash'

let dics = {}

class Dictionary {
  constructor (dic) {
    this.code = dic.code
    this.name = dic.name
    this.index = dic.indexNo
    this.color = getColor(dic.code)
  }
}

/**
 * 生成数据字典
 */
function generateDictionaries () {
  // 从vuex中获取后端传入的数据字典原始数据
  let dictionaries = store.state.dictionaries

  _.forEach(dictionaries, (dic) => {
    // 每个数据字典对应的类名
    let className = _.camelCase(dic.className)
    // 简短编码，用于类名下的属性名
    let simpleCode = dic.realCode

    // 判断该类名是否已经创建数据字典
    if (dics[className]) {
      // 已创建则添加属性名
      // 属性名接收的是实例化后的数据字典类
      dics[className][simpleCode] = new Dictionary(dic)
    } else {
      // 没有创建则创建一个对应类名的数据字典

      dics[className] = class {
        // 为该数据字典指定一个获取内部所有编码的函数
        static all () {
          let dictionaries = []

          for (let key in this) {
            dictionaries.push(this[key])
          }

          // 排序
          return _.orderBy(dictionaries, ['index'])
        }

        // 获取指定字典对象
        static get (code) {
          return _.find(this.all(), (dic) => {
            return dic.code === code
          })
        }
      }

      // 为该数据字典添加第一个属性
      dics[className][simpleCode] = new Dictionary(dic)
    }
  })

  return dics
}

/**
 * 获取对应数据字典的颜色
 */
export function getColor (code) {
  let underlineIndex = code.indexOf('_')

  // 判断传入的编码是否符合规则
  if (underlineIndex !== -1) {
    code = _.camelCase(code.substr(underlineIndex + 1))
  }

  switch (code) {
    case 'init':
      return 'default'
    case 'impl':
      return 'warning'
    case 'finish':
      return 'success'
    case 'normal':
      return '#a6a6a6'
    case 'urgency':
      return '#ffaf38'
    case 'very':
      return '#ff4f3e'
  }
}

// 对外暴露数据字典集，同时使用拦截器检查数据可行性
export default new Proxy(dics, {
  get: function (target, property) {
    // 判断数据字典集是否为空
    if (Object.keys(target).length !== 0) {
      // 不为空则直接返回对应属性名的内容
      return target[property]
    }

    // 为空则生成后再返回
    return generateDictionaries()[property]
  }
})