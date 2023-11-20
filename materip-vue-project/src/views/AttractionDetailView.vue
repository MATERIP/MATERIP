<script setup>
import { onMounted, ref, computed } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
import router from '../router'
import { VCol } from 'vuetify/lib/components/index.mjs'
const route = useRoute()
const contentId = route.params.contentId

const attractionInfo = ref({})
const attractionDescription = ref({})

onMounted(() => {
  axios({
    baseURL: '',
    method: 'get',
    url: 'http://localhost:8080/attraction/info/' + encodeURIComponent(contentId), // URL에 한글이 포함될 경우 인코딩
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    }
  }).then(function (response) {
    console.log(response.data['description'])

    attractionInfo.value = response.data['info']
    attractionDescription.value = response.data['description']
  })
})

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
</script>

<template>
  <div>
    <v-layout row wrap style="display: flex; justify-content: center; margin-top: 10rem">
      <v-sheet>
        <v-card class="mx-auto pa-12 pb-8" min-width="80rem" rounded="lg">
          <v-card-item>
            <v-card-title>
              {{ attractionInfo.title }}
            </v-card-title>
            <v-card-subtitle>
              <v-icon icon="mdi-account"></v-icon>
              {{ attractionInfo.addr1 + ' ' + attractionInfo.addr2 }}
            </v-card-subtitle>
          </v-card-item>
          <v-divider></v-divider>
          <v-card-text>
            <v-layout>
              <v-row>
                <v-col cols="3">
                  <v-img
                    :src="imageProcess(attractionInfo.firstImage, attractionInfo.firstImage2)"
                    alt=""
                    max-width="30rem"
                  />
                </v-col>
                <v-col>
                  {{ attractionDescription.overview }}
                </v-col>
              </v-row>
            </v-layout>
          </v-card-text>
        </v-card>
      </v-sheet>
    </v-layout>
  </div>
</template>

<style scoped></style>
