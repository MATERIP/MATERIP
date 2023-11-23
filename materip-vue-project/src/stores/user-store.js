import { ref, inject } from 'vue'
import { defineStore } from 'pinia'
import { useRouter } from 'vue-router'

// Pinia Composing Store
// https://pinia.vuejs.org/cookbook/composing-stores.html
export const useUserStore = defineStore(
  'userStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')
    const userInfo = ref({ nickname: 'unknowon', name: 'unknowon' }) // mypage 유저 정보 (접속한 사용자 정보 아님)
    const userId = ref() // 접속한 사용자 아이디
    const isAdmin = ref(0) // 접속한 관리자 정보 (0: 일반 사용자, 1: 관리자)
    const auth = ref(false) // 접속한 사용자가 자신의 정보를 수정하는 경우 true, 아니면 false
    const router = useRouter()
    const likeState = ref(false)

    const navTitle = ref({ review: '나의 리뷰', mate: '나의 여행 메이트' })

    const menuList = ref([
      { name: '로그인', show: true, routeName: 'login' },
      { name: '마이페이지', show: false, routeName: 'user' },
      { name: '로그아웃', show: false, routeName: 'logout' }
    ])

    const changeMenuState = () => {
      menuList.value.forEach((menu) => {
        menu.show = !menu.show
      })
    }
    const changeUserState = (userid, isadmin) => {
      userId.value = userid
      isAdmin.value = isadmin
    }

    const changeUserInfoState = (userinfo) => {
      userInfo.value = userinfo
    }

    const changeAuthState = (Auth) => {
      auth.value = Auth
    }

    const changeLikeState = (likestate) => {
      likeState.value = likestate
    }
    // **************** actions ****************
    const login = async (userInfo) => {
      // 서버로 요청
      console.log(userInfo)
      console.log(userId)
      await axios.post('/user/login', userInfo).then((response) => {
        console.log(response)
        const accessToken = response.data['accessToken']

        // 이미 페이지가 로드된 시점에 로그인을 수행 했으므로
        // axios 객체의 아래 값은 초기화가 되어있지 않음으로 값을 저장.
        // accessToken을 storage에 저장하는 경우 취약점이 발생할 수 있다.
        // pinia-plugin-persistedstate 를 사용하는 경우 storage에 저장되는 것을 막아야한다.
        axios.defaults.headers.common['Authorization'] = accessToken

        // 로그인을 성공하여 토큰이 정상적으로 저장된 경우
        // 메뉴 표시를 수정.
        alert('로그인!')
        changeUserInfoState({
          id: response.data['userId'],
          password: '',
          email: '',
          name: '',
          nickname: '',
          tel: '',
          birth: '',
          gender: 0,
          admin: 0,
          joinDate: '',
          modifiedAt: ''
        })
        changeMenuState()
        changeUserState(response.data['userId'], response.data['isAdmin'])
      })
    }

    const logout = async () => {
      changeMenuState()
      await axios.delete('/user/logout').then(() => {
        axios.defaults.headers.common['Authorization'] = ''
        isAdmin.value = 0
        userId.value = ''
        userInfo.value = null
        auth.value = false
        alert('로그아웃!')
      })
    }

    const getUserInfo = async (Id) => {
      await axios
        .get('/user/' + Id)
        .then((response) => {
          changeUserInfoState(response.data)
          response.data['id'] == userId.value ? changeAuthState(true) : changeAuthState(false)
          if (auth.value) {
            navTitle.value = {
              review: '나의 리뷰',
              mate: '나의 여행 메이트'
            }
          } else {
            navTitle.value = {
              review: Id + ' 리뷰',
              mate: Id + ' 여행 메이트'
            }
          }
        })
        .catch((response) => {})
    }

    const getLikeState = async (Id) => {
      await axios
        .post('/user/like-state', { id: userId.value, sequence: 0, likedBy: Id })
        .then((response) => {
          console.log({ id: userId.value, sequence: 0, likedBy: Id })
          changeLikeState(response.data['likeState'])
        })
    }
    const toggleLikeState = async () => {
      console.log(likeState.value)
      likeState.value = !likeState.value
    }
    const addLikeState = async (Id) => {
      await axios
        .post('/user/like', { id: userId.value, sequence: 0, likedBy: Id })
        .then((response) => {})
    }

    const deleteLikeState = async (Id) => {
      await axios
        .post('/user/unlike', { id: userId.value, sequence: 0, likedBy: Id })
        .then((response) => {})
    }

    // 회원 정보를 수정하면 axios header에 저장된 accessToken을 삭제한다.
    const modify = async (userInfo) => {
      console.log(userInfo.value)
      await axios.put('/user/modify', userInfo).then(() => {
        alert('수정 완료! 다시 로그인 해주세요.')
        router.push('/')
        axios.defaults.headers.common['Authorization'] = ''
        changeMenuState()
      })
    }

    // 회원 탈퇴 시 axios header에 저장된 accessToken을 삭제한다.
    const withdrawal = async () => {
      await axios.delete('user/withdrawal', { data: userId.value }).then(() => {
        console.log(userId.value)
        axios.defaults.headers.common['Authorization'] = ''
        changeMenuState()
        router.push('/')
        isAdmin.value = 0
        userId.value = ''
        userInfo.value = { nickname: 'unknowon', name: 'unknowon' }
        auth.value = false
      })
    }

    return {
      menuList,

      login,
      logout,
      modify,
      withdrawal,

      userId,
      userInfo,
      isAdmin,
      auth,
      navTitle,

      addLikeState,
      toggleLikeState,
      deleteLikeState,
      getUserInfo,
      getLikeState,

      likeState
    }
  },
  {
    persist: {
      storage: localStorage
    }
  }
)