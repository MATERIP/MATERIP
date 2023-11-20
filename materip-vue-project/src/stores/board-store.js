import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

// Pinia Composing Store
// https://pinia.vuejs.org/cookbook/composing-stores.html
export const useMateStore = defineStore(
  'mateStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')
    const userMateList = ref()

    const changeuserMateList = (usermateList) => { 
      userMateList.value = usermateList
    }
    // **************** actions ****************
    const myMate = async (userId) => {
      await axios
        .get('board/getMateList/' + userId)
        .then((response) => {
          changeuserMateList(response.data)
          
        })
        .catch((response) => {
          // alert(response.data["message"])
        })
    }

    return {
      myMate,
      userMateList
    }
  },
  {
    persist: {
      storage: localStorage
    }
  }
)

export const useBoardReview = defineStore(
  'boardStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')
    var userReviewList = ref([])
    // **************** actions ****************
    const myReview = async (user) => {
      await axios
        .get('board/getReviewList/' + userId)
        .then((response) => {
          const { boardList } = response.data
          userReviewList.value = boardList
        })
        .catch((response) => {})
    }

    return {
      myReview,
      userReviewList
    }
  },
  {
    persist: {
      storage: localStorage
    }
  }
)
