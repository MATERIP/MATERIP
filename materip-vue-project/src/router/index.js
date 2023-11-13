import { createRouter, createWebHistory } from 'vue-router'

import MainView from "../views/MainView.vue"
import LoginView from "../views/LoginView.vue"
import SignupView from "../views/SignupView.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name: 'main',
      path: '/',
      component: MainView
    },
    {
      name: 'login',
      path:'/login',
      component: LoginView
    },
    {
      name: 'signup',
      path: '/signup',
      component: SignupView
    }
  ]
})

export default router
