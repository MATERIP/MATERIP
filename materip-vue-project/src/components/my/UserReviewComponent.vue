<script setup>
import { ref, onMounted, computed } from 'vue'
import { useReviewStore } from '../../stores/board-store.js'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../stores/user-store'
const router = useRouter()

const userStore = useUserStore()
const reviewStore = useReviewStore()

const { userReviewList } = storeToRefs(reviewStore)
const { navTitle } = storeToRefs(userStore)

const itemsPerPage = ref(10)
const page = ref(1)
const headers = [
  {
    align: 'center',
    key: 'title',
    sortable: false,
    title: '제목'
  },

  { title: '작성자', key: 'author', align: 'center', sortable: false },
  {
    title: '작성일',
    key: 'createdAt',
    align: 'center',
    value: (item) => formatDate(item),
    sortable: false
  },
  { title: '조회', key: 'hits', align: 'center', sortable: false }
]

const formatDate = function (item) {
  console.log(item)
  if (item == null || item == '') {
    return ' '
  }

  let today = new Date()
  let year = today.getUTCFullYear()
  let month = today.getUTCMonth() + 1
  let date = today.getUTCDate()

  if (
    year == item.createdAt.substring(0, 4) &&
    month == item.createdAt.substring(5, 7) &&
    date == item.createdAt.substring(8, 10)
  ) {
    item.createdAt = item.createdAt.substring(11, 19)
  } else {
    item.createdAt = item.createdAt.substring(0, 10).replaceAll('-', '.')
  }
  return item.createdAt
}
async function goToDetail(item) {
  console.log(item)
  // 상세 페이지 이동 전에 조회수를 1 증가시킨다.
  // await updateBoardHits(item);

  router.push({ name: 'boardDetail', params: { id: item.id } })
}
const pageCount = computed(() => Math.ceil(userReviewList.value.length / itemsPerPage.value))
</script>

<template>
  <h1>{{ navTitle['review'] }}</h1>
  <v-data-table
    v-model:page="page"
    :headers="headers"
    :items="userReviewList"
    :items-per-page="itemsPerPage"
    class="elevation-0"
    style="width: 80%; margin: 0 auto"
    hover
  >
    <template v-slot:bottom>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
    </template>
    <template v-slot:item.title="{ item }">
      <v-btn text @click="goToDetail(item)" variant="flat" :class="[item.boardType]">{{
        item.title
      }}</v-btn>
    </template>
  </v-data-table>
</template>

<style scoped>
h1 {
  justify-content: center;
  padding-left: 10%;
  margin-top: 0;
  margin-bottom: 1rem;
}
</style>
