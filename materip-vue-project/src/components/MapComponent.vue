<template>
  <div>
    <div id="map"></div>
    <div class="button-group">
      <button @click="changeSize(0)">Hide</button>
      <button @click="changeSize(400)">Show</button>
      <button @click="displayMarker(markerPositions1)">Marker Set 1</button>
      <button @click="displayMarker(markerPositions2)">Marker Set 2</button>
      <button @click="displayMarker([[33.452278, 126.567803]])">Marker Set 3 (Empty)</button>
      <button @click="displayInfoWindow">Info Window</button>
    </div>
  </div>
</template>

<script setup>
/**https://apis.map.kakao.com/web/sample/markerWithCustomOverlay/ */
import { ref, onMounted, toRaw } from 'vue'

const markerPositions1 = ref([
  [33.452278, 126.567803],
  [33.452671, 126.574792],
  [33.451744, 126.572441]
])

const markerPositions2 = ref([
  [37.499590490909185, 127.0263723554437],
  [37.499427948430814, 127.02794423197847],
  [37.498553760499505, 127.02882598822454],
  [37.497625593121384, 127.02935713582038],
  [37.49629291770947, 127.02587362608637],
  [37.49754540521486, 127.02546694890695],
  [37.49646391248451, 127.02675574250912]
])

const markerPosition = ref([[37.499590490909185, 127.0263723554437]])
const markers = ref([])
let infowindow = null

const initMap = () => {
  const container = document.getElementById('map')
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 5
  }

  const kmap = new kakao.maps.Map(container, options)
  map.value = kmap
}

const map = ref(null)

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    map.value = initMap()
  } else {
    const script = document.createElement('script')

    /* global kakao */
    script.onload = () => kakao.maps.load(initMap)
    script.src =
      '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=eb6931206ff1fcdb8d82fb079ab62952'
    document.head.appendChild(script)
  }
})

const changeSize = (size) => {
  const container = document.getElementById('map')
  container.style.width = `${size}px`
  container.style.height = `${size}px`
  toRaw(map).relayout()
}

const displayMarker = (markerPositions) => {
  console.log('~~~~~')
  console.log(map.value)
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null))
  }
  console.log(markerPositions)
  const positions = markerPositions.map((position) => new kakao.maps.LatLng(...position))
  console.log(positions)
  var markerImage = new kakao.maps.MarkerImage(
    'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png',
    new kakao.maps.Size(24, 35)
  )

  if (positions.length > 0) {
    markers.value = positions.map(
      (position) =>
        new kakao.maps.Marker({
          map: map.value,
          image: markerImage,
          position: new kakao.maps.LatLng(position.Ma, position.La)
        })
    )
    console.log(markers.value)
    const bounds = positions.reduce(
      (bounds, latlng) => bounds.extend(latlng),
      new kakao.maps.LatLngBounds()
    )

    map.value.setBounds(bounds)
  }
}

const displayInfoWindow = () => {
  if (infowindow && infowindow.getMap()) {
    toRaw(map).setCenter(infowindow.getPosition())
    return
  }

  const iwContent = '<div style="padding:5px;">Hello World!</div>'
  const iwPosition = new kakao.maps.LatLng(33.450701, 126.570667)
  const iwRemoveable = true

  infowindow = new kakao.maps.InfoWindow({
    map: toRaw(map.value),
    position: iwPosition,
    content: iwContent,
    removable: iwRemoveable
  })

  toRaw(map).setCenter(iwPosition)
}
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
