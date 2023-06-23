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
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    {
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [
        { path: '/welcome', component: Welcome },
        { path: '/users', component: Users },
        { path: '/article', component: Article },
        { path: '/article/add', component: Add },
        { path: '/cate', component: Cate },
        { path: '/setting', component: Setting }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach((to, form, next) => {
  if (to.path === '/login') return next()
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  next()
})

export default router
