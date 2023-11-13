<template>
  <v-app-bar app color="transparent" flat class="px-10">
    <v-toolbar-title min-width="10rem">
      <RouterLink to="/">
        <v-img src="src/assets/orglogo.png" alt="Logo" max-height="32"></v-img>
      </RouterLink>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <RouterLink to="/review">
      <v-btn text @click="goToReview"
        ><v-icon icon="mdi-note-edit"></v-icon>여행지 리뷰</v-btn
      >
    </RouterLink>
    <RouterLink to="/mate">
      <v-btn text @click="goToMate"
        ><v-icon icon="mdi-account-multiple-plus"></v-icon>여행 메이트</v-btn
      >
    </RouterLink>
    <v-autocomplete
      :items="items"
      auto-select-first
      class="flex-full-width align-self-center transparent-background px-7"
      density="comfortable"
      item-props
      menu-icon=""
      placeholder="search Travel "
      prepend-inner-icon="mdi-magnify"
      rounded
      theme="light"
      variant="solo"
      hide-details
    >
    </v-autocomplete>
    <RouterLink to="/login">
      <v-btn text @click="goToLogin" class="mr-10"
        ><v-icon icon="mdi-account"></v-icon>로그인</v-btn
      >
    </RouterLink>
  </v-app-bar>
</template>

<script setup>
import { ref } from "vue";
const items = ref(["setup", "settup", "mainview"]);
</script>
