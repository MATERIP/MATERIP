import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

// Pinia Composing Store
// https://pinia.vuejs.org/cookbook/composing-stores.html
export const useMateStore = defineStore(
  'mateStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')
    var userBoardList = ref([])
    // **************** actions ****************
    const myMate = async () => {
      await axios
        .get('board/myboard/mate')
        .then((response) => {
          console.log(response)
          console.log(response.data['boardList'])
          userBoardList.value = response.data['boardList']
        })
        .catch((response) => {
          
        })
    }

    return {
      myMate,
      userBoardList
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
    const myReview = async () => {
      await axios
        .get('board/myboard/review')
        .then((response) => {
          
          console.log(response.data['boardList'])
          userReviewList.value = response.data['boardList']
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
