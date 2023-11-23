<script setup>
import { ref, watchEffect, onMounted, onBeforeUnmount } from 'vue'
import VideoBackground from 'vue-responsive-video-background-player'
const showComponent = ref(false)
import CarouselComponent from '../components/CarouselComponent.vue'
onMounted(() => {
  setTimeout(() => {
    showComponent.value = true
    scrollToPosition(280)
  }, 5700)
})
const scrollToPosition = (position) => {
  window.scrollTo({
    top: position,
    behavior: 'smooth'
  })
}
const position = ref(0)
</script>

<template>
  <div class="backgrd">
    <!-- 어두운 배경 -->

    <!-- 실제 컨텐츠 -->

    <div class="videoContent">
      <video-background src="src/assets/sky.mp4" style="max-height: 1200px; height: 1200px">
      </video-background>
    </div>
    <div class="overlay main-text">
      <div style="text-align: center">
        <div class="text-wrap">
          <svg width="525" height="160" viewBox="0 0 525 160">
            <text x="0" y="50%">L</text>
            <text x="45" y="50%">E</text>
            <text x="90" y="50%">T</text>
            <text x="130" y="50%">'</text>
            <text x="150" y="50%">S</text>
            <text x="220" y="50%">G</text>
            <text x="265" y="50%">O</text>
            <text x="335" y="50%">T</text>
            <text x="380" y="50%">R</text>
            <text x="425" y="50%">I</text>
            <text x="450" y="50%">P</text>
          </svg>
        </div>
      </div>
    </div>

    <Transition>
      <div v-if="showComponent" class="carousel">
        <div style="height: 80%; width: 50%; z-index: 3">
          <CarouselComponent />
        </div>
      </div>
    </Transition>
  </div>
  <div style="height: 500px"></div>
</template>
<style>
@import url('https://fonts.googleapis.com/css2?family=Staatliches&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@100&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  margin: 0;
}

.overlay {
  position: relative;
  top: 350px;
  left: 0;
  width: 100vw;
  height: 100px;
  z-index: 300;
  transition: opacity 5s ease-in-out;
  padding: 0;
}
.textSpan > span {
  font-size: 20px;
  font-weight: bold;
  color: white;
}
.content {
  position: relative;
  z-index: 1;
  color: #fff;
}
.carousel {
  position: absolute;
  top: 500px;
  left: 0;
  width: 100%;
  height: 300px;
  z-index: 30;
  display: flex;
  justify-content: center;
}
.videoContent {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

body {
  width: 100%;
  height: 100%;
  background: #16345a;
  padding: 0;
  margin: 0;
}

.text-box {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

svg text {
  font-family: 'Staatliches', cursive;
  font-size: 6em;
  fill: transparent;
  stroke-dasharray: 326px;
  animation: stroke 1s linear;
  animation-fill-mode: forwards;
}
svg text:nth-child(1) {
  animation-delay: 0s;
}
svg text:nth-child(2) {
  animation-delay: 0.5s;
}
svg text:nth-child(3) {
  animation-delay: 1s;
}
svg text:nth-child(4) {
  animation-delay: 1.5s;
}
svg text:nth-child(5) {
  animation-delay: 2s;
}
svg text:nth-child(6) {
  animation-delay: 2.5s;
}
svg text:nth-child(7) {
  animation-delay: 3s;
}
svg text:nth-child(8) {
  animation-delay: 3.5s;
}
svg text:nth-child(9) {
  animation-delay: 4s;
}
svg text:nth-child(10) {
  animation-delay: 4.5s;
}
svg text:nth-child(11) {
  animation-delay: 5s;
}
svg text:nth-child(12) {
  animation-delay: 5.5s;
}
@keyframes stroke {
  0% {
    stroke: #f1c164;
    stroke-width: 3px;
    stroke-dashoffset: 326px;
  }
  70% {
    fill: transparent;
  }
  98% {
    stroke: #f1c164;
    stroke-width: 3px;
  }
  100% {
    fill: #f1c164;
    stroke-dashoffset: 0px;
  }
}

.v-enter-active {
  transition: opacity 0.5s ease;
}

.v-enter-from {
  opacity: 0;
}
</style>
