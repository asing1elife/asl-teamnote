/**
 * 验证类
 */
export default new class Verify {
  /**
   * 对象是否为空
   */
  objIsNull (val) {
    return Object.keys(val).length === 0
  }
}
