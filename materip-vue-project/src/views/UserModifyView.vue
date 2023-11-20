<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import { useUserStore } from '../stores/user-store'
const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)

const router = useRouter()

const message = ref('')
const color = ref('')
const icon = ref('')
const hasInput = ref(false)

onMounted(() => {
  hasInput.value = false
})

const instance = axios.create({
  baseURL: 'http://localhost:8080/'
})

function idCheck() {
  hasInput.value = true
  // console.log(userInfo.value.id)
  instance
    .post('/user', { id: userInfo.id })
    .then(function (response) {
      //console.log(response.data);
      if (response.data == 1) {
        message.value = '중복된 아이디 존재'
        color.value = 'red'
        icon.value = 'mdi-alert-circle'
      } else if (userInfo.id === '') {
        message.value = '아이디를 입력하세요!'
        color.value = 'red'
        icon.value = 'mdi-alert-circle'
      } else {
        message.value = '사용 가능한 아이디'
        color.value = 'green'
        icon.value = 'mdi-check-circle'
      }
    })
    .catch(function (error) {
      console.log(error)
    })
}
</script>

<template>
  <v-layout row wrap>
    <v-sheet width="30rem">
      <v-card elevation="3" class="mx-auto pa-12 pb-8">
        <v-card-title>
          <v-layout align-center justify-center-between>
            <p style="font-weight: bold">회원정보 수정</p>
          </v-layout>
        </v-card-title>
        <v-form class="mt-5" @submit.prevent="userStore.modify(userInfo)">
          <v-text-field
            density="comfortable"
            label="아이디"
            type="text"
            prepend-inner-icon="mdi-account"
            variant="solo"
            v-model="userInfo.id"
            required
            @keyup="idCheck"
          >
            <template v-if="hasInput" v-slot:append-inner>
              <v-btn rounded size="15" flat>
                <v-icon v-bind:style="{ color: color }">{{ icon }}</v-icon>
                <v-tooltip activator="parent" location="top">
                  <v-text>{{ message }}</v-text>
                </v-tooltip>
              </v-btn>
            </template>
          </v-text-field>

          <v-text-field
            density="comfortable"
            label="비밀번호"
            type="password"
            prepend-inner-icon="mdi-lock-outline"
            variant="solo"
            v-model="userInfo.password"
            required
          ></v-text-field>
          <v-text-field
            density="comfortable"
            label="이메일"
            type="email"
            prepend-inner-icon="mdi-email"
            variant="solo"
            v-model="userInfo.email"
            required
          ></v-text-field>
          <v-text-field
            density="comfortable"
            label="이름"
            prepend-inner-icon="mdi-account"
            variant="solo"
            v-model="userInfo.name"
            required
          ></v-text-field>
          <v-text-field
            density="comfortable"
            label="닉네임"
            type="text"
            prepend-inner-icon="mdi-account-outline"
            variant="solo"
            v-model="userInfo.nickname"
            required
            lazy-validation="between:3, 15"
            validation-visibility="live"
          ></v-text-field>
          <v-text-field
            density="comfortable"
            label="생년월일"
            type="date"
            prepend-inner-icon="mdi-calendar"
            variant="solo"
            v-model="userInfo.birth"
            required
          ></v-text-field>

          <v-radio-group
            inline
            required
            hide-details
            style="display: flex; justify-content: space-around"
          >
            <v-radio
              true-icon="mdi-gender-male"
              label="남자"
              color="blue"
              value="0"
              v-model="userInfo.gender"
            ></v-radio>
            <v-radio
              true-icon="mdi-gender-female"
              label="여자"
              color="red"
              value="1"
              v-model="userInfo.gender"
            ></v-radio>
          </v-radio-group>

          <v-text-field
            name="tel"
            label="휴대전화번호"
            id="tel"
            type="tel"
            pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
            prepend-inner-icon="mdi-cellphone"
            variant="solo"
            required
            v-model="userInfo.tel"
          ></v-text-field>
          <v-divider></v-divider>
          <v-btn type="submit" color="green" size="large" block class="mt-5">회원 정보 수정</v-btn>
        </v-form>
      </v-card>
    </v-sheet>
  </v-layout>
</template>

<style scoped>
div {
  text-align: center;
  align-self: center;
}
</style>
