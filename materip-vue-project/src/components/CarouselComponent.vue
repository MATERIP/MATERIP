<script setup>
import { ref } from 'vue'
const colors = ref([
  '../src/assets/carousel/carousel_2.png',
  '../src/assets/carousel/carousel_1.png',
  '../src/assets/carousel/carousel_3.png'
])
</script>

<template>
  <div style="height:"></div>
  <v-carousel cycle height="800" interval="5000" id="hello" :show-arrows="false">
    <v-carousel-item v-for="i in 3" :key="i">
      <v-sheet
        style="padding-left: 10rem; padding-right: 10rem; background-color: rgba(255, 255, 255, 0)"
      >
        <div class="d-flex align-center">
          <v-img :src="colors[i - 1]" style="z-index: 3" />
        </div>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</template>

<style></style>
