<script setup>
import { ref } from "vue";
import axios from "axios";
const boardList = ref([]);
const instance = axios.create({
  baseURL: "http://localhost:8080/",
});
function fetchData() {
  instance
    .get("/board/getList", boardList.value)
    .then((response) => {
      boardList.value = response.data;
      console.log(response);
    })
    .catch(function (error) {
      console.log(error);
    });
}
fetchData();
</script>

<template>
  <div style = "height: 10rem;"></div>
  <v-table>
    <tr v-for="item in boardList" :key="item.id">
      <td class="text-xs-right">{{ item.id }}</td>
      <td class="text-xs-right">{{ item.title }}</td>
      <td class="text-xs-right">{{ item.createdAt }}</td>
    </tr>
  </v-table>
</template>

<style scoped></style>
