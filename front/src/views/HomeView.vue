<script setup>
import { ref } from 'vue';
import Sidebar from '@/components/Sidebar.vue';
import NaverMaps from '@/components/NaverMaps.vue';

const selectedToilet = ref(null);
const allMarkerToilets = ref([]);

const handleSelectToilet = (toilet) => {
  selectedToilet.value = toilet;
};

const updateToilets = (toilets) => {
  allMarkerToilets.value = toilets;
};
</script>

<template>
  <Sidebar
      :selectedToilet="selectedToilet"
      :allMarkerToilets="allMarkerToilets"
      @selectToilet="handleSelectToilet"
  />
  <NaverMaps
      @selectToilet="handleSelectToilet"
      @updateToilets="updateToilets"
  />
</template>

<style scoped>
</style>
