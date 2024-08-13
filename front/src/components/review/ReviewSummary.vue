<template>
  <div class="review-summary">
    <div v-if="!loadingState">
      <p>평균 점수: {{ copySummary.averageScore }}</p>
      <p>총 리뷰 수: {{ copySummary.reviewCount }}</p>
    </div>
    <div v-else>
      <p>로딩 중...</p>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';

// 재사용성을 고려하여 userId, locationId 조회 둘 다 활용될 수 있도록 type 추가.
const props = defineProps({
  type: String,
  id: Number
})

const summary = {};
const copySummary = ref({});
const loadingState = ref(true);


onMounted(async () => {
  try {
    const response = await axios.get(`/api/reviews/${props.type}/${props.id}/summary`);
    loadingState.value = false;
    summary.value = response.data.ReviewSummary;

    copySummary.value = {
      averageScore: summary.value.averageScore,
      reviewCount: summary.value.reviewCount,
      id: summary.value.id,
    };
  } catch (error) {
    console.error("Error fetching summary:", error);
  } finally {
    loadingState.value = false;
  }
});
</script>

<style scoped>

</style>