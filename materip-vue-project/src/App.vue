<script setup>
import NavigationComponent from './components/NavigationComponent.vue'
import FooterComponent from './components/FooterComponent.vue'
</script>

<template>
  <v-app>
    <NavigationComponent />

    <RouterView />

    <FooterComponent />
  </v-app>
</template>

<style scoped></style>
