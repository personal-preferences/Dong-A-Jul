<template>
  <div>
    <div class="d-flex">
      <div :class="['sidebar', { 'active': isSidebarOpen }]" id="sidebar">
        <input type="text" class="form-control mb-3" placeholder="검색" />

        <ToiletInfoDetails v-if="selectedToilet" :location="selectedToilet" />

        <button
            class="btn btn-primary toggle-btn"
            @click="toggleSidebar"
            :style="{ right: isSidebarOpen ? '-15px' : '-30px' }"
        >
          {{ isSidebarOpen ? '<' : '>' }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import ToiletInfoDetails from "@/components/ToiletinfoDetails.vue";

const props = defineProps({
  selectedToilet: Object
});

const isSidebarOpen = ref(true);

const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
};

watch(() => props.selectedToilet, (newVal) => {
  if (newVal && !isSidebarOpen.value) {
    isSidebarOpen.value = true;
  }
});
</script>

<style scoped>
.sidebar {
  width: 250px;
  transition: transform 0.3s ease;
  background-color: #f8f9fa;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  transform: translateX(-100%);
}

.sidebar.active {
  transform: translateX(0);
}

.toggle-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}
</style>
