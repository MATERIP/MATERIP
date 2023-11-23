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
  travelSpot: "",
  travelDate: "",
});

const contentId = ref();
const attraction = ref({
  id: "",
  sidocode: "",
  guguncode: "",
  title: "",
});

const auth = ref(false);
// const participants = ref(0);
const paricipantsDetail = ref([]);
const paricipantsAllDetail = ref([]);
const isJoinable = ref(false);
const dialog = ref(false);
const auth_dialog = ref(false);

const fetchData = async () => {
  await axios
    .get(`/board/detail/${router.currentRoute.value.params.id}`)
    .then((response) => {
      board.value = response.data["board"];
      auth.value = response.data["auth"];
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
      // readParticipantsCount();
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
      // readParticipantsCount();
    })
    .catch(function (error) {
      console.log(error);
    });
};

// 참여자 수 가져오기
const readParticipantsCount = async () => {
  await axios
    .get(`/board/participants/${router.currentRoute.value.params.id}`)
    .then((response) => {
      console.log(response.data);
      board.value.currentCount = response.data;
    })
    .catch(function (error) {
      console.log(error);
    });
};

// 참여자 목록 가져오기
const readParticipantsDetail = async () => {
  await axios
    .get(`/board/participants/${router.currentRoute.value.params.id}/detail`)
    .then((response) => {
      // console.log(response.data);
      paricipantsDetail.value = response.data["userList"];
      // console.log(paricipantsDetail.value);
    })
    .catch(function (error) {
      console.log(error);
    });
};

