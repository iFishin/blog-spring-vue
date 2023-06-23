const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

module.exports = {
  lintOnSave: true,
  publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
  publicPath: '/admin/'
}