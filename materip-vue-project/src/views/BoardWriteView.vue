<script setup>
import axios from "axios";
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../stores/user-store";
import { storeToRefs } from "pinia";
const router = useRouter();
const board = ref({
  title: "",
  contents: "",
  author: "",
  boardType: "recruitment",
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
  board.value.author = userInfo.value.userInfo.id;
  console.log(board.value);
})

watch(() => board.value.boardType, (newValue) => {
  console.log(newValue);
})

const instance = axios.create({
  baseURL: "http://localhost:8080/",
});

function write() {
  console.log(board.value)
  instance
    .post("/board/write", board.value)
    .then(() => {
      alert("글쓰기 성공");
      router.push("/board");
    })
    .catch(function (error) {
      console.log(error);
    });
}
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
            single-line
            clearable
            :items="items"
            item-title="name"
            item-value="value"
            style="width: 15rem; justify-content: end"
            v-model="board.boardType">
          </v-select>
          <v-text-field clearable label="제목" placeholder="제목을 입력하세요." v-model="board.title" variant="outlined" required width="80%">
          </v-text-field>
          <!-- 내용 textarea 사용자 입력에 맞춰 크기 키우기, 기본 row 개수: 5, 최대 row 개수: 10-->
          <v-textarea clearable label="내용" v-model="board.contents" variant="outlined" required auto-grow counter=500 persistent-counter="true" rows=5 max-rows=10>
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
