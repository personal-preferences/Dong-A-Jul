<template>
  <div :class="['map-container', isSidebarOpen ? 'with-sidebar' : 'fullscreen']" ref="mapRef"></div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import axios from 'axios';

const props = defineProps({
  isSidebarOpen: Boolean,
});

const emit = defineEmits(['updateToilets', 'selectToilet'])

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
      const bounds = map.getBounds();
      const southWest = bounds.getSW();
      const northEast = bounds.getNE();

      let locationMarkers = []

      await axios.get('http://localhost:8080/locations', {
        params: {
          northEastLatitude: northEast.lat(),
          northEastLongitude: northEast.lng(),
          southWestLatitude: southWest.lat(),
          southWestLongitude: southWest.lng()
        }
      })
          .then(response => {
            locationMarkers = response.data;
            emit('updateToilets', locationMarkers); // 모든 화장실 정보를 부모 컴포넌트로 전달
          })
          .catch(axiosError => {
            console.log('마커 요청 오류 발생 : ', axiosError)
          });

      const markers = [];

      locationMarkers.forEach(location => {
        const position = new window.naver.maps.LatLng(location.latitude, location.longitude)

        const marker = new window.naver.maps.Marker({
          map: map,
          position: position,
          title: location.name,   // 마커에 마우스를 올렸을 때 이름 표시
          icon: {
            content: `<p>🚽</p>`, // 버튼으로 생성
            size: new window.naver.maps.Size(24, 37),
            anchor: new window.naver.maps.Point(12, 37)
          },
          zIndex: 100
        })

        window.naver.maps.Event.addListener(marker, 'click', () => {
          emit('selectToilet', location)
        })

        markers.push(marker);
      });

      window.naver.maps.Event.addListener(map, 'idle', () => {
        updateMarkers(map, markers);
      });

      // 사이드바 상태 변경에 따른 지도 리사이즈
      watch(() => props.isSidebarOpen, () => {
        setTimeout(() => {
          map.updateSize();
        }, 300); // 애니메이션 시간과 일치시킴
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

<style scoped>
.map-container {
  width: 800px;
  height: 800px;
  transition: margin-left 0.3s ease;
}

.map-container.fullscreen {
  margin-left: 0;
}

.map-container.with-sidebar {
  margin-left: 250px;
}
</style>
