<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { storeToRefs } from 'pinia'

import { useUserStore } from '../stores/user-store'
import { useMateStore } from '../stores/board-store'
import { useReviewStore } from '../stores/board-store'

import UserProfileComponent from '../components/my/UserProfileComponent.vue'
import UserReviewComponent from '../components/my/UserReviewComponent.vue'
import UserRecruitComponent from '../components/my/UserRecruitComponent.vue'

const userStore = useUserStore()
const mateStore = useMateStore()
const reviewStore = useReviewStore()
const { navTitle } = storeToRefs(userStore)
const { userInfo } = storeToRefs(userStore)
const { likeState } = storeToRefs(userStore)
const reviewShow = ref(true)
const mateShow = ref(false)

onMounted(() => {
  const route = useRoute()
  const userId = route.params.userId

  userStore.getUserInfo(userId)
  console.log(userInfo.value)
  mateStore.myMate(userId)
  reviewStore.myReview(userId)
  userStore.getLikeState(userId)
  console.log('~~~~~~~~~~~~~~~')
  console.log(likeState.value)

  userStore.getLikeRank(userId)
  userStore.getLikeCnt(userId)
})
const goToReview = () => {
  reviewShow.value = true
  mateShow.value = false
}
const goToMate = () => {
  reviewShow.value = false
  mateShow.value = true
}
</script>

<template>
  <div style="height: 8rem"></div>
  <v-container>
    <v-row no-gutters>
      <v-col cols="3"> <UserProfileComponent /> </v-col>
      <v-col>
        <div>
          <v-app-bar app color="transparent" flat style="position: sticky; margin-left: 3rem">
            <v-btn text @click="goToReview"
              ><v-icon icon="mdi-note-edit"></v-icon>{{ navTitle['review'] }}</v-btn
            >
            <v-btn text @click="goToMate"
              ><v-icon icon="mdi-account-multiple-plus"></v-icon>{{ navTitle['mate'] }}</v-btn
            >
          </v-app-bar>
        </div>
        <div v-show="reviewShow">
          <UserReviewComponent />
        </div>
        <div v-show="mateShow">
          <UserRecruitComponent />
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.max-v-list-height {
  line-height: 5rem;
}
* {
  padding: 0px;
  margin: 0px;
}
</style>
