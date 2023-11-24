<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script setup>
/**https://apis.map.kakao.com/web/sample/markerWithCustomOverlay/ */
import { ref, onMounted, defineProps, watch, computed } from "vue";

const props = defineProps(["mapData"]);
const mapData = computed(() => {
  return props.mapData;
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(mapData.value["latitude"], mapData.value["longitude"]),
    level: 5,
  };

  const kmap = new kakao.maps.Map(container, options);
  map.value = kmap;

  const markerImage = new kakao.maps.MarkerImage(
    "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
    new kakao.maps.Size(24, 35)
  );

  new kakao.maps.Marker({
    map: map.value,
    image: markerImage,
    position: new kakao.maps.LatLng(
      mapData.value["latitude"],
      mapData.value["longitude"]
    ),
  });
};
watch(
  () => mapData.value,
  () => {
    console.log("mapData changed");
    console.log(mapData.value);
  }
);
const map = ref(null);

const fetchMap = function () {
  if (window.kakao && window.kakao.maps) {
    map.value = initMap();
  } else {
    const script = document.createElement("script");

    /* global kakao */
    script.onload = () => {
      kakao.maps.load(initMap);
    };
    script.src =
      "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=eb6931206ff1fcdb8d82fb079ab62952";
    document.head.appendChild(script);
  }
};

onMounted(() => {
  fetchMap();
});
</script>

<style scoped>
#map {
  min-width: 50rem;
  max-width: 80rem;
  width: 500px;
  height: 400px;
}

.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}
</style>
