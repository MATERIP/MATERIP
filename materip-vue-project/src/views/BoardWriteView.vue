<script setup>
import { ref, onMounted, watch, inject, computed } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../stores/user-store";
import { storeToRefs } from "pinia";

const axios = inject("axios");
const userStore = useUserStore();
const { userInfo, userId } = storeToRefs(userStore);
const { isAdmin } = storeToRefs(userStore);
const router = useRouter();
const board = ref({
  title: "",
  contents: "",
  author: userId.value,
  boardType: null,
  maxCount: 2,
  currentCount: 0,
});

const items = ref([
  {
    name: "공지사항",
    value: "notice",
    show: false,
  },
  {
    name: "여행 메이트 모집",
    value: "recruitment",
    show: true,
  },
  {
    name: "여행 리뷰",
    value: "review",
    show: true,
  },
]);

onMounted(() => {
  userStore.getUserInfo(userId.value);
  console.log(userInfo.value.id);
  console.log(isAdmin.value);
  if (isAdmin.value === 1) {
    items.value[0].show = true;
  }

  board.value.author = userInfo.value.id;

  // console.log(board.value);
});

watch(
  () => board.value.boardType,
  (newValue) => {
    console.log(newValue);
  }
);

const write = async () => {
  console.log(board.value);
  await axios
    .post("/board/write", board.value)
    .then(() => {
      alert("글쓰기 성공");
      if (`${board.value.boardType}` === "notice") {
        router.push(`/board/review`);
      } else {
        router.push(`/board/${board.value.boardType}`);
      }
    })
    .catch(function (error) {
      alert("로그인 후 이용해주세요.");
      console.log(error);
    });
};
</script>

<template>
  <div>
    <!-- {{userInfo}} -->
  </div>
  <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem">
    <v-sheet>
      <v-card class="mx-auto pa-12 pb-8" min-width="80rem" rounded="lg">
        <v-card-title>
          <v-layout align-center justify-center-between>
            <v-icon icon="mdi-pencil"></v-icon>
            <p style="font-weight: bold">글쓰기</p>
          </v-layout>
        </v-card-title>
        <v-form class="mt-5" @submit.prevent="write">
          <div class="board-title">
            <v-select
              label="게시판 선택"
              variant="underlined"
              single-line
              clearable
              :items="items.filter((item) => item.show)"
              item-title="name"
              item-value="value"
              v-model="board.boardType"
              style="display: flex; margin-bottom: 1.5rem; width: 10rem"
            >
            </v-select>
            <template v-if="board.boardType === 'recruitment'">
              <div class="count">
                <p>최대 인원 수</p>
                <v-icon icon="mdi-account-group" style="margin: 0 1rem"></v-icon>

                <v-icon
                  icon="mdi-minus"
                  @click="board.maxCount > 2 ? board.maxCount-- : board.maxCount"
                  style="margin-right: 1rem"
                ></v-icon>
                <div class="max-count">
                  <p>{{ board.maxCount }}</p>
                </div>
                <v-icon
                  icon="mdi-plus"
                  @click="board.maxCount < 10 ? board.maxCount++ : board.maxCount"
                  style="margin-left: 1rem"
                ></v-icon>
              </div>
            </template>
          </div>
          <v-text-field
            clearable
            label="제목"
            placeholder="제목을 입력하세요."
            v-model="board.title"
            variant="outlined"
            required
            width="80%"
          >
          </v-text-field>
          <!-- 내용 textarea 사용자 입력에 맞춰 크기 키우기, 기본 row 개수: 5, 최대 row 개수: 10-->
          <v-textarea
            clearable
            label="내용"
            v-model="board.contents"
            variant="outlined"
            required
            auto-grow
            counter="500"
            persistent-counter="true"
            rows="5"
            max-rows="10"
          >
          </v-textarea>
          <div class="button">
            <v-btn
              color="grey"
              size="large"
              width="fit-content"
              style="display: flex; text-align: center; margin: 1rem"
              @click="router.back"
            >
              취소</v-btn
            >
            <v-btn
              type="submit"
              color="light-blue"
              size="large"
              width="fit-content"
              style="
                display: flex;
                text-align: center;
                margin-top: 1rem;
                margin-bottom: 1rem;
              "
              >등록</v-btn
            >
          </div>
        </v-form>
      </v-card>
    </v-sheet>
  </v-layout>
</template>

<style scoped>
.count {
  display: flex;
  justify-content: center;
  margin-bottom: 1rem;
  align-items: center;
}

.max-count {
  display: flex;
  align-items: center;
}

.board-title {
  display: flex;
}

.button {
  display: flex;
  justify-content: center;
}

p {
  font-family: "Noto Sans KR", sans-serif;
  font-weight: bold;
}
</style>
