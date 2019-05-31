/**
 * 添加class
 * @param el 需要添加class的对象
 * @param className 待添加的class名称
 */
export function addClass (el, className) {
  // 判断待添加的class是否已存在
  if (hasClass(el, className)) {
    return
  }

  // 拆分el已存在的class列表
  let existClasses = el.className.split(' ')
  // 将待添加的class加入该列表
  existClasses.push(className)

  // 将添加了新的class的列表重新回传到el中
  el.className = existClasses.join(' ').substring(0)
}

/**
 * 删除class
 * @param el 需要删除class的对象
 * @param className 待删除的class名称
 */
export function removeClass (el, className) {
  // 判断待删除的class是否存在
  if (!hasClass(el, className)) {
    return
  }

  // 拆分el已存在的class列表
  let existClasses = el.className.split(' ')
  // 找到待删除的class索引
  let preDelClassIndex = existClasses.indexOf(className)

  // 找到则从列表中移除
  if (preDelClassIndex !== -1) {
    existClasses.splice(preDelClassIndex, 1)
  }

  // 将移除指定class的列表重新回传到el中
  el.className = existClasses.join(' ').substring(0)
}

/**
 * 判断是否存在class
 * @param el 需要被判断的对象
 * @param className 待判断是否存在的class名称
 */
export function hasClass (el, className) {
  // 定义正则来判断class是否存在
  // \\s表示空白字符
  let reg = new RegExp('(^|\\s)' + className + '(\\s|$)')

  // 使用定义好的正则表达式对el的所有className进行匹配，直接返回匹配结果
  return reg.test(el.className)
}

/**
 * 判断当前标签的名称是否为传入的标签名称
 * @param el 需要被判断的对象
 * @param targetTagName 待判断是否相等的标签名称
 */
export function isTargetTag (el, targetTagName) {
  // \\s表示空白字符
  let reg = new RegExp('(^|\\s)' + targetTagName + '(\\s|$)')

  return reg.test(el.tagName.toLowerCase())
}

/**
 * 激活列表当前项
 * @param els 列表所有项
 * @param el 当前项
 */
export function activeCurrentItem (els, el) {
  // 清空列表激活项
  els.forEach((item) => {
    removeClass(item, 'active')
  })

  // 激活当前项
  addClass(el, 'active')
}