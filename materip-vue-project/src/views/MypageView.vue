<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../stores/user-store'

const userStore = useUserStore()
onMounted(() => {
  userStore.getUserInfo()
})
</script>

<template>
  <div></div>
</template>

<style scoped></style>
