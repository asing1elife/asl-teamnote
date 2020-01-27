const path = require('path')

function resolve (dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  devServer: {
    // 前端请求的链接
    host: 'localhost',
    // 前端请求的端口
    port: 3000,
    // 代理
    proxy: {
      '/teamnote/api/': {
        target: 'http://127.0.0.1:8091',
        changeOrigin: true,
        pathRewrite: {
          '^/teamnote/api/': '/teamnote/api/'
        }
      }
    }
  },
  chainWebpack: (config) => {
    const alias = config.resolve.alias

    alias
      .set('@', resolve('src'))
      .set('assets', resolve('src/assets'))
      .set('views', resolve('src/views'))
      .set('components', resolve('src/components'))
      .set('model', resolve('src/model'))
      .set('router', resolve('src/router'))
      .set('store', resolve('src/store'))
      .set('api', resolve('src/api'))

    config.module
      .rule('vue')
      .test(/\.vue$/)
      .use('iview-loader')
      .loader('iview-loader')
      .options({
        prefix: true
      })
  }
}