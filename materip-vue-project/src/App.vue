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

<style>

.v-application {
  font-family: 'Ghanachocolate';
  font-weight: normal;
}

@font-face {
  font-family: 'Ghanachocolate';
  src : url('./assets/Ghanachocolate.ttf') format('truetype');
  font-weight: 300;
}

</style>
