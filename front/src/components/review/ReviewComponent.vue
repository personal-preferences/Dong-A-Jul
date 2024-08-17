<template>
  <div class="card mb-3 shadow-sm">
    <div class="card-body">
      <div class="d-flex align-items-center mb-2">
        <div class="me-3">
          <!-- 사용자 아바타 -->
          <img src="https://via.placeholder.com/50" class="rounded-circle" alt="User Avatar">
        </div>
        <div class="me-3">
          <h6 class="card-title mb-0">User ID: {{ props.review.userId }}</h6>
        </div>
        <div>
          <ReviewSummary :type="'user'" :id="props.review.userId"/>
        </div>
      </div>
      <div class="mb-2">
        <span :class="['badge', badgeClass]">{{ props.review.reviewScore }}점</span>
        <span class="text-muted ms-2">{{ props.review.reviewRegisteredDate }}</span>
      </div>
      <p class="card-text">{{ props.review.reviewContent }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, computed } from 'vue';
import ReviewSummary from '@/components/review/ReviewSummary.vue';

const props = defineProps({
  review: {
    type: Object,
    required: true
  }
});

// 점수에 따라 배지 클래스 결정
const badgeClass = computed(() => {
  const score = props.review.reviewScore;
  if (score >= 4.5) return 'bg-success'; // 4.5 이상: 초록색
  if (score >= 3.5) return 'bg-info';    // 3.5 이상: 파란색
  if (score >= 2.0) return 'bg-warning'; // 2.5 이상: 노란색
  return 'bg-danger'; // 2.5 미만: 빨간색
});
</script>

<style scoped>
/* 추가적인 스타일은 필요에 따라 적용 */
</style>
