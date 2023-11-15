import { createRouter, createWebHistory } from 'vue-router'

import MainView from '../views/MainView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'
import BoardListView from '../views/BoardListView.vue'
import MypageView from '../views/MypageView.vue'
import BoardWriteView from '../views/BoardWriteView.vue'
import BoardDetailView from '../views/BoardDetailView.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name: 'main',
      path: '/',
      component: MainView
    },
    
    {
      name: 'review',
      path: '/board',
      component: BoardListView
    },
    {
      name: 'mate',
      path: '/mate',
      component: BoardListView,
    },
    {
      name: 'write',
      path: '/mate/write',
      component: BoardWriteView,
    },
    {
      name: 'detail',
      path: '/mate/detail/:id',
      component: BoardDetailView,
    },
    {
      name: 'login',
      path: '/login',
      component: LoginView
    },
    {
      name: 'signup',
      path: '/signup',
      component: SignupView
    },
    
    {
      name: 'mypage',
      path: '/mypage',
      component: MypageView 
    }, {
      name: 'logout',
      path: '/'
      
    },
    
  ]
})

export default router