// 참여 신청자 전체 목록 가져오기
const readParticipantsAllDetail = async () => {
  await axios
    .get(`/board/participants/${router.currentRoute.value.params.id}/all/detail`)
    .then((response) => {
      // console.log(response.data);
      paricipantsAllDetail.value = response.data["allUserList"];
      console.log(paricipantsAllDetail.value);
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

const accept = async (item) => {
  await axios
    .put(`/board/participants/accept`, {
      boardId: board.value.id,
      userId: item.userId,
    })
    .then(() => {
      alert("수락 성공");
      readParticipantsCount();
      readParticipantsDetail();
      readParticipantsAllDetail();
    })
    .catch(function (error) {
      console.log(error);
    });
};

const decline = async (item) => {
  await axios
    .put(`/board/participants/decline`, {
      boardId: board.value.id,
      userId: item.userId,
    })
    .then(() => {
      alert("거절 성공");
      readParticipantsCount();
      readParticipantsDetail();
      readParticipantsAllDetail();
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
      comment.value.contents = "";
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
      console.log(item);
      alert("댓글 수정 성공");
      getComments();
    })
    .catch(function (error) {
      console.log(error);
    });
};

const commentRules = [
  (v) => !!v || "댓글을 입력해주세요",
  (v) => (v && v.length <= 300) || "댓글은 300자 이내로 입력해주세요",
];

// 관광지 컨텐츠 아이디로 관광지 정보 가져오기
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

const search = ref("");

onMounted(() => {
  userStore.getUserInfo(userInfo.value.id);

  fetchData();

  getComments();
  readParticipantsCount();
  readParticipantsAllDetail();
  readParticipantsDetail();
  isJoined();
});

/*
watch(participants, (newCount) => {
  // board.value.currentCount = newCount;
  readParticipantsDetail();
});
*/

watch(board, (newBoard) => {
  contentId.value = board.value.travelSpot;

  getTravelSpot(contentId);
});
</script>

<template>
  <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem">
    <v-sheet>
      <v-card class="mx-auto pa-12 pb-8" min-width="80rem" rounded="lg" hover>
        <v-card-item>
          <v-card-title style="font-size: xx-large">
            [{{ displayBoardType }}]
            {{ board.title }}
          </v-card-title>
          <v-card-subtitle hide-details style="font-size: large">
            <v-icon icon="mdi-map-marker" color="red-darken-2"></v-icon>
            {{ attraction.title }}
            <v-icon icon="mdi-calendar" color="red-darken-2"></v-icon>
            {{ board.travelDate }}
          </v-card-subtitle>
          <v-card-subtitle hide-details>
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

              <v-btn color="primary" variant="plain">
                참여자 목록 보기
                <v-dialog v-model="dialog" activator="parent" width="500">
                  <v-card>
                    <v-card-text>
                      <v-list>
                        <v-list-item v-for="item in paricipantsDetail" :key="item.id">
                          <template v-slot:prepend>
                            <template v-if="item.userId === board.author">
                              <v-icon icon="mdi-account-star"></v-icon>
                            </template>
                            <template v-else>
                              <v-icon icon="mdi-account"></v-icon>
                            </template>
                          </template>
                          <v-list-item-title v-text="item.userId"></v-list-item-title>
                        </v-list-item>
                      </v-list>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn color="primary" block @click="dialog = false">닫기</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-btn>
              <!-- 모집글 작성자인 경우 참여 신청자 목록 보기 가능 -->
              <template v-if="board.author === userInfo.id">
                |
                <v-btn color="primary" variant="plain">
                  참여자 관리
                  <v-dialog v-model="auth_dialog" activator="parent" width="500">
                    <v-card>
                      <v-card-text>
                        <v-list>
                          <v-list-item
                            v-for="item in paricipantsAllDetail"
                            :key="item.id"
                          >
                            <template v-slot:prepend>
                              <template v-if="item.userId === board.author">
                                <v-icon icon="mdi-account-star"></v-icon>
                              </template>
                              <template v-else>
                                <v-icon icon="mdi-account"></v-icon>
                              </template>
                            </template>

                            <v-list-item-title v-text="item.userId"></v-list-item-title>
                            <template v-slot:append>
                              <!-- 참여 신청자의 상태에 따라 아이콘 변경 -->
                              <template v-if="item.userId !== userInfo.id">
                                <template v-if="item.status === 1">
                                  <v-icon icon="mdi-check"></v-icon>
                                </template>
                                <template v-if="item.status === 0">
                                  <v-btn @click="accept(item)"> 수락 </v-btn>
                                  <v-btn @click="decline(item)"> 거절 </v-btn>
                                </template>
                                <template v-if="item.status === -1">
                                  <v-icon icon="mdi-close"></v-icon>
                                </template>
                              </template>
                              <template v-else> ME! </template>
                            </template>
                          </v-list-item>
                        </v-list>
                      </v-card-text>
                      <v-card-actions>
                        <v-btn color="primary" block @click="auth_dialog = false"
                          >닫기</v-btn
                        >
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-btn>
              </template>
              <!-- [
              <template v-for="item in paricipantsDetail" :key="item.id">
                {{ item.userId }} |
              </template>
              ] -->
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
            <h3 class="comment-title">댓글</h3>

            <v-form @submit.prevent="writeComment">
              <v-textarea
                placeholder="댓글을 입력하세요."
                variant="underlined"
                v-model="comment.contents"
                rows="2"
                auto-grow
                max-rows="3"
                :rules="commentRules"
              >
              </v-textarea>
              <div class="button">
                <v-btn
                  type="submit"
                  color="primary"
                  style="display: flex; justify-content=flex-end"
                  variant="tonal"
                  >등록</v-btn
                >
              </div>
            </v-form>
          </v-col>
        </v-row>
      </v-container>

      <!-- 댓글 목록 -->
      <v-card class="pb-3 comments" border flat>
        <v-data-iterator :items="comments" :items-per-page="2" :search="search">
          <!-- header -->
          <template v-slot:header>
            <v-toolbar class="px-2">
              <v-text-field
                v-model="search"
                clearable
                density="comfortable"
                hide-details
                placeholder="댓글 검색..."
                prepend-inner-icon="mdi-magnify"
                style="max-width: 300px"
                variant="solo"
              ></v-text-field>
            </v-toolbar>
          </template>
          <!-- default -->
          <template v-slot:default="{ items }">
            <v-container class="pa-2" fluid>
              <v-row v-for="item in items" :key="item.sequence" rows="auto">
                <v-col dense>
                  <v-card class="pb-3" border flat>
                    <v-form @submit.prevent>
                      <v-card-title>
                        <v-icon icon="mdi-account-badge" size="small"></v-icon>
                        {{ item.raw.author }}
                      </v-card-title>
                      <v-card-subtitle>
                        {{ item.raw.createdAt }}
                      </v-card-subtitle>
                      <v-divider style="margin: 0.5rem"></v-divider>
                      <v-card-text style="padding-top: 0">
                        <v-textarea
                          v-model="item.raw.contents"
                          auto-grow
                          rows="1"
                          max-rows="3"
                          :disabled="item.raw.author !== userInfo.id"
                          variant="plain"
                          hide-details
                        ></v-textarea>
                      </v-card-text>
                      <template v-if="item.raw.author === userInfo.id">
                        <v-card-actions>
                          <v-btn
                            prepend-icon="mdi-update"
                            size="small"
                            color="secondary"
                            variant="outlined"
                            style="margin-right: 1rem"
                            @click="updateComment(item.raw)"
                            >수정</v-btn
                          >
                          <v-btn
                            prepend-icon="mdi-delete"
                            size="small"
                            color="warning"
                            variant="outlined"
                            @click="deleteComment(item.raw.sequence)"
                            >삭제</v-btn
                          >
                        </v-card-actions>
                      </template>
                    </v-form>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
          </template>
          <!-- 페이징 -->
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

              <div class="mx-2 text-caption">{{ page }} / {{ pageCount }}</div>

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
      </v-card>
    </v-sheet>
  </v-layout>
</template>

<style scoped>
.button {
  display: flex;
  justify-content: flex-end;
  margin-top: 0.5rem;
}

.comments {
  max-height: 250px;
  overflow-y: auto;
}

h3 {
  margin-bottom: 1rem;
}

.v-card-subtitle {
  margin: 0.5rem 0;
}

.v-textarea {
  font-family: "Nanum Gothic";
}

.v-text-field {
  font-family: "Nanum Gothic";
}

i {
  color: #f09404;
}
</style>
