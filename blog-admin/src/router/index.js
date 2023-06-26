/*
 * @Date: 2023-06-23 17:22:17
 * @LastEditors: Fishing yoo725@hotmail.com
 * @LastEditTime: 2023-06-25 15:25:43
 * @FilePath: \blog-admin\src\router\index.js
 * @Description: https://github.com/iFishin
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/login/Login.vue'
import Home from '../components/home/Home.vue'
import Welcome from '../components/Welcome.vue'
import Users from '../components/users/Users.vue'
import Article from '../components/article/Article.vue'
import Add from '../components/article/Add.vue'
import Cate from '../components/cate/Cate.vue'
import Setting from '../components/website/Setting.vue'

Vue.use(VueRouter)

const router = new VueRouter({
  base: '/admin/',
  routes: [{
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [{
          path: '/welcome',
          component: Welcome
        },
        {
          path: '/users',
          component: Users
        },
        {
          path: '/article',
          component: Article
        },
        {
          path: '/article/add',
          component: Add
        },
        {
          path: '/cate',
          component: Cate
        },
        {
          path: '/setting',
          component: Setting
        }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 如果要访问登录页，则直接放行
  if (to.path === '/login') {
    return next()
  }
  // 获取 token
  const token = window.sessionStorage.getItem('token')
  // 如果 token 不存在或已过期，则跳转到登录页
  if (!token || isTokenExpired(token)) { // isTokenExpired() 表示检查token是否过期的函数
    return next('/login')
  }
  // 如果 token 存在且未过期，则允许访问目标页
  next()
})

// 检查 token 是否过期的函数
function isTokenExpired(token) {
  // 这里根据具体情况编写代码，比如解析 token 并检查过期时间等
  return false // 假设 token 未过期
}

// 路由守卫
router.beforeEach((to, form, next) => {
  if (to.path === '/login') return next()
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  next()
})

export default router
