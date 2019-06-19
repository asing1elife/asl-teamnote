export default new class Uri {
  decode (value) {
    return decodeURIComponent(value)
  }
  
  encode (value) {
    return encodeURIComponent(value)
  }
}
