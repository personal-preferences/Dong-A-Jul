<template>
  <div id="map" ref="mapRef" style="width:400px;height:400px;"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';

const mapRef = ref(null);
const MARKER_SPRITE_X_OFFSET = 29;
const MARKER_SPRITE_Y_OFFSET = 50;
const MARKER_SPRITE_POSITION = {
  "A0": [0, 0],
  "B0": [MARKER_SPRITE_X_OFFSET, 0],
  "C0": [MARKER_SPRITE_X_OFFSET*2, 0],
  "D0": [MARKER_SPRITE_X_OFFSET*3, 0],
  "E0": [MARKER_SPRITE_X_OFFSET*4, 0],
  "F0": [MARKER_SPRITE_X_OFFSET*5, 0],
  "G0": [MARKER_SPRITE_X_OFFSET*6, 0],
  "H0": [MARKER_SPRITE_X_OFFSET*7, 0],
  "I0": [MARKER_SPRITE_X_OFFSET*8, 0],

  "A1": [0, MARKER_SPRITE_Y_OFFSET],
  "B1": [MARKER_SPRITE_X_OFFSET, MARKER_SPRITE_Y_OFFSET],
  "C1": [MARKER_SPRITE_X_OFFSET*2, MARKER_SPRITE_Y_OFFSET],
  "D1": [MARKER_SPRITE_X_OFFSET*3, MARKER_SPRITE_Y_OFFSET],
  "E1": [MARKER_SPRITE_X_OFFSET*4, MARKER_SPRITE_Y_OFFSET],
  "F1": [MARKER_SPRITE_X_OFFSET*5, MARKER_SPRITE_Y_OFFSET],
  "G1": [MARKER_SPRITE_X_OFFSET*6, MARKER_SPRITE_Y_OFFSET],
  "H1": [MARKER_SPRITE_X_OFFSET*7, MARKER_SPRITE_Y_OFFSET],
  "I1": [MARKER_SPRITE_X_OFFSET*8, MARKER_SPRITE_Y_OFFSET],

  "A2": [0, MARKER_SPRITE_Y_OFFSET*2],
  "B2": [MARKER_SPRITE_X_OFFSET, MARKER_SPRITE_Y_OFFSET*2],
  "C2": [MARKER_SPRITE_X_OFFSET*2, MARKER_SPRITE_Y_OFFSET*2],
  "D2": [MARKER_SPRITE_X_OFFSET*3, MARKER_SPRITE_Y_OFFSET*2],
  "E2": [MARKER_SPRITE_X_OFFSET*4, MARKER_SPRITE_Y_OFFSET*2],
  "F2": [MARKER_SPRITE_X_OFFSET*5, MARKER_SPRITE_Y_OFFSET*2],
  "G2": [MARKER_SPRITE_X_OFFSET*6, MARKER_SPRITE_Y_OFFSET*2],
  "H2": [MARKER_SPRITE_X_OFFSET*7, MARKER_SPRITE_Y_OFFSET*2],
  "I2": [MARKER_SPRITE_X_OFFSET*8, MARKER_SPRITE_Y_OFFSET*2]
};


onMounted(async () => {
  try {
    await loadNaverMapsScript();

    if (window.naver && window.naver.maps) {
      const mapOptions = {
        center: new window.naver.maps.LatLng(37.3595704, 127.105399),
        zoom: 10
      };

      const map = new window.naver.maps.Map(mapRef.value, mapOptions);
      const bounds = map.getBounds();
      const southWest = bounds.getSW();
      const northEast = bounds.getNE();
      const lngSpan = northEast.lng() - southWest.lng();
      const latSpan = northEast.lat() - southWest.lat();

      const markers = [];

      for (const key in MARKER_SPRITE_POSITION) {
        const position = new window.naver.maps.LatLng(
            southWest.lat() + latSpan * Math.random(),
            southWest.lng() + lngSpan * Math.random()
        );

        const marker = new window.naver.maps.Marker({
          map: map,
          position: position,
          title: key,
          icon: {
            content: '<p>🚽</p>',
            size: new window.naver.maps.Size(24, 37),
            anchor: new window.naver.maps.Point(12, 37),
            origin: new window.naver.maps.Point(MARKER_SPRITE_POSITION[key][0], MARKER_SPRITE_POSITION[key][1])
          },
          zIndex: 100
        });

        markers.push(marker);
      }

      window.naver.maps.Event.addListener(map, 'idle', () => {
        updateMarkers(map, markers);
      });

    } else {
      console.error('Naver Maps API is not loaded');
    }
  } catch (error) {
    console.error('Error initializing map:', error);
  }
});

function loadNaverMapsScript() {
  return new Promise((resolve, reject) => {
    const script = document.createElement('script');
    script.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${import.meta.env.VITE_NAVER_MAPS_CLIENT_ID}`;
    script.async = true;
    script.onload = resolve;
    script.onerror = reject;
    document.head.appendChild(script);
  });
}

function updateMarkers(map, markers) {
  const mapBounds = map.getBounds();
  markers.forEach(marker => {
    const position = marker.getPosition();
    if (mapBounds.hasLatLng(position)) {
      showMarker(map, marker);
    } else {
      hideMarker(map, marker);
    }
  });
}

function showMarker(map, marker) {
  if (marker.getMap()) return;
  marker.setMap(map);
}

function hideMarker(map, marker) {
  if (!marker.getMap()) return;
  marker.setMap(null);
}
</script>