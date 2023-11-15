<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
// import { useField, useForm } from "vee-validate";
import axios from "axios";

// const { handleSubmit } = useForm({
//   validationSchema: {
//     id(value) {
//       if (value?.length >= 5) return true;

//       return "아이디는 5글자 이상이어야 합니다!";
//     },
//     password(value) {
//       if (value?.length >= 8) return true;

//       return "비밀번호는 8글자 이상이어야 합니다!";
//     },
//     email(value) {
//       if (/^[a-z.-]+@[a-z.-]+\.[a-z]+$/i.test(value)) return true;

//       return "유효한 이메일이 아닙니다";
//     },
//     name(value) {
//       if (value) return true;

//       return "이름을 입력하세요!";
//     },
//     nickname(value){
//       if(value?.length >= 5) return true;

//       return "닉네임은 5글자 이상이어야 합니다!";
//     },
//     tel(value){
//       if(/^[0-9]{0,13}$/i.test(value)) return true;

//       return '올바른 형식이 아닙니다!'
//     },
//     gender(value){
//       if(value) return true;

//       return '성별을 선택하세요!'
//     }
//   },
// });

// const submit = handleSubmit((values) => {
//   alert(JSON.stringify(values, null, 2));
// });

const router = useRouter();
const userInfo = ref({
  id: null,
  password: "",
  email: "",
  name: "",
  nickname: "",
  birth: "",
  tel: "",
  gender: "",
});
const message = ref("");
const color = ref("");
const icon = ref("");
const hasInput = ref(false);

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

onMounted(() => {
  hasInput.value = false;
});

function idCheck() {
  hasInput.value = true;
  // console.log(userInfo.value.id)
  instance
    .post("/admin/user", { id: userInfo.value.id })
    .then(function (response) {
      //console.log(response.data);
      if (response.data == 1) {
        message.value = "중복된 아이디 존재";
        color.value = "red";
        icon.value = "mdi-alert-circle";
      } else if (userInfo.value.id === "") {
        message.value = "아이디를 입력하세요!";
        color.value = "red";
        icon.value = "mdi-alert-circle";
      } else {
        message.value = "사용 가능한 아이디";
        color.value = "green";
        icon.value = "mdi-check-circle";
      }
    })
    .catch(function (error) {
      console.log(error);
    });
}
console.log(userInfo.value.id);
</script>

<template>
  <v-layout row wrap>
    <v-sheet width="30rem">
      <v-card elevation="3">
        <v-card-title>
          <v-layout align-center justify-center-between>
            <p style="font-weight: bold">회원가입</p>
          </v-layout>
        </v-card-title>
        <v-form class="mt-5" @submit.prevent="signup">
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
