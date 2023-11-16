<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();
const boardList = ref([]);
const instance = axios.create({
  baseURL: "http://localhost:8080/",
});

const title = ref("여행 메이트 모집");
const writeBtn = ref("글쓰기");

const itemsPerPage = ref(10);
const page = ref(1);
const headers = [
  {
    align: "center",
    key: "title",
    sortable: false,
    title: "제목",
  },

  { title: "작성자", key: "author", align: "center", sortable: false },
  {
    title: "작성일",
    key: "createdAt",
    align: "center",
    value: (item) => formatDate(item),
    sortable: false,
  },
  { title: "조회", key: "hits", align: "center", sortable: false },
];

const formatDate = function (item) {
  // console.log(item.createdAt.substring(0, 4))
  // console.log(item.createdAt.substring(5, 7))
  // console.log(item.createdAt.substring(8, 10))
  let today = new Date();
  let year = today.getUTCFullYear();
  let month = today.getUTCMonth() + 1;
  let date = today.getUTCDate();
  // console.log(year);
  // console.log(month);
  // console.log(date);
  if (
    year == item.createdAt.substring(0, 4) &&
    month == item.createdAt.substring(5, 7) &&
    date == item.createdAt.substring(8, 10)
  ) {
    item.createdAt = item.createdAt.substring(11, 19);
  } else {
    item.createdAt = item.createdAt.substring(0, 10).replaceAll("-", ".");
  }
  return item.createdAt;
};

const pageCount = computed(() => Math.ceil(boardList.value.length / itemsPerPage.value));

function fetchData() {
  instance
    .get("/board/getList", boardList.value)
    .then((response) => {
      boardList.value = response.data;
      console.log(response);
    })
    .catch(function (error) {
      console.log(error);
    });
}

onMounted(() => {
  fetchData();
});

function updateBoardHits(item) {
  instance
  .get(`/board/updateHits/${item.id}`)
  .then((response) => {
    console.log(response);
  })
  .catch(function (error) {
    console.log(error);
  });
}

function goToDetail(item) {
  console.log(item)
  updateBoardHits(item);
  router.push({ name: "boardDetail", params: { id: item.id } });
}

const goWrite = () => {
  router.push({ name: "write" });
};

</script>

<template>
  
  <div style="height: 8rem"></div>
  <div>
    <router-view></router-view>
  </div>
  
  <div
    style="
      display: flex;
      justify-content: space-between;
      margin-right: 10%;
      margin-left: 10%;
    "
  >
    <h1>{{ title }}</h1>
    <v-btn
      style="align-self: center"
      variant="elevated"
      color="primary"
      width="fit-content"
      @click="goWrite"
      >{{ writeBtn }}</v-btn
    >
  </div>
  <v-data-table
    v-model:page="page"
    :headers="headers"
    :items="boardList"
    :items-per-page="itemsPerPage"
    class="elevation-0"
    style="width: 80%; margin: 0 auto"
    hover
  >

  <template v-slot:item.title="{ item }">
    <v-btn text @click="goToDetail(item)" variant="flat">{{ item.title }}</v-btn>
    </template>

    <template v-slot:bottom>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
    </template>
  </v-data-table>
</template>

<style scoped>
h1 {
  margin-top: 0;
  align-self: center;
}
</style>
