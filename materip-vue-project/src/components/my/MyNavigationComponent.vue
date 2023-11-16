<template>
  <v-app-bar app color="transparent" flat style="position: sticky; margin-left: 3rem">
    <RouterLink to="/mypage/myreview">
      <v-btn text @click="goToReview"><v-icon icon="mdi-note-edit"></v-icon>나의 리뷰</v-btn>
    </RouterLink>
    <RouterLink to="/mypage/mymate">
      <v-btn text @click="goToMate"
        ><v-icon icon="mdi-account-multiple-plus"></v-icon>나의 여행 메이트</v-btn
      >
    </RouterLink>
    <RouterLink to="/mate">
      <v-btn text @click="goToMate"
        ><v-icon icon="mdi-account-multiple-plus"></v-icon>나의 여행 메이트</v-btn
      >
    </RouterLink>
  </v-app-bar>
</template>

<script setup>
import { onMounted, ref } from 'vue'
</script>
