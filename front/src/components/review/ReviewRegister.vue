<template>
  <div class="card mb-3 shadow-sm">
    <div class="card-body">
      <!-- 리뷰 작성 폼 -->
      <div class="mb-3">
        <label for="reviewScore" class="form-label">별점</label>
        <select v-model="reviewScore" id="reviewScore" class="form-select">
          <option value="5">5점 - 아주 좋음</option>
          <option value="4">4점 - 좋음</option>
          <option value="3">3점 - 보통</option>
          <option value="2">2점 - 나쁨</option>
          <option value="1">1점 - 매우 나쁨</option>
        </select>
      </div>

      <div class="mb-3">
        <label for="reviewContent" class="form-label">리뷰 내용</label>
        <textarea v-model="reviewContent" id="reviewContent" class="form-control" rows="3" placeholder="리뷰 내용을 입력하세요."></textarea>
      </div>

      <!-- 중앙 정렬된 버튼 -->
      <div class="text-center">
        <button @click="submitReview" class="btn btn-primary">리뷰 등록</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

// 실제 user, location ID로 변경 필요
const userId = 1;
const locationId = 1;

const reviewScore = ref(5);
const reviewContent = ref('');

const submitReview = async () => {
  if (!reviewContent.value) {
    alert('리뷰 내용을 입력해주세요.');
    return;
  }

  const newReview = {
    userId: userId,
    locationId: locationId,
    reviewScore: reviewScore.value,
    reviewContent: reviewContent.value,
  };

  try {
    const response = await axios.post(`${import.meta.env.VITE_REVIEW_SERVICE_BASE_URL}`, newReview);

    if (response.status === 201) {
      alert('리뷰가 성공적으로 등록되었습니다!');
      // 폼 초기화
      reviewScore.value = 5;
      reviewContent.value = '';
    } else {
      console.error('Unexpected response status:', response.status);
    }
  } catch (error) {
    console.error('Error creating review:', error);
    alert('리뷰 등록 중 오류가 발생했습니다.');
  }
};
</script>

<style scoped>
/* 필요에 따라 추가적인 스타일 적용 */
</style>
