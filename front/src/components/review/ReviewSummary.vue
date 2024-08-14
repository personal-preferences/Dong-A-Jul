<template>
  <div class="card text-center mb-3 shadow-sm fixed-size-card">
    <div class="card-body">
      <div class="d-flex justify-content-center align-items-center" v-if="!loadingState">
        <p class="card-text text-muted me-2 mb-0 fs-8">후기 {{ copySummary.reviewCount }}</p>
        <p class="card-text text-muted mb-0 fs-8">평점 {{ copySummary.averageScore }}</p>
      </div>
      <div v-else>
        <p class="card-text fs-8">로딩 중...</p>
      </div>
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
    console.log(`${import.meta.env.VITE_REVIEW_SERVICE_BASE_URL}/${props.type}/${props.id}/summary`);
    const response = await axios.get(`${import.meta.env.VITE_REVIEW_SERVICE_BASE_URL}/${props.type}/${props.id}/summary`);
    summary.value = response.data;

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
/* 카드의 크기를 작게 조정 */
.fixed-size-card {
  width: 100px;
  height: 35px;
  overflow: hidden; /* 내용이 넘치면 숨김 처리 */
}

.card-body {
  padding: 0.5rem; /* 카드 내부 패딩 조정 */
}

.card-text {
  font-size: 0.7rem; /* 기본 폰트 크기를 더 작게 조정 */
}
</style>