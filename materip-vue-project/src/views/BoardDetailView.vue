<script setup>

import { ref, computed, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const instance = axios.create({
  baseURL: "http://localhost:8080/",
});

const router = useRouter();

const board = ref({
  id: "",
  title: "",
  contents: "",
  author: "",
  hits: "",
  boardType: "",
  createdAt: "",
  modifiedAt: "",
});

const fetchData = () => {
  instance
    .get(`/board/detail/${router.currentRoute.value.params.id}`)
    .then((response) => {
      board.value = response.data;
      console.log(response);
    })
    .catch(function (error) {
      console.log(error);
    });
};

const displayBoardType = computed(() => {
  if (board.value.boardType === "notice") {
    return "공지사항";
  } else if (board.value.boardType === "recruitment") {
    return "메이트 모집";
  } else if (board.value.boardType === "review") {
    return "여행지 리뷰";
  }
  return "";
});

const displayTime = computed(() => {
  return board.value.createdAt.replace('T',' ').replaceAll('-','.')
});

const deleteBoard = () => {
  instance
    .delete(`/board/delete/${board.value.id}`)
    .then(() => {
      alert("삭제 성공");
      router.push(`/board/${board.value.boardType}`);
    })
    .catch(function (error) {
      console.log(error);
    });
};

onMounted(() => {
  fetchData();
});
</script>

<template>
  <div></div>
  <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem">
    <v-sheet>
      <v-card class="mx-auto pa-12 pb-8" min-width="80rem" rounded="lg">
        <v-card-item>
          <v-card-title>
            [{{ displayBoardType}}]
            {{ board.title }}
          </v-card-title>
          <v-card-subtitle>
            <v-icon icon="mdi-account"></v-icon>
            {{ board.author }} |
            <v-icon icon="mdi-clock-outline"></v-icon>
            {{ displayTime }} |
            <v-icon icon="mdi-eye-outline"></v-icon>
            {{ board.hits }}
          </v-card-subtitle>
        </v-card-item>
        <v-divider></v-divider>
        <v-card-text>
          {{ board.contents }}
        </v-card-text>
      </v-card>
      <div class="button">
      <v-btn prepend-icon="mdi-update" color="green" variant="plain" @click="">수정</v-btn>
      <v-btn prepend-icon="mdi-delete" style="margin-left: 1rem;" color="red" variant="plain" @click="deleteBoard">삭제</v-btn>
      </div>
    </v-sheet>
  </v-layout>
</template>

<style scoped>
.button {
  display: flex;
  justify-content: flex-end;
  margin-top: 1rem;
}
</style>
