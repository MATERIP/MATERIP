import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

// Pinia Composing Store
// https://pinia.vuejs.org/cookbook/composing-stores.html
export const useTravelStore = defineStore(
  'travelStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')

    const searchInput = ref('')
    const searchResult = ref([])
    // **************** actions ****************
    const searchTravelSpot = async () => {
      // 서버로 요청
      if (searchResult.value != [] && searchResult.value.length != 0) {
        console.log('안빔!')
        console.log()
        return searchResult.value
      } else {
        await axios.get('/attraction/info', searchInput.value).then((response) => {
          console.log(response.data)
          searchResult.value = response.data
          console.log(searchResult.value)
          return response.data
        })
      }
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
