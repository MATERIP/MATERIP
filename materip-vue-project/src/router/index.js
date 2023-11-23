import { createRouter, createWebHistory } from 'vue-router'

import MainView from '../views/MainView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'
import BoardListView from '../views/BoardListView.vue'
import UserView from '../views/UserView.vue'
import UserModifyView from '../views/UserModifyView.vue'
import SearchComponent from '../components/SearchComponent.vue'
import UserReviewComponent from '../components/my/UserReviewComponent.vue'
import UserRecruitComponent from '../components/my/UserRecruitComponent.vue'
import AttractionView from '../views/AttractionView.vue'
import AttractionDetailView from '../views/AttractionDetailView.vue'

import BoardWriteView from '../views/BoardWriteView.vue'
import BoardDetailView from '../views/BoardDetailView.vue'
import BoardUpdateView from '../views/BoardUpdateView.vue'
import CarouselComponent from '../components/CarouselComponent.vue'

import MapComponentView from '../components/MapComponent.vue'
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
      name: 'boardUpdate',
      path: '/board/update/:id',
      component: BoardUpdateView
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
      path: '/user/:userId',
      component: UserView,
      children: [
        {
          path: 'myreview', // Remove the leading '/'
          component: UserReviewComponent
        },
        {
          path: 'myrecruit', // Remove the leading '/'
          component: UserRecruitComponent
        }
      ]
    },

    {
      name: 'logout',
      path: '/'
    },

    {
      name: 'attractionInfo',
      path: '/attraction/:attractionName', // Correct dynamic parameter syntax
      component: AttractionView
    },
    {
      name: 'attractionDetail',
      path: '/attraction/detail/:contentId', // Correct dynamic parameter syntax
      component: AttractionDetailView
    },
    {
      name: 'modify',
      path: '/user/modify',
      component: UserModifyView
    },
    {
      name: 'map',
      path: '/map',
      component: MapComponentView
    },

    {
      name: 'carousel',
      path: '/carousel',
      component: CarouselComponent
    }
  ]
})

export default router
