<template>
  <!-- 리뷰 헤더 -->
  <div class="review-header">
    <ReviewSummary :type="'location'" :id="props.id"/>
    <!--  정렬 기준 선택(드롭다운)  -->
    <div class="review-order">
    </div>
  </div>
  <div class="review-list">
    <div v-if="loadingState">
      Loading reviews...
    </div>
    <div v-else>
      <div v-for="(review, index) in copyReviews" :key="index" class="review-item">
        <ReviewComponent :review="review"/>
      </div>
    </div>
  </div>
  <!-- 페이지 선택 or 드래그 처리 -->
  <div class="pagenation-container">
  </div>
</template>

<script setup>
import axios from 'axios';
import {ref, onMounted} from 'vue';
import { parse, format } from 'date-fns';
import ReviewComponent from "@/components/review/ReviewComponent.vue";
import ReviewSummary from "@/components/review/ReviewSummary.vue";

// 재사용성을 고려하여 userId, locationId 조회 둘 다 활용될 수 있도록 type 추가.
const props = defineProps({
  type: String,
  id: Number
})

const reviews = [];
const copyReviews = ref([{}]);
const loadingState = ref(true);

onMounted(async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_REVIEW_SERVICE_BASE_URL}/${props.type}/${props.id}`);
    reviews.value = response.data.content;

    for (let i = 0; i < reviews.value.length; i++) {
      const reviewId = reviews.value[i].reviewId;
      const reviewContent = reviews.value[i].reviewContent;
      const reviewScore = reviews.value[i].reviewScore;
      const reviewIsDeleted = reviews.value[i].reviewIsDeleted;
      const reviewRegisteredDate = reviews.value[i].reviewRegisteredDate;
      const userId = reviews.value[i].userId;
      const locationId = reviews.value[i].locationId;

      // 날짜 문자열을 Date 객체로 변환
      const parsedDate = parse(reviewRegisteredDate, 'yyyy-MM-dd HH:mm:ss', new Date());

      copyReviews.value[i] = {
        reviewId: reviewId,
        reviewContent: reviewContent,
        reviewScore: reviewScore,
        reviewIsDeleted: reviewIsDeleted,
        reviewRegisteredDate: format(parsedDate, 'yyyy-MM-dd HH:mm:ss'),
        userId: userId,
        locationId: locationId
      };
    }
  } catch (error) {
    console.error("Error fetching reviews:", error);
  } finally {
    loadingState.value = false;
  }
});
</script>

<style scoped>
.review-item {
  padding: 1px;
}
</style>