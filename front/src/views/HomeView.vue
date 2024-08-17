<script setup>
import { ref } from 'vue';
import Sidebar from '@/components/Sidebar.vue';
import NaverMaps from '@/components/NaverMaps.vue';

const selectedToilet = ref(null);

const handleSelectToilet = (toilet) => {
  selectedToilet.value = toilet;
};
</script>

<template>
  <Sidebar :selectedToilet="selectedToilet" />
  <NaverMaps @selectToilet="handleSelectToilet" />
</template>

<style scoped>
</style>
