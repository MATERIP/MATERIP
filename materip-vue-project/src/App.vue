<script setup>
import NavigationComponent from "./components/NavigationComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";
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
  font-family: "KIMM_Bold";
  font-weight: 300;
}

@font-face {
  font-family: "KIMM_Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2212@1.0/KIMM_Bold.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}

@font-face {
  font-family: "Nanum Gothic";
  src: url(//fonts.googleapis.com/earlyaccess/nanumgothic.css);
}

@font-face {
  font-family: "NPSfontBold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2310@1.0/NPSfontBold.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}
</style>
