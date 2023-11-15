<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../stores/user-store";
import { storeToRefs } from "pinia";
const router = useRouter();
const board = ref({
  title: "",
  contents: "",
  author: "",
  boardType: "",
});
const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);

const items = ref([
  {
    name : '공지사항',
    value : 'notice',
  },
  {
    name : '여행 메이트 모집',
    value : 'recruitment',
  },
  {
    name : '여행 리뷰',
    value : 'review',
  }
])

onMounted(() => {
  userStore.getUserInfo();
  board.value.author = userInfo.value.userInfo.nickname;
  console.log(board.value);
})

function write() {}
</script>

<template>
  <div>
  <!-- {{userInfo}} -->
  </div>
  <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem;">
    <v-sheet>
      <v-card class="mx-auto pa-12 pb-8" min-width="80rem" rounded="lg">
        <v-card-title>
          <v-layout align-center justify-center-between>
            <v-icon icon="mdi-pencil"></v-icon>
            <p style="font-weight: bold">글쓰기</p>
          </v-layout>
        </v-card-title>
        <v-form class="mt-5" @submit.prevent="write">
          <v-select
            label="게시판 선택"
            variant="underlined"
            clearable
            :items="items"
            item-title="name"
            item-value="value"
            style="width: 15rem; justify-content: end"
            v-model="board.boardType">
          </v-select>
          <v-text-field clearable label="제목" placeholder="제목을 입력하세요." v-model="board.title" variant="outlined" required width="80%">
          </v-text-field>
          <v-textarea clearable label="내용" v-model="board.contents" variant="outlined" required>
          </v-textarea>
          <v-btn type="submit" color="light-blue" size="large" width="fit-content" style="display: block; text-align: center; margin : 0 auto; margin-top: 1rem; margin-bottom: 1rem;"  
            >등록</v-btn
          >
        </v-form>
      </v-card>
    </v-sheet>
  </v-layout>
</template>

<style scoped></style>
