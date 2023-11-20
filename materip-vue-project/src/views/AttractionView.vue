<script setup>
import { onMounted, ref, computed } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
import router from '../router'
const route = useRoute()
const attractionName = route.params.attractionName

const attractionList = ref([])
onMounted(() => {
  axios({
    baseURL: '',
    method: 'get',
    url: 'http://localhost:8080/attraction/searchinfo/' + encodeURIComponent(attractionName), // URL에 한글이 포함될 경우 인코딩
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    }
  }).then(function (response) {
    console.log(response.data)
    attractionList.value = response.data
  })
})

const title = ref({
  review: '여행지 리뷰',
  recruitment: '여행 메이트 모집'
})

const itemsPerPage = ref(10)
const page = ref(1)
const headers = [
  {
    title: '',
    key: 'img1',
    align: 'center',
    sortable: false,
    template: 'imageColumn' // 이미지를 표시할 컬럼에 대한 템플릿 지정
  },
  {
    align: 'center',
    key: 'title',
    sortable: false,
    title: '장소'
  },

  { title: '주소', key: 'addr1', align: 'center', sortable: false }
]

const pageCount = computed(() => Math.ceil(attractionList.value.length / itemsPerPage.value))

const imageProcess = function (img1, img2) {
  if (img1 === null || img1 === '') {
    if (img2 === null || img2 === '') {
      return '../../src/assets/empty.png'
    } else {
      return img2
    }
  } else {
    return img1
  }
}

const goToDetail = function (contentId) {
  router.push('../attraction/detail/' + contentId)
}
</script>

<template>
  <div style="height: 8rem"></div>
  <v-data-table
    v-model:page="page"
    :headers="headers"
    :items="attractionList"
    :items-per-page="itemsPerPage"
    class="elevation-0"
    style="width: 70%; margin: 0 auto"
    hover
  >
    <template v-slot:item.img1="{ item }">
      <v-img :src="imageProcess(item.firstImage, item.firstImage2)" width="50" height="50"></v-img>
    </template>
    <template v-slot:item.title="{ item }">
      <v-btn text @click="goToDetail(item.contentId)" variant="flat" :class="[item.boardType]">{{
        item.title
      }}</v-btn>
    </template>

    <template v-slot:bottom>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
    </template>
  </v-data-table>
</template>

<style scoped></style>
