<template>
  <v-app-bar app color="transparent" flat class="px-10 navigation-bar">
    <v-toolbar-title min-width="10rem">
      <RouterLink to="/">
        <v-img src="../src/assets/orglogo.png" alt="Logo" max-height="32"></v-img>
      </RouterLink>
    </v-toolbar-title>
    <v-spacer></v-spacer>
    <RouterLink to="/board/review" replace>
      <v-btn text><v-icon icon="mdi-note-edit"></v-icon>여행지 리뷰</v-btn>
    </RouterLink>
    <RouterLink to="/board/recruitment" replace>
      <v-btn text><v-icon icon="mdi-account-multiple-plus"></v-icon>여행 메이트</v-btn>
    </RouterLink>
    <v-dialog width="500">
      <template v-slot:activator="{ props }">
        <v-btn v-bind="props"> <v-icon icon="mdi-briefcase-search"></v-icon> 여행지 검색 </v-btn>
      </template>

      <template v-slot:default="{ isActive }">
        <v-card>
          <SearchComponent @search="searchFunction" />
          <v-spacer></v-spacer>

          <v-btn text="Close" @click="isActive.value = false"></v-btn>
        </v-card>
      </template>
    </v-dialog>
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
            <router-link :to="{ name: menu.routeName, params: { userId: userId } }" class="menu">
              <v-btn>
                <v-icon icon="mdi-account"></v-icon>
                {{ menu.name }}
              </v-btn>
            </router-link>
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
import SearchComponent from './SearchComponent.vue'
const travelStore = useTravelStore()
const userStore = useUserStore()
const { logout } = useUserStore()
const { menuList } = storeToRefs(userStore)
const { userId } = storeToRefs(userStore)
onMounted(() => {
  travelStore.searchTravelSpot()
})

const inputValue = ref('')

const isSearchModalActive = ref(false)

const searchFunction = function () {
  console.log(inputValue.value)

  // Close the modal after search
  isSearchModalActive.value = false
}
</script>

<style scoped>
.v-btn {
  color: black;
}
.navigation-bar {
  z-index: 1000;
}
</style>
