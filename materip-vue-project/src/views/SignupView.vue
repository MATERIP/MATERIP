<script setup></script>

<template>
  <v-layout row wrap>
    <v-sheet width="30rem">
      <v-card elevation="3" class="pa-10">
        <v-card-title>
          <v-layout align-center justify-center-between>
            <h3>회원가입</h3>
          </v-layout>
        </v-card-title>
        <v-form class="mt-5" method="post">
          <v-text-field
            name="id"
            label="아이디"
            id="id"
            type="text"
            prepend-inner-icon="mdi-account"
            variant="solo"
          ></v-text-field>
          <v-text-field
            name="password"
            label="비밀번호"
            id="password"
            type="password"
            prepend-inner-icon="mdi-lock-outline"
            variant="solo"
          ></v-text-field>
          <v-text-field
            name="email"
            label="이메일"
            id="email"
            type="email"
            prepend-inner-icon="mdi-email"
            variant="solo"
          ></v-text-field>
          <v-text-field
            name="name"
            label="이름"
            id="name"
            prepend-inner-icon="mdi-account"
            variant="solo"
          ></v-text-field>
          <v-text-field
            name="nickname"
            label="닉네임"
            id="nickname"
            type="text"
            prepend-inner-icon="mdi-account-outline"
            variant="solo"
          ></v-text-field>
          <v-text-field
            name="birth"
            label="생년월일"
            id="birth"
            type="date"
            prepend-inner-icon="mdi-calendar"
            variant="solo"
          ></v-text-field>
          <v-radio-group inline>
            <v-radio label="남자" value="male"></v-radio>
            <v-radio label="여자" value="female"></v-radio>
          </v-radio-group>

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
