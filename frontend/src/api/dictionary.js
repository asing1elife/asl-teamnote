import BaseApi from './baseApi'

class DictionaryApi extends BaseApi {
  constructor () {
    super('/dictionaries')
  }
}

export default new DictionaryApi()