<script setup>

import { onMounted, ref, nextTick } from 'vue'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user-store'
const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)
const { auth } = storeToRefs(userStore)
const { likeState } = storeToRefs(userStore)
const { likePercent } = storeToRefs(userStore)
const { likeRank } = storeToRefs(userStore)

const router = useRouter()
const iconColor = ref('white')
const iconBgColor = ref('grey')
onMounted(() => {
  userStore.getLikeRank(userInfo.value.id)
  userStore.getLikeCnt(userInfo.value.id)
  animateProgressBar(likePercent.value)

  console.log('auth ' + auth.value)

  if (likeState.value) {
    iconColor.value = "red";
    iconBgColor.value = "white";
  }
});

const progressValue = ref(0); // 초기 게이지 바 값 (0-100 범위)
const animationDuration = 2000; // 애니메이션 지속 시간 (2초)

const animateProgressBar = (target) => {
  const interval = 20; // 애니메이션 갱신 간격 (20ms)
  const frames = animationDuration / interval;

  const increment = (target - progressValue.value) / frames;

  let currentFrame = 0;

  const animate = () => {
    if (currentFrame < frames) {
      progressValue.value += increment;
      currentFrame++;
      requestAnimationFrame(animate);
    } else {
      // 애니메이션 완료
      progressValue.value = target;
    }
  };

  animate();
};

const toggleButton = () => {
  if (likeState.value) {
    userStore.toggleLikeState();
    console.log("좋아요 취소");
    console.log(likeState.value);
    userStore.deleteLikeState(userInfo.value.id);
    iconBgColor.value = "grey";
    iconColor.value = "white";
    console.log(iconBgColor.value + " " + iconColor.value);
  } else {

    userStore.toggleLikeState()

    console.log('좋아요')
    console.log(likeState.value)
    userStore.addLikeState(userInfo.value.id)
    iconBgColor.value = 'white'
    iconColor.value = 'red'
    console.log(iconBgColor.value + ' ' + iconColor.value)
  }

  userStore.getLikeRank(userInfo.value.id)
  userStore.getLikeCnt(userInfo.value.id)
  animateProgressBar(likePercent.value)
}

const goToWithdrawal = () => {
  var input = confirm("정말 탈퇴하시겠습니까? 탈퇴하시면 모든 정보가 삭제됩니다.");
  if (input === true) {
    userStore.withdrawal(userInfo.value.id);
    alert("탈퇴되었습니다.");
  } else {
    alert("탈퇴가 취소되었습니다.");
  }
};
</script>

<template>
  <div id="app" class="d-flex justify-center mb-6">
    <v-app>
      <v-main>
        <v-container fluid>
          <v-card
            max-width="450px"
            min-width="300px"
            class="mx-auto bg align-center"
            elevation="2"
          >
            <v-img class="" height="200px" src="src/assets/background.jpg" cover> </v-img>
            <v-row justify="center">
              <v-col
                align-self="start"
                class="d-flex justify-center align-center pa-0"
                cols="12"
              >
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
                  <div class="nickname">{{ userInfo.nickname }}</div></v-list-item-title
                >
                <v-list-item-subtitle class="max-v-list-height">
                  {{ userInfo.name }}
                </v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-spacer></v-spacer>

            <v-row align="center">
              <!-- 게이지 바 -->
              <v-col cols="3" align-right style="padding: 0%" class="d-flex justify-end">
                <div v-if="!auth">
                  <v-btn
                    style="min-width: 2rem; min-height: 3rem; border-radius: 100%"
                    :color="iconBgColor"
                    v-model="likeState"
                    @click="toggleButton"
                  >
                    <v-icon icon="mdi-heart" :style="{ color: iconColor }"></v-icon>
                  </v-btn>
                </div>
                <div v-if="auth">
                  <v-btn
                    style="min-width: 2rem; min-height: 3rem; border-radius: 100%"
                    :color="iconBgColor"
                    v-model="likeState"
                    class="custom-btn"
                  >
                    <v-icon icon="mdi-heart" :style="{ color: iconColor }"></v-icon>
                  </v-btn>
                </div>
              </v-col>
              <v-col cols="6" style="" align-left>
                <v-progress-linear
                  v-model="progressValue"
                  height="5"
                  color="red"
                  :rounded="true"
                ></v-progress-linear>
              </v-col>
              <v-col cols="3" align-center>{{ Math.floor(progressValue) }}% </v-col>
            </v-row>
            <v-spacer></v-spacer>

            <v-row>
              <v-col class="text-center">
                <v-container>
                  <v-list-item-content class="sutitles">
                    <v-list-item-title class="text-h6"> #{{ likeRank }} </v-list-item-title>
                    <v-list-item-subtitle class="text-caption">랭킹</v-list-item-subtitle>
                  </v-list-item-content>
                </v-container>
              </v-col>
            </v-row>
            <v-spacer style="height: 1rem"></v-spacer>

            <template v-if="auth">
              <div class="button-container">
                <v-btn color="success" @click="router.push('modify')">수정</v-btn>
                <v-btn color="red" @click="goToWithdrawal">탈퇴</v-btn>
              </div>
            </template>
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
.button-container {
  display: flex;
  justify-content: space-evenly;
}

.custom-btn:hover,
.custom-btn.v-btn--active {
  background-color: inherit; /* or the color you want to set */
  color: inherit; /* or the color you want to set */
}

.nickname {
  font-family: "NPSfontBold";
}
</style>
