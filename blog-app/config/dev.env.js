/*
 * @Date: 2023-06-22 10:17:02
 * @LastEditors: Fishing yoo725@hotmail.com
 * @LastEditTime: 2023-06-26 19:09:38
 * @FilePath: \blog-app\config\dev.env.js
 * @Description: https://github.com/iFishin
 */
'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')


module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API: '"http://localhost:8888/"'
})
