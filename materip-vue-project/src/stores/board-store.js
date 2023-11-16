import { ref, inject } from 'vue'
import { defineStore } from 'pinia'

// Pinia Composing Store
// https://pinia.vuejs.org/cookbook/composing-stores.html
export const useBoardStore = defineStore(
  'boardStore',
  () => {
    // ***************** state *****************
    const axios = inject('axios')
    var userBoardList = ref()
    // **************** actions ****************
    const myBoard = async () => {
      await axios
        .get('board/myboard')
        .then((response) => {
          console.log(response.data['boardList'])
          userBoardList.value = response.data['boardList']
        })
        .catch((response) => {})
    }

    return {
      myBoard,
      userBoardList
    }
  },
  {
    persist: {
      storage: localStorage
    }
  }
)
