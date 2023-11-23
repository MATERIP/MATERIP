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
        .get('board/getRecruitmentList/'+ userId)
        .then((response) => {
          changeuserMateList(response.data["boardList"])
          console.log(response.data["boardList"])
          console.log('board/getRecruitmentList/'+ userId)
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

export const useReviewStore = defineStore(
  'reviewStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')
    var userReviewList = ref([])


    const changeuserReviewList = (list) => { 
      userReviewList.value = list
    }
    // **************** actions ****************
    const myReview = async (user) => {
      await axios
        .get('board/getReviewList/' + user)
        .then((response) => {
          changeuserReviewList(response.data["boardList"])
          console.log(response.data["boardList"])
          console.log("board/getReviewList/" + user)
          
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