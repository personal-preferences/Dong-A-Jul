<template>
  <div id="map" ref="mapRef" style="width:400px;height:400px;"></div>
</template>

<script setup>
import { onMounted, ref } from 'vue';

const mapRef = ref(null);

onMounted(async () => {
  try {
    await loadNaverMapsScript();

    if (window.naver && window.naver.maps) {
      const mapOptions = {
        center: new window.naver.maps.LatLng(37.3595704, 127.105399),
        zoom: 10
      };

      const map = new window.naver.maps.Map(mapRef.value, mapOptions);

      const marker = new window.naver.maps.Marker({
        position: new window.naver.maps.LatLng(37.3595704, 127.105399),
        map: map
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
</script>