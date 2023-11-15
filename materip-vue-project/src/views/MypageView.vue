<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../stores/user-store'
import { storeToRefs } from 'pinia'
const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)
onMounted(() => {
  userStore.getUserInfo()

  animateProgressBar()
})

const progressValue = ref(0) // 초기 게이지 바 값 (0-100 범위)
const targetValue = 80 // 원하는 게이지 바 값
const animationDuration = 2000 // 애니메이션 지속 시간 (2초)

const animateProgressBar = () => {
  const interval = 20 // 애니메이션 갱신 간격 (20ms)
  const frames = animationDuration / interval

  const increment = (targetValue - progressValue.value) / frames

  let currentFrame = 0

  const animate = () => {
    if (currentFrame < frames) {
      progressValue.value += increment
      currentFrame++
      requestAnimationFrame(animate)
    } else {
      // 애니메이션 완료
      progressValue.value = targetValue
    }
  }

  animate()
}
</script>
<template>
  <div style="height: 100px"></div>
  <div id="app" class="d-flex justify-center mb-6">
    <v-app>
      <v-main>
        <v-container fluid>
          <v-card max-width="450px" class="mx-auto bg" elevation="2">
            <v-img class="" height="200px" src="src/assets/profilebackground.png" cover> </v-img>
            <v-row justify="center">
              <v-col align-self="start" class="d-flex justify-center align-center pa-0" cols="12">
                <v-avatar
                  class="profile avatar-center-heigth avatar-shadow"
                  color="grey"
                  size="164"
                >
                  <v-img src="src/assets/profile.png"> </v-img>
                </v-avatar>
              </v-col>
            </v-row>
            <v-list-item color="#0000" class="profile-text-name ma-4 pt-16">
              <v-list-item-content>
                <v-list-item-title class="text-h2 max-v-list-height">
                  {{ userInfo.userInfo.nickname }}</v-list-item-title
                >
                <v-list-item-subtitle class="max-v-list-height">{{
                  userInfo.userInfo.id
                }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-spacer></v-spacer>

            <v-row align="center">
              <!-- 게이지 바 -->
              <v-col cols="3" align-right style="padding: 0%" class="d-flex justify-end">
                <v-btn style="min-width: 2rem; min-height: 3rem; border-radius: 100%">
                  <v-icon><v-icon icon="mdi-heart" color="red"></v-icon></v-icon>
                </v-btn>
              </v-col>
              <v-col cols="6" style="" align-left>
                <v-progress-linear
                  v-model="progressValue"
                  height="5"
                  color="red"
                  :rounded="true"
                ></v-progress-linear>
              </v-col>
              <v-col cols="3" align-center>{{ Math.floor(progressValue) }} </v-col>
            </v-row>
            <v-spacer></v-spacer>

            <v-row>
              <v-col class="text-center">
                <v-container>
                  <v-list-item-content class="sutitles">
                    <v-list-item-title class="text-h6"> 1002 </v-list-item-title>
                    <v-list-item-subtitle class="text-caption">Curtidas</v-list-item-subtitle>
                  </v-list-item-content>
                </v-container>
              </v-col>
            </v-row>
            <v-spacer style="height: 1rem"></v-spacer>
          </v-card>
        </v-container>
      </v-main>
    </v-app>
  </div>
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
