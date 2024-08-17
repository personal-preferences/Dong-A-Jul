<script setup>
import { ref } from 'vue';
import NaverMaps from "@/components/NaverMaps.vue";
import Sidebar from "@/components/Sidebar.vue";

// Sidebar의 열림/닫힘 상태를 관리
const isSidebarOpen = ref(true);

function toggleSidebar() {
  isSidebarOpen.value = !isSidebarOpen.value;
}
</script>

<template>
  <div class="app-container">
    <Sidebar :isSidebarOpen="isSidebarOpen" @toggle-sidebar="toggleSidebar" />
    <NaverMaps :isSidebarOpen="isSidebarOpen" />
  </div>
</template>

<style scoped>
.app-container {
  display: flex;
  height: 100vh; /* 화면 전체 높이를 차지 */
}

.sidebar {
  flex-shrink: 0;
  width: 250px;
}

.map-container {
  flex-grow: 1;
  height: 100vh;
  transition: margin-left 0.3s ease;
}

.map-container.fullscreen {
  margin-left: 0;
}

.map-container.with-sidebar {
  margin-left: 250px; /* 사이드바가 열려 있을 때 지도 위치 조정 */
}
</style>
