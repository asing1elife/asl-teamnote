/** vuex的异步请求以及对mutation的封装 */

import * as types from './mutation-types'
import dictionary from 'api/dictionary'

export const getDictionaries = function ({commit}) {
  dictionary.list().then((res) => {
    commit(types.SET_DICTIONARIES, res.data)
  })
}