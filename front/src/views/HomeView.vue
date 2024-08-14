<script setup>
import NaverMaps from "@/components/NaverMaps.vue";
import ReviewList from '@/components/review/ReviewList.vue'
</script>

<template>
<!--<NaverMaps />-->
<ReviewList :type="'location'" :id="1"/>
</template>

<style scoped>

</style>