<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, toRaw } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
import router from "../router";
const route = useRoute();
const attractionName = route.params.attractionName;
const attractionMapCoord = ref([]);
const attractionMapTitle = ref([]);
const attractionMapContentId = ref([]);
const attractionList = ref([]);

onMounted(() => {
  fetchAttractionInfo();
  console.log(attractionMapCoord.value);
  if (window.kakao && window.kakao.maps) {
    map.value = initMap();
  } else {
    const script = document.createElement("script");
    /* global kakao */
    script.onload = () => kakao.maps.load(initMap);
    script.src =
      "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=eb6931206ff1fcdb8d82fb079ab62952";
    document.head.appendChild(script);
  }
});
const fetchAttractionInfo = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/attraction/searchinfo/${encodeURIComponent(attractionName)}`,
      {
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
      }
    );
    attractionList.value = response.data;
    parseAttractionInfo();
  } catch (error) {
    console.error("Error fetching attraction info:", error);
  }
};

const parseAttractionInfo = () => {
  attractionList.value.forEach((attr) => {
    attractionMapCoord.value.push([attr.latitude, attr.longitude]);
    attractionMapTitle.value.push(attr.title);
    attractionMapContentId.value.push(attr.contentId);
  });
  console.log(attractionMapCoord.value[0]);
};

const markers = ref([]);

const initMap = () => {
  const container = document.getElementById("map");
  container.style.width = "800px";
  container.style.height = "500px";
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  const kmap = new kakao.maps.Map(container, options);
  map.value = kmap;
  console.log(attractionMapCoord.value);
  displayMarker(attractionMapCoord.value);
};
const map = ref(null);

const changeSize = (size) => {
  const container = document.getElementById("map");
  container.style.width = `${size}px`;
  container.style.height = `${size}px`;
  toRaw(map).relayout();
};
const displayMarker = (markerPositions) => {
  console.log("~~~~~");
  console.log(markerPositions[0]);
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
  console.log(markerPositions);
  const positions = [];
  for (let idx = 0; idx < markerPositions.length; idx++) {
    positions.push(
      new kakao.maps.LatLng(markerPositions[idx][0], markerPositions[idx][1])
    );
  }
  console.log(positions);
  var markerImage = new kakao.maps.MarkerImage(
    "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
    new kakao.maps.Size(24, 35)
  );
  let idx = 0;
  if (positions.length > 0) {
    markers.value = positions.map(
      (position) =>
        new kakao.maps.Marker({
          map: map.value,
          image: markerImage,
          title: attractionMapTitle.value[idx++],
          position: new kakao.maps.LatLng(position.Ma, position.La),
        })
    );

    for (let idx = 0; idx < markers.value.length; idx++) {
      kakao.maps.event.addListener(markers.value[idx], "click", function () {
        console.log("click");
        console.log(attractionMapContentId.value[idx]);
        router.push("../attraction/detail/" + attractionMapContentId.value[idx]);
      });
    }

    const bounds = positions.reduce(
      (bounds, latlng) => bounds.extend(latlng),
      new kakao.maps.LatLngBounds()
    );
    map.value.setBounds(bounds);
  }
};
</script>

<style scoped>
#map {
  width: 400px;
  height: 400px;
}
.button-group {
  margin: 10px 0px;
}
button {
  margin: 0 3px;
}
</style>
