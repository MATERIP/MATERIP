<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();
const boardList = ref([]);
const instance = axios.create({
  baseURL: "http://localhost:8080/",
});

const itemsPerPage = ref(10);
const page = ref(1);
const headers = [
  {
    align: "center",
    key: "title",
    sortable: false,
    title: '제목'
  },
  
  { title: "작성자", key: "author", align: "center", sortable: false },
  { title: "작성일", key: "createdAt", align: "center", value: (item) => item.createdAt.substring(0, 10), sortable: false },
  { title: "조회", key: "hits", align: "center", sortable: false },

];



const pageCount = computed(() => Math.ceil(boardList.value.length / itemsPerPage.value));

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
  
  <div style="height: 10rem"></div>
  <v-data-table
    v-model:page="page"
    :headers="headers"
    :items="boardList"
    :items-per-page="itemsPerPage"
    class="elevation-0" style="width: 80%; margin: 0 auto;"
    hover
  >
  <!-- <template v-slot:top>
    <v-text-field
      :model-value="itemsPerPage"
      class="pa-2"
      hide-details
      label="Items per page"
      min="5"
      max="20"
      type="number"
      @update:model-value="itemsPerPage = parseInt($event, 10)"
      ></v-text-field>
  </template> -->
  
    <template v-slot:bottom>
      <div class="text-center pt-2">
        <v-pagination v-model="page" :length="pageCount"></v-pagination>
      </div>
    </template>
  </v-data-table>
</template>

<style scoped></style>
