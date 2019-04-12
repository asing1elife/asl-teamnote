import Base from './base'

export default class TaskTag extends Base {
  _simple (id) {
    this.id = id
    this.name = ''
    this.color = TaskTag.tagColors[0].color
    this.indexNo = 0
  }

  _complex (data) {
    this.id = data.id
    this.name = data.name
    this.color = data.color
    this.indexNo = data.indexNo
  }

  static tagColors = [{
    name: '蓝',
    color: '#3da8f5'
  }, {
    name: '绿',
    color: '#75c940'
  }, {
    name: '靛',
    color: '#2ebdb3'
  }, {
    name: '紫',
    color: '#797ec9'
  }, {
    name: '桔',
    color: '#ffaf38'
  }, {
    name: '橙',
    color: '#ff4f3d'
  }]
}