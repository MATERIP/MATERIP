<template>
  <v-app-bar app color="transparent" flat class="px-10">
    <v-toolbar-title min-width="10rem">
      <RouterLink to="/">
        <v-img src="src/assets/orglogo.png" alt="Logo" max-height="32"></v-img>
      </RouterLink>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <RouterLink to="/review">
      <v-btn text @click="goToReview"><v-icon icon="mdi-note-edit"></v-icon>여행지 리뷰</v-btn>
    </RouterLink>
    <RouterLink to="/mate">
      <v-btn text @click="goToMate"
        ><v-icon icon="mdi-account-multiple-plus"></v-icon>여행 메이트</v-btn
      >
    </RouterLink>
    <v-autocomplete
      :items="travelStore.searchResult"
      auto-select-first
      class="flex-full-width align-self-center transparent-background px-7"
      density="comfortable"
      item-props
      menu-icon=""
      placeholder="search Travel"
      prepend-inner-icon="mdi-magnify"
      rounded
      theme="light"
      variant="solo"
      hide-details
      v-model="inputValue"
      @keydown.enter="searchFunction"
    >
    </v-autocomplete>

    <template v-for="menu in menuList" :key="menu.name">
      <template v-if="menu.show">
        <template v-if="menu.routeName === 'logout'">
          <v-list-item>
            <RouterLink to="/" @click.prevent="logout" class="menu mr-10">
              <v-btn> <v-icon icon="mdi-logout"></v-icon>{{ menu.name }} </v-btn>
            </RouterLink>
          </v-list-item>
        </template>
        <template v-if="menu.routeName === 'login'">
          <v-list-item>
            <RouterLink to="/login">
              <v-btn text @click="goToLogin" class="mr-10"
                ><v-icon icon="mdi-login"></v-icon>로그인</v-btn
              >
            </RouterLink>
          </v-list-item>
        </template>
        <template v-if="menu.routeName === 'mypage'">
          <v-list-item>
            <RouterLink :to="{ name: menu.routeName }" class="menu">
              <v-btn> <v-icon icon="mdi-account"></v-icon>{{ menu.name }} </v-btn>
            </RouterLink>
          </v-list-item>
        </template>
      </template>
    </template>
  </v-app-bar>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user-store'
import { useTravelStore } from '@/stores/travel-store'

const travelStore = useTravelStore()
const userStore = useUserStore()
const { logout } = useUserStore()
const { menuList } = storeToRefs(userStore)
onMounted(() => {
  travelStore.searchTravelSpot()
})

const inputValue = ref('')
const searchFunction = function () {
  console.log(inputValue.value)
}
</script>
