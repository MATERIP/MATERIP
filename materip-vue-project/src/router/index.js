import { createRouter, createWebHistory } from 'vue-router'

import MainView from '../views/MainView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'
import BoardListView from '../views/BoardListView.vue'
import MypageView from '../views/MypageView.vue'

import MyRecruitView from '../components/my/MyRecruitComponent.vue'
import MyReviewView from '../components/my/MyReviewComponent.vue'
import AttractionView from '../views/AttractionView.vue'
// import ProfileView from '../views/ProfileView.vue'

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
      path: '/board/review',
      component: BoardListView
    },
    {
      name: 'recruitment',
      path: '/board/recruitment',
      component: BoardListView
    },
    {
      name: 'write',
      path: '/board/write',
      component: BoardWriteView
    },
    {
      name: 'boardDetail',
      path: '/board/:id',
      component: BoardDetailView
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
      component: MypageView,
      children: [
        {
          path: 'myreview', // Remove the leading '/'
          component: MyReviewView
        },
        {
          path: 'mymate', // Remove the leading '/'
          component: MyRecruitView
        }
      ]
    },

    {
      name: 'logout',
      path: '/'
    },

    {
      name: 'attractionInfo',
      path: '/attraction/:attractionId', // Correct dynamic parameter syntax
      component: AttractionView
    }
  ]
})

export default router
