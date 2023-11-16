<script setup>
import { ref } from 'vue'
const colors = ref(['#edfaf7', '#b7efe3', '#9ee4d9', '#85ebbb', '#8cdbec'])
const slides = ref(['First', 'Second', 'Third', 'Fourth', 'Fifth'])
import VideoBackground from 'vue-responsive-video-background-player'
</script>

<template>
  <v-carousel cycle height="800" hide-delimiters show-arrows="hover" interval="5000">
    <v-carousel-item>
      <v-sheet>
        <video-background src="src/assets/sky.mp4" style="max-height: 800px; height: 200vh">
        </video-background>
      </v-sheet>
    </v-carousel-item>
    <v-carousel-item v-for="(slide, i) in slides" :key="i">
      <v-sheet :color="colors[i]" height="100%">
        <div class="d-flex fill-height justify-center align-center">
          <div class="text-h2">{{ slide }} Slide</div>
        </div>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</template>

<style></style>
