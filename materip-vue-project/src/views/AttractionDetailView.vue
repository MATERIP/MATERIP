<script setup>
import { onMounted, ref, watch } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
import MapComponent from '../components/MapComponent.vue'
import { VCol } from 'vuetify/lib/components/index.mjs'
const route = useRoute()
const contentId = route.params.contentId

const attractionInfo = ref({ latitude: 37.52251412, longitude: 128.2919115 })
const attractionDescription = ref({})
const imgurl = ref('')
onMounted(() => {
  axios({
    baseURL: '',
    method: 'get',
    url: 'http://localhost:8080/attraction/information/' + encodeURIComponent(contentId), // URL에 한글이 포함될 경우 인코딩
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    }
  }).then(function (response) {
    console.log(response.data['description'])

    attractionInfo.value = response.data['info']
    attractionDescription.value = response.data['description']
    console.log(attractionInfo.value['firstImage'])
    console.log('~~~~~~~~~~~~~~~')
    console.log(attractionInfo.value)
    imgurl.value = imageProcess(
      attractionInfo.value['firstImage'],
      attractionInfo.value['firstImage2']
    )
  })
})

const imageProcess = function (img1, img2) {
  console.log(img1 + ' ' + img2)
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

watch(
  () => attractionInfo.value,
  () => {
    console.log('attractionInfo changed')
    console.log(attractionInfo.value)
    imgurl.value = imageProcess(
      attractionInfo.value['firstImage'],
      attractionInfo.value['firstImage2']
    )
  }
)
</script>

<template>
  <div>
    <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem">
      <v-sheet>
        <v-card class="mx-auto" min-width="80rem" rounded="lg">
          <v-card-item>
            <div style="text-align: center">
              <h1>{{ attractionInfo.title }}</h1>
              <h2>{{ (attractionInfo.firstimage, attractionInfo.firstimage2) }}</h2>
            </div>

            <v-card-subtitle style="text-align: right">
              <v-icon icon="mdi-map"></v-icon>
              {{ attractionInfo.addr1 + ' ' + attractionInfo.addr2 }}
            </v-card-subtitle>
          </v-card-item>
          <v-divider></v-divider>

          <v-card-text>
            <v-layout>
              <v-row style="display: flex; justify-content: center">
                <div>
                  <v-hover v-slot="{ isHovering, props }">
                    <v-card
                      class="mx-auto"
                      color="grey-lighten-4"
                      min-width="50rem"
                      max-width="80rem"
                      v-bind="props"
                    >
                      <v-img :aspect-ratio="16 / 9" cover v-bind:src="imgurl">
                        <v-expand-transition>
                          <div
                            v-if="isHovering"
                            class="d-flex transition-fast-in-fast-out bg-grey-darken-2 v-card--reveal text pa-10"
                            style="height: 100%; opacity: 80%"
                          >
                            {{ attractionDescription.overview }}
                          </div>
                        </v-expand-transition>
                      </v-img>

                      <v-card-text class="pt-6">
                        <v-row
                          style="
                            display: flex;
                            align-items: center;
                            justify-content: space-between;
                            margin: 10px;
                          "
                        >
                          <h3 class="text-orange mb-2">
                            {{ attractionInfo.title }}
                          </h3>
                          <div style="display: flex; justify-content: space-between">
                            <v-btn style="margin: 3px; background-color: #ff9800; color: white"
                              ><v-icon icon="mdi-bag-personal"></v-icon>여행 떠나기</v-btn
                            >
                            <v-btn style="margin: 3px; background-color: #ff9800; color: white"
                              ><v-icon icon="mdi-message-draw"></v-icon>후기 남기기</v-btn
                            >
                          </div>
                        </v-row>
                        <MapComponent :mapData="attractionInfo" />
                      </v-card-text>
                    </v-card>
                  </v-hover>
                </div>
              </v-row>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-sheet>
    </v-layout>
    <div style="height: 8rem"></div>
  </div>
</template>

<style scoped>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.5;
  position: absolute;
  width: 100%;
}
</style>
