<script setup>

import { ref, computed, onMounted, watch, inject } from "vue";

import { useRouter } from "vue-router";
import { useUserStore } from "../stores/user-store";
import { storeToRefs } from "pinia";

const axios = inject("axios");

const userStore = useUserStore();
const { userInfo, userId } = storeToRefs(userStore);

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
const isJoinable = ref(false);


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

      readParticpantsCount();

    })
    .catch(function (error) {
      console.log(error);
    });

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

      readParticpantsCount();

    })
    .catch(function (error) {
      console.log(error);
    });

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
    .get(`board/participants/${router.currentRoute.value.params.id}/isjoinable`, {
      params: {
        userid: userInfo.value.id,
      },
    })
    .then((response) => {
      isJoinable.value = response.data;
      console.log(`joinable` + response.data);
    })
    .catch(function (error) {
      console.log(error);
    });
};

// 댓글
const comment = ref({
  sequence: "",
  articleId: router.currentRoute.value.params.id,
  author: userInfo.value.id,
  contents: "",
  createdAt: "",
});

const comments = ref([
  {
    sequence: "",
    author: "",
    contents: "",
    createdAt: "",
  },
]);

const writeComment = async () => {
  await axios
    .post(`/board/comment/write`, comment.value)
    .then(() => {
      alert("댓글 등록 성공");
      getComments();
    })
    .catch(function (error) {
      console.log(error);
    });
};

const getComments = async () => {
  await axios
    .get(`/board/comment/getList/${router.currentRoute.value.params.id}`)
    .then((response) => {
      comments.value = response.data;
    })
    .catch(function (error) {
      console.log(error);
    });
};

const deleteComment = async (item) => {
  await axios
    .delete(`/board/comment/delete`, {
      params: {
        sequence: item,
      },
    })
    .then(() => {
      alert("댓글 삭제 성공");
      getComments();
    })
    .catch(function (error) {
      console.log(error);
    });
};

const updateComment = async (item) => {
  await axios
    .put(`/board/comment/modify`, item)
    .then(() => {
      alert("댓글 수정 성공");
      getComments();

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


//



onMounted(() => {
  userStore.getUserInfo(userInfo.value.id);

  fetchData();

  getComments();
  readParticpantsCount();
  isJoined();
});

watch(participants, (newCount) => {
  board.value.currentCount = newCount;

  readParticpantsCount();
  isJoined();

});
</script>

<template>
  <div><RouterView /></div>
  <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem">
    <v-sheet>
      <v-card class="mx-auto pa-12 pb-8" min-width="80rem" rounded="lg" hover>
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

            <v-icon icon="mdi-comment-text-multiple-outline"></v-icon>
            {{ comments.length }} |

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

            <v-btn
              prepend-icon="mdi-account-plus"
              color="blue"
              variant="plain"
              @click="join"
              :disabled="!isJoinable"
              >참여</v-btn
            >

            <v-btn
              prepend-icon="mdi-account-minus"
              color="blue"
              variant="plain"
              @click="leave"
              :disabled="isJoinable"
              >취소</v-btn
            >

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

      <v-container>
        <v-row>
          <v-col cols="12">
            <h3>댓글</h3>
            <v-form @submit.prevent>
              <v-textarea
                label="댓글을 남겨보세요"
                outlined
                v-model="comment.contents"
                rows="2"
                clearable
                auto-grow
                max-rows="3"
              >
              </v-textarea>
              <div class="button">
                <v-btn
                  color="green"
                  style="display: flex; justify-content=flex-end"
                  variant="outlined"
                  @click="writeComment"
                  >등록</v-btn
                >
              </div>
            </v-form>
          </v-col>
        </v-row>

        <!-- 댓글 목록 -->
        <v-data-iterator :items="comments">
          <v-row v-for="comment in comments" :key="comment.sequence">
            <v-col>
              <v-card variant="elevated" hover>
                <v-form @submit.prevent>
                  <v-card-title>
                    <v-icon icon="mdi-account-badge" size="small"></v-icon>
                    {{ comment.author }}
                  </v-card-title>
                  <v-card-subtitle>
                    {{ comment.createdAt }}
                  </v-card-subtitle>
                  <v-card-text>
                    <v-textarea
                      v-model="comment.contents"
                      auto-grow
                      rows="1"
                      max-rows="3"
                      :disabled="comment.author !== userInfo.id"
                      variant="plain"
                    ></v-textarea>
                  </v-card-text>
                  <template v-if="comment.author === userInfo.id">
                    <v-card-actions>
                      <v-btn
                        prepend-icon="mdi-update"
                        size="small"
                        color="secondary"
                        variant="outlined"
                        style="margin-right: 1rem"
                        @click="updateComment(comment)"
                        >수정</v-btn
                      >
                      <v-btn
                        prepend-icon="mdi-delete"
                        size="small"
                        color="warning"
                        variant="outlined"
                        @click="deleteComment(comment.sequence)"
                        >삭제</v-btn
                      >
                    </v-card-actions>
                  </template>
                </v-form>
              </v-card>
            </v-col>
          </v-row>
          <template v-slot:footer="{ page, pageCount, prevPage, nextPage }">
            <div class="d-flex align-center justify-center pa-4">
              <v-btn
                :disabled="page === 1"
                icon="mdi-arrow-left"
                density="comfortable"
                variant="tonal"
                rounded
                @click="prevPage"
              ></v-btn>

              <div class="mx-2 text-caption">Page {{ page }} of {{ pageCount }}</div>

              <v-btn
                :disabled="page >= pageCount"
                icon="mdi-arrow-right"
                density="comfortable"
                variant="tonal"
                rounded
                @click="nextPage"
              ></v-btn>
            </div>
          </template>
        </v-data-iterator>
      </v-container>

    </v-sheet>
  </v-layout>
</template>

<style scoped>
.button {
  display: flex;
  justify-content: flex-end;
  margin-top: 0.5rem;
}
</style>
