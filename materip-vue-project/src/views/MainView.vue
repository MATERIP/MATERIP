<script setup>
import { ref, watchEffect, onMounted, onBeforeUnmount } from 'vue'
import VideoBackground from 'vue-responsive-video-background-player'
const showComponent = ref(false)

onMounted(() => {
  setTimeout(() => {
    showComponent.value = true
  }, 5500)
  window.addEventListener('scroll', handleScroll)
})

const handleScroll = () => {
  // Adjust the values based on your layout and scroll behavior
  const scrollThreshold = 500
  const maxOpacity = 1

  // Calculate the opacity based on the scroll position
  const opacity = 1 - window.scrollY / scrollThreshold
  scrollOpacity.value = Math.max(opacity, 0)

  // Update the opacity of the text
  const textElements = document.querySelectorAll('.fade-in')
  textElements.forEach((element) => {
    element.style.opacity = scrollOpacity.value
  })
}

// Cleanup the event listener when the component is destroyed
onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<template>
  <div>
    <!-- 어두운 배경 -->

    <!-- 실제 컨텐츠 -->

    <div class="videoContent">
      <video-background src="src/assets/sky.mp4" style="max-height: 1200px; height: 1200px">
      </video-background>
    </div>
    <div class="overlay">
      {{ showComponent }}
      <div style="text-align: center; margin-top: 350px">
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
    <v-app>
      <v-fade-transition>
        <div v-if="showComponent" class="fade-in slide-in">
          <div style="height: 100px; width: 100px; background: #000; z-index: 3"></div>
        </div>
      </v-fade-transition>
    </v-app>
    <div class="marquee">
      <div class="textSpan">
        <span>You spin me right round, baby. Like a record, baby.</span>
        <span>You spin me right round, baby. Like a record, baby.</span>
      </div>
    </div>
  </div>
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
  overflow: hidden;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 1500px;
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

.videoContent {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.marquee {
  height: 25px;
  margin-top: 1050px;
  width: 100%;
  background-color: rgb(255, 221, 0);
  overflow: hidden;
  position: relative;
}

.marquee div {
  display: block;
  width: 200%;
  height: 30px;

  position: absolute;
  overflow: hidden;

  animation: marquee 10s linear infinite;
}

.marquee span {
  float: left;
  width: 50%;
}

@keyframes marquee {
  0% {
    left: 0;
  }
  100% {
    left: -100%;
  }
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

.fade-in-enter-active,
.fade-in-leave-active {
  transition: opacity 10s; /* 나타나는데 1.5초 동안의 시간을 가짐 */
}
.fade-in-enter,
.fade-in-leave-to {
  opacity: 0;
}

.slide-in-enter-active,
.slide-in-leave-active {
  transition: transform 10s; /* 나타나는데 1.5초 동안의 시간을 가짐 */
}
.slide-in-enter,
.slide-in-leave-to {
  transform: translateY(100%); /* 시작 위치를 아래로 이동시킴 */
}
</style>
