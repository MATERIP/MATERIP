<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useTravelStore } from '@/stores/travel-store'

const travelStore = useTravelStore()

onMounted(() => {
  travelStore.searchTravelSpot()
})
const inputValue = ref('')

const search = function () {
  if (inputValue.value === '') {
    return
  }
  window.location.href = 'http://localhost:5173/' + 'attraction/' + inputValue.value
}

const sizes = ref([
  '단풍',
  '부산',
  '제주도',
  '경주',
  '단양',
  '서울',
  '축제',
  '강릉',
  '음악',
  '여수',
  '음악축제'
])
const rules = ref([(value) => !!value || '검색어를 입력하세요'])
const removeDarkEffect = function () {
  // 입력 필드를 클릭했을 때 어두워지는 효과 제거
  const inputField = document.querySelector('.v-text-field__slot input')
  if (inputField) {
    inputField.style.backgroundColor = 'transparent'
    inputField.style.border = '1px solid #ccc' // 필요에 따라 테두리 스타일도 조정할 수 있습니다.
  }
}
</script>

<template>
  <div class="searchCmp">
    <div style="text-align: center; padding: 20px 20px">
      <h1>어디로?🤷‍♂️</h1>
    </div>
    <v-form @submit.prevent="search" style="color: aliceblue">
      <v-text-field
        color="#ffc70f"
        placeholder="검색어를 입력하세요"
        :rules="rules"
        hide-details="auto"
        prepend-inner-icon="mdi-magnify"
        v-model="inputValue"
        base-color="white"
        bg-color="white"
        variant="underlined"
        clearable
        @click="removeDarkEffect"
      ></v-text-field>
    </v-form>
    <div class="space"></div>
    <v-chip-group v-model="inputValue" selected-class="text-orange-accent-4" mandatory>
      <v-chip v-for="size in sizes" :key="size" :value="size">
        {{ size }}
      </v-chip>
    </v-chip-group>
  </div>
</template>

<style scoped>
v-text-field {
  width: 10rem;
}
.searchCmp {
  padding: 1rem;
}
.space {
  height: 2rem;
}
</style>
