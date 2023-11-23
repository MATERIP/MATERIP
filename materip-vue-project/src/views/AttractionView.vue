<script setup>
import { onMounted, ref, computed } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
import router from '../router'
import SearchMapComponent from '../components/SearchMapComponent.vue'
const route = useRoute()
const attractionName = route.params.attractionName
const attractionMapCoord = ref([])
const attractionMapTitle = ref([])
const attractionMapContentId = ref([])
const attractionList = ref([])
onMounted(() => {
  title.value = "#'" + attractionName + "' 검색 결과"
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
    console.log(Object.keys(attractionList.value[0]))
    response.data.forEach((attr) => {
      attractionMapCoord.value.push([attr.mapX, attr.mapY])
      attractionMapTitle.value.push(attr.title)
      attractionMapContentId.value.push(attr.contentId)
    })
  })

  // console.log(attractionMapInfo.value)
})

const title = ref('')

const itemsPerPage = ref(10)
const page = ref(1)
const headers = [
  {
    title: '🖼️🖼️🖼️',
    key: 'img1',
    align: 'start',
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
  <div
    style="
      margin-left: 15%;
      margin-right: 15%;
      display: flex;
      align-items: center;
      justify-content: space-between;
    "
  >
    <h1>{{ title }}</h1>
    <v-dialog width="900">
      <template v-slot:activator="{ props }">
        <v-btn v-bind="props" text="지도로 찾기"> </v-btn>
      </template>

      <template v-slot:default="{ isActive }">
        <v-card style="display: flex; justify-content: center">
          <h1 style="margin: 40px">{{ title }}</h1>
          <div class="map"><SearchMapComponent></SearchMapComponent></div>
          <div class="modalcard">
            <v-btn
              id="closebtn"
              text="닫기"
              color="rgb(255, 221, 0)"
              style="width: 100px; margin-bottom: 40px; align-self: center"
              @click="isActive.value = false"
            ></v-btn>
          </div>
        </v-card>
      </template>
    </v-dialog>
  </div>

  <div style="height: 1rem"></div>
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

<style scoped>
.map {
  padding: 0px;
  margin: 0px 0px 30px 0px;
}
.map > div {
  display: flex;
  justify-content: center;
}
.modalcard {
  display: flex;
  justify-content: center;
}
</style>
