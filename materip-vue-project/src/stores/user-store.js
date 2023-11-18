import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

// Pinia Composing Store
// https://pinia.vuejs.org/cookbook/composing-stores.html
export const useUserStore = defineStore(
  'userStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')
    const userInfo = ref()
    const userId = ref()
    const isAdmin = ref(0)
    const menuList = ref([
      { name: '로그인', show: true, routeName: 'login' },
      { name: '마이페이지', show: false, routeName: 'mypage' },
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
    // **************** actions ****************
    const login = async (userInfo) => {
      // 서버로 요청
      console.log(userInfo)
      await axios.post('/user/login', userInfo).then((response) => {
        console.log(response)
        const accessToken = response.data["accessToken"]
        

        // 이미 페이지가 로드된 시점에 로그인을 수행 했으므로
        // axios 객체의 아래 값은 초기화가 되어있지 않음으로 값을 저장.
        // accessToken을 storage에 저장하는 경우 취약점이 발생할 수 있다.
        // pinia-plugin-persistedstate 를 사용하는 경우 storage에 저장되는 것을 막아야한다.
        axios.defaults.headers.common['Authorization'] = accessToken
        
        // 로그인을 성공하여 토큰이 정상적으로 저장된 경우
        // 메뉴 표시를 수정.
        alert('로그인!')
        changeMenuState()
        changeUserState(response.data["userId"], response.data["isAdmin"])
      })
    }

    const logout = async () => {
      changeMenuState()
      await axios.delete('/user/logout').then(() => {
        axios.defaults.headers.common['Authorization'] = ''
        isAdmin.value = 0
        userId.value = ''
        userInfo.value = null
        alert('로그아웃!')
      })
    }

    const getUserInfo = async (userId) => {
      await axios
        .get('/user/' + userId)
        .then((response) => {
          changeUserInfoState(response.data["userInfo"])
        })
        .catch((response) => {
          
        })
    }

    // 회원 정보를 수정하면 axios header에 저장된 accessToken을 삭제한다.
    const modify = async (userInfo) => {
      await axios.put('/user/modify', userInfo).then(() => {
        axios.defaults.headers.common['Authorization'] = ''
        changeMenuState()
      })
    }

    // 회원 탈퇴 시 axios header에 저장된 accessToken을 삭제한다.
    const withdrawal = async (userInfo) => {
      await axios.delete('user/withdrawal', userInfo).then(() => {
        axios.defaults.headers.common['Authorization'] = ''
        changeMenuState()
      })
    }

    return {
      login,
      logout,
      modify,
      withdrawal,
      userInfo,
      isAdmin,
      userId,
      getUserInfo,
      menuList
    }
  },
  {
    persist: {
      storage: localStorage
    }
  }
)
