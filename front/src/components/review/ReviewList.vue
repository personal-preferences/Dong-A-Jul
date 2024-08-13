<template>
  <div class="review-header">
<!-- 리뷰 헤더 -->
    <div class="order">
<!--  정렬 기준 선택(드롭다운)  -->
    </div>
  </div>
  <div class="review-list">
    <div v-if="loadingState">
      Loading reviews...
    </div>
    <div v-else>
      <div v-for="(review, index) in copyReviews" :key="index" class="review-item">
        <p v-if="review.reviewIsDeleted" class="deleted-review">작성자에 의해 삭제된 리뷰입니다.</p>
        <div v-else>
          <Review :review="review"/>
        </div>
        <hr />
      </div>
    </div>
  </div>
  <div class="select-page">
<!-- 페이지 선택 or 드래그 처리 -->
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { format } from 'date-fns';
import Review from "@/components/review/Review.vue";
// import { useRoute } from 'vue-router';

// List로 받아오는 재사용성을 고려하여 userId, locationId 조회 둘 다 활용될 수 있도록 type 추가.
// type과 id를 받아오는 방식으로 설계
// const type = useRoute().params.type;
// const id = useRoute().params.id;

const type = "locationId"
const id = 1;

const reviews = [];
const copyReviews = ref([{}]);
const loadingState = ref(true);

onMounted(async () => {
  try {
    const response = await axios.get(`/api/reviews/${type}/${id}`);
    loadingState.value = false;
    reviews.value = response.data.ReviewResponse;

    for (let i = 0; i < reviews.value.length; i++) {
      const reviewId = reviews.value[i].reviewId;
      const reviewContent = reviews.value[i].reviewContent;
      const reviewScore = reviews.value[i].reviewScore;
      const reviewIsDeleted = reviews.value[i].reviewIsDeleted;
      const reviewRegisteredDate = reviews.value[i].reviewRegisteredDate;
      const userId = reviews.value[i].userId;
      const locationId = reviews.value[i].locationId;

      copyReviews.value[i] = {
        reviewId: reviewId,
        reviewContent: reviewContent,
        reviewScore: reviewScore,
        reviewIsDeleted: reviewIsDeleted,
        reviewRegisteredDate: format(new Date(reviewRegisteredDate[0], reviewRegisteredDate[1] - 1, reviewRegisteredDate[2], reviewRegisteredDate[3], reviewRegisteredDate[4], reviewRegisteredDate[5]), 'yyyy-MM-dd HH:mm:ss'),
        userId: userId,
        locationId: locationId
      };
    }

  } catch (error) {
    console.error("Error fetching reviews:", error);
  }
});
</script>

<style scoped>
.review-item {
}

.deleted-review {
  color: dimgray;
}
</style>