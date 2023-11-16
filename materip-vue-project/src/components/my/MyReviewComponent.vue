<script setup>
import { ref, onMounted } from 'vue'
import { useBoardStore } from '../../stores/board-store.js'
import { storeToRefs } from 'pinia'
const boardStore = useBoardStore()
const boardList = ref()

onMounted(() => {
  boardStore.myBoard()
  boardList.value = userBoardList
})
</script>

<template>
  <div>
    <h1>{{ boardList }}</h1>
  </div>
</template>

<style scoped></style>
