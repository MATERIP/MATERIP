<script setup>
import { ref, onMounted, computed } from 'vue'
import { useMateStore } from '../../stores/board-store.js'
import { storeToRefs } from 'pinia'
const mateStore = useMateStore()
const { userBoardList } = storeToRefs(mateStore)
onMounted(() => {
  mateStore.myMate()
})

const title = ref('나의 리뷰')

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
  console.log(item.createdAt.substring(0, 4))
  console.log(item.createdAt.substring(5, 7))
  console.log(item.createdAt.substring(8, 10))
  let today = new Date()
  let year = today.getUTCFullYear()
  let month = today.getUTCMonth() + 1
  let date = today.getUTCDate()
  console.log(year)
  console.log(month)
  console.log(date)
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

const pageCount = computed(() => Math.ceil(userBoardList.value.length / itemsPerPage.value))
</script>

<template>
  <h1>{{ title }}</h1>
  <v-data-table
    v-model:page="page"
    :headers="headers"
    :items="userBoardList"
    :items-per-page="itemsPerPage"
    class="elevation-0"
    style="width: 80%; margin: 0 auto"
    hover
  >
    <!-- <template v-slot:top>
    <v-text-field
      :model-value="itemsPerPage"
      class="pa-2"
      hide-details
      label="Items per page"
      min="5"
      max="20"
      type="number"
      @update:model-value="itemsPerPage = parseInt($event, 10)"
      ></v-text-field>
  </template> -->

    <template v-slot:bottom>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
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
