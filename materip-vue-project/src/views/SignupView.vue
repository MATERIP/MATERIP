<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
const router = useRouter();
const userInfo = ref({
  id: "",
  password: "",
  email: "",
  name: "",
  nickname: "",
  birth: "",
  tel: "",
});
const instance = axios.create({
  baseURL: "http://localhost:8080/",
});
function signup() {
  console.log(userInfo.value);
  instance
    .post("/admin/user/signup", userInfo.value)
    .then(() => {
      // userInfo.value = response.data;
      // console.log(response);
      alert("회원가입 성공");
      router.push("/login");
    })
    .catch(function (error) {
      console.log(error);
    });
}
</script>

<template>
  <v-layout row wrap>
    <v-sheet width="30rem">
      <v-card elevation="3" class="pa-10">
        <v-card-title>
          <v-layout align-center justify-center-between>
            <h3>회원가입</h3>
          </v-layout>
        </v-card-title>
        <v-form class="mt-5" @submit.prevent="signup">
          <v-text-field
            label="아이디"
            type="text"
            prepend-inner-icon="mdi-account"
            variant="solo"
            v-model="userInfo.id"
          ></v-text-field>
          <v-text-field
            label="비밀번호"
            type="password"
            prepend-inner-icon="mdi-lock-outline"
            variant="solo"
            v-model="userInfo.password"
          ></v-text-field>
          <v-text-field
            label="이메일"
            type="email"
            prepend-inner-icon="mdi-email"
            variant="solo"
            v-model="userInfo.email"
          ></v-text-field>
          <v-text-field
            label="이름"
            prepend-inner-icon="mdi-account"
            variant="solo"
            v-model="userInfo.name"
          ></v-text-field>
          <v-text-field
            label="닉네임"
            type="text"
            prepend-inner-icon="mdi-account-outline"
            variant="solo"
            v-model="userInfo.nickname"
          ></v-text-field>
          <v-text-field
            label="생년월일"
            type="date"
            prepend-inner-icon="mdi-calendar"
            variant="solo"
            v-model="userInfo.birth"
          ></v-text-field>
          <!-- <v-radio-group inline>
            <v-radio label="남자" value="male"></v-radio>
            <v-radio label="여자" value="female"></v-radio>
          </v-radio-group> -->
          <v-text-field
            label="휴대전화번호"
            type="tel"
            prepend-inner-icon="mdi-cellphone"
            variant="solo"
            v-model="userInfo.tel"
          ></v-text-field>
          <v-divider></v-divider>
          <v-btn type="submit" color="light-blue" size="large" block class="mt-5"
            >회원가입</v-btn
          >
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
