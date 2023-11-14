import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

// Pinia Composing Store
// https://pinia.vuejs.org/cookbook/composing-stores.html
export const useUserStore = defineStore(
  'userStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')

    const searchInput = ref('')
    const searchResult = ref([])
    // **************** actions ****************
    const searchTravelSpot = async (searchInput) => {
      // 서버로 요청

      await axios.post('/admin/user/login', searchInput).then((response) => {
        console.log(response)
        searchResult.value = response.value
      })
    }

    return {
      searchInput,
      searchTravelSpot,
      searchResult
    }
  },
  {
    persist: {
      storage: localStorage
    }
  }
)
