<script>
export default {
  data() {
    return {
      colors: ['#edfaf7', '#b7efe3', '#9ee4d9', '#85ebbb', '#8cdbec'],
      slides: ['First', 'Second', 'Third', 'Fourth', 'Fifth']
    }
  }
}
</script>

<template>
  <v-carousel cycle height="800" hide-delimiters show-arrows="hover">
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
