<script setup>

import { ref } from 'vue';
import { useRouter } from "vue-router";
import { useUserStore } from '@/stores/user-store';
const router = useRouter();
const userStore = useUserStore()

const userInfo = ref({
    id: '',
    password:''
})

const login = async () => { 
    await userStore.login(userInfo.value)
    router.push("/")
}

const idRules = [
  value => !!value || '아이디를 입력하세요!',
  value => value.length >= 5 || '아이디는 최소 5자 이상이어야 합니다.',
  value => /^[A-Za-z0-9]+$/.test(value) || '아이디는 영어 대소문자와 숫자만 포함할 수 있습니다.' 
];

const passwordRules = [
  value => !!value || '비밀번호를 입력하세요!',
  value => value.length >= 5 || '비밀번호는 최소 5자 이상이어야 합니다.',
];

</script>

<template>
    <v-layout row wrap>
        <v-sheet width="30rem" >
        <v-card elevation="3" class = "pa-10">
        <v-card-title>
            <v-layout align-center justify-center-between>
                <h3>로그인</h3>
            </v-layout>
        </v-card-title>
        <v-form @submit.prevent="login">
            <v-text-field
                name="id"
                label="아이디"
                id="id"
                type="text"
                prepend-inner-icon="mdi-account"
                variant="solo"
                required
                v-model = "userInfo.id"
                :rules="idRules"
            ></v-text-field>
            <v-text-field
                name="password"
                label="비밀번호"
                id="password"
                type="password"
                prepend-inner-icon="mdi-lock-outline"
                variant="solo"
                required
                v-model = "userInfo.password"
                :rules="passwordRules"
            ></v-text-field>

            <v-divider></v-divider>
            <v-btn type="submit" color="light-blue" size="large" block class = "mt-5">로그인</v-btn>
        </v-form>
        </v-card>
        <v-layout row wrap style="display: flex; justify-content: space-around; margin:1rem">
                <a href="">비밀번호 찾기</a> |
                <a href="">아이디 찾기</a> |
                <router-link to="/signup">회원가입</router-link>
            </v-layout>
        </v-sheet>

    </v-layout>
</template>

<style scoped>

div {
    text-align: center;
    align-self: center;
}

</style>