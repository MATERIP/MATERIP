<script setup>
import { ref, computed, onMounted, inject, watch } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../stores/user-store";
import { storeToRefs } from "pinia";

const axios = inject("axios");

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);

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
});

const auth = ref(false);
const participants = ref(0);
const isJoinable = ref();

const fetchData = async () => {
  await axios
    .get(`/board/detail/${router.currentRoute.value.params.id}`)
    .then((response) => {
      board.value = response.data["board"];
      auth.value = response.data["auth"];
      // console.log(response);
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
  return board.value.createdAt.replace("T", " ").replaceAll("-", ".");
});

const deleteBoard = async () => {
  let boardType = board.value.boardType;
  await axios
    .delete(`/board/delete/${board.value.id}`)
    .then(() => {
      alert("삭제 성공");
      if (boardType === "notice") {
        router.push(`/board/review`);
      } else {
        router.push(`/board/${board.value.boardType}`);
      }
    })
    .catch(function (error) {
      console.log(error);
    });
};

const goToUpdate = () => {
  router.push(`/board/update/${board.value.id}`);
};

const join = async () => {
  await axios
    .post(`/board/participants/join`, {
      boardId: board.value.id,
      userId: userInfo.value.id,
    })
    .then(() => {
      alert("참여 성공");
      isJoinable.value = false;
    })
    .catch(function (error) {
      console.log(error);
    });
  if (isJoinable.value) {
    isJoinable.value != isJoinable.value;
  }
  readParticpantsCount();
};

const leave = async () => {
  await axios
    .post(`/board/participants/leave`, {
      boardId: board.value.id,
      userId: userInfo.value.id,
    })
    .then(() => {
      alert("참여 취소 성공");
      isJoinable.value = true;
    })
    .catch(function (error) {
      console.log(error);
    });
  if (!isJoinable.value) {
    isJoinable.value != isJoinable.value;
  }
  readParticpantsCount();
};

const readParticpantsCount = async () => {
  await axios
    .get(`/board/participants/${router.currentRoute.value.params.id}`)
    .then((response) => {
      participants.value = response.data;
    })
    .catch(function (error) {
      console.log(error);
    });
};

const isJoined = async () => {
  await axios
    .get(`board/participants/${router.currentRoute.value.params.id}`, {
      userId: userInfo.value.id,
    })
    .then((response) => {
      isJoinable.value = response.data;
      console.log(response.data);
    })
    .catch(function (error) {
      console.log(error);
    });
};

onMounted(() => {
  fetchData();
  readParticpantsCount();
  isJoined();
});
</script>

<template>
  <div><RouterView /></div>
  <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem">
    <v-sheet>
      <v-card class="mx-auto pa-12 pb-8" min-width="80rem" rounded="lg">
        <v-card-item>
          <v-card-title>
            [{{ displayBoardType }}]
            {{ board.title }}
          </v-card-title>
          <v-card-subtitle>
            <v-icon icon="mdi-account"></v-icon>
            {{ board.author }} |
            <v-icon icon="mdi-clock-outline"></v-icon>
            {{ displayTime }} |
            <v-icon icon="mdi-eye-outline"></v-icon>
            {{ board.hits }} |
            <template v-if="board.boardType === 'recruitment'">
              <v-icon icon="mdi-account-multiple"></v-icon>
              {{ board.currentCount }} / {{ board.maxCount }}
            </template>
          </v-card-subtitle>
        </v-card-item>
        <v-divider></v-divider>
        <v-card-text>
          {{ board.contents }}
        </v-card-text>
      </v-card>

      <div class="button">
        <template v-if="board.boardType === 'recruitment'">
          <template v-if="board.author !== userInfo.id">
            <template v-if="isJoinable">
              <v-btn
                prepend-icon="mdi-account-plus"
                color="blue"
                variant="plain"
                @click="join"
                >참여</v-btn
              >
            </template>
            <template v-else>
              <v-btn
                prepend-icon="mdi-account-minus"
                color="blue"
                variant="plain"
                @click="leave"
                >취소</v-btn
              >
            </template>
          </template>
        </template>
        <template v-if="auth">
          <v-btn
            prepend-icon="mdi-update"
            color="green"
            variant="plain"
            @click="goToUpdate"
            >수정</v-btn
          >
          <v-btn
            prepend-icon="mdi-delete"
            style="margin-left: 1rem"
            color="red"
            variant="plain"
            @click="deleteBoard"
            >삭제</v-btn
          >
        </template>
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
