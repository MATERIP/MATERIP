import { createRouter, createWebHistory } from 'vue-router'

import MainView from '../views/MainView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'
import BoardListView from '../views/BoardListView.vue'
import MypageView from '../views/MypageView.vue'
import ProfileView from '../views/ProfileView.vue'
import AttracionView from '../views/AttractionView.vue'
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
      component: BoardListView
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
    },
    {
      name: 'logout',
      path: '/'
    },
    {
      name: 'attractionInfo',
      path: '/attraction/{}',
      component: AttracionView
    }
  ]
})

export default router
