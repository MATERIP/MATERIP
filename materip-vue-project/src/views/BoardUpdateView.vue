<script setup>
import { ref, onMounted, watch, inject } from "vue";
import { useRouter } from "vue-router";

const axios = inject("axios");

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
  maxCount: "",
  currentCount: "",
  travelSpot: "",
});

const participants = ref([]);
const attraction = ref({
  id: "",
  sidocode: "",
  guguncode: "",
  title: "",
});

const contentId = ref("");

const fetchData = async () => {
  await axios
    .get(`/board/detail/${router.currentRoute.value.params.id}`)
    .then((response) => {
      board.value = response.data["board"];
      console.log(response);
    })
    .catch(function (error) {
      console.log(error);
    });
};

function modify() {
  axios
    .put("/board/modify", board.value)
    .then(() => {
      alert("수정 성공");
      // 수정 후 게시판 상세 페이지로 이동
      router.push(`/board/${board.value.id}`);
    })
    .catch(function (error) {
      console.log(error);
    });
}

const getTravelSpot = async (item) => {
  console.log(item.value);
  await axios
    .post("/attraction/info/contentid/detail?contentId=" + item.value)
    .then((response) => {
      attraction.value = response.data;
      console.log(response.data);
    })
    .catch(function (error) {
      console.log(error);
    });
};

const items = ref([
  {
    name: "공지사항",
    value: "notice",
  },
  {
    name: "여행 메이트 모집",
    value: "recruitment",
  },
  {
    name: "여행 리뷰",
    value: "review",
  },
]);

onMounted(() => {
  // userStore.getUserInfo()
  // 게시판 정보 가져오기
  fetchData();
  // board.value.author = userInfo.value.id

  console.log(board.value);
});

watch(board, (newboard) => {
  contentId.value = board.value.travelSpot;
  getTravelSpot(contentId);
});
</script>

<template>
  <div></div>
  <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem">
    <v-sheet>
      <v-card class="mx-auto pa-12 pb-8" min-width="80rem" rounded="lg">
        <v-card-title>
          <v-layout align-center justify-center-between>
            <v-icon icon="mdi-pencil"></v-icon>
            게시글 수정
          </v-layout>
        </v-card-title>
        <v-form class="mt-5" @submit.prevent="modify">
          <div class="board-title">
            <v-select
              label="게시판 선택"
              variant="underlined"
              single-line
              :items="items"
              item-title="name"
              item-value="value"
              v-model="board.boardType"
              disabled
              style="display: flex; margin-bottom: 1.5rem; width: fit-content"
            >
            </v-select>

            <template v-if="board.boardType === 'recruitment'">
              <div class="count">
                최대 인원 수
                <v-icon icon="mdi-account-group" style="margin: 0 1rem"></v-icon>

                <v-icon
                  icon="mdi-minus"
                  @click="board.maxCount--"
                  style="margin-right: 1rem"
                ></v-icon>
                <div class="max-count">
                  <p>{{ board.maxCount }}</p>
                </div>
                <v-icon
                  icon="mdi-plus"
                  @click="board.maxCount++"
                  style="margin-left: 1rem"
                ></v-icon>
              </div>
            </template>
          </div>
          <div class="attraction-title">
            <v-icon
              icon="mdi-map-marker"
              style="margin: 0.5rem"
              color="red-darken-2"
            ></v-icon>
            {{ attraction.title }}
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
              style="text-align: center; margin: 1rem"
              @click="router.back()"
              >취소</v-btn
            >

            <v-btn
              type="submit"
              color="primary"
              size="large"
              width="fit-content"
              style="text-align: center; margin: 1rem"
              >수정</v-btn
            >
          </div>
        </v-form>
      </v-card>
    </v-sheet>
  </v-layout>
</template>

<style scoped>
.button {
  display: flex;
  justify-content: center;
}

.count {
  display: flex;
  justify-content: center;
  align-items: center;
}

.max-count {
  display: flex;
  align-items: center;
}

.board-title {
  display: flex;
}

.attraction-title {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

i {
  color: #f09404;
  margin-right: 1rem;
}

.v-layout {
  margin: 0;
}

p {
  font-family: "Noto Sans KR", sans-serif;
  font-weight: bold;
}
</style>
