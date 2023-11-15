import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

// Pinia Composing Store
// https://pinia.vuejs.org/cookbook/composing-stores.html
export const useUserStore = defineStore(
  'userStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')

    const menuList = ref([
      { name: '로그인', show: true, routeName: 'login' },
      { name: '마이페이지', show: false, routeName: 'mypage' },
      { name: '로그아웃', show: false, routeName: 'logout' }
    ])
    const userInfo = ref();
    const changeMenuState = () => {
      menuList.value.forEach((menu) => {
        menu.show = !menu.show
      })
    }
    
    // **************** actions ****************
    const login = async (userInfo) => {
      // 서버로 요청
      console.log(userInfo)
      await axios.post('/admin/user/login', userInfo).then((response) => {
        console.log(response)
        const { accessToken } = response.data

        // 이미 페이지가 로드된 시점에 로그인을 수행 했으므로
        // axios 객체의 아래 값은 초기화가 되어있지 않음으로 값을 저장.
        // accessToken을 storage에 저장하는 경우 취약점이 발생할 수 있다.
        // pinia-plugin-persistedstate 를 사용하는 경우 storage에 저장되는 것을 막아야한다.
        axios.defaults.headers.common['Authorization'] = accessToken
        
        // 로그인을 성공하여 토큰이 정상적으로 저장된 경우
        // 메뉴 표시를 수정.
        alert("로그인!");
        changeMenuState()
      })
    }

    const logout = async () => {
      changeMenuState()
      await axios.delete('admin/user/logout').then(() => {

        axios.defaults.headers.common['Authorization'] = ''
        alert("로그아웃!");
        
      })
    }

    const getUserInfo = async () => {
      console.log("getUserInfo");
      await axios.get('/admin/user/myPage').then((response) => {
        console.log(response);
        userInfo.value = response.data;
        console.log(axios.defaults.headers.common['Authorization']);
        return response.value;
      }).catch((response) => { 
        alert(response.message);
      })
    }

    // 회원 정보를 수정하면 axios header에 저장된 accessToken을 삭제한다.
    const modify = async (userInfo) => {
      await axios.put('/admin/user/modify', userInfo).then(() => {
        axios.defaults.headers.common['Authorization'] = ''
        changeMenuState()
      })
    }

    // 회원 탈퇴 시 axios header에 저장된 accessToken을 삭제한다.
    const withdrawal = async (userInfo) => {
      await axios.delete('admin/user/withdrawal', userInfo).then(() => {
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
