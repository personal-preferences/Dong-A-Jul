<template>
  <div class="review-container">
    <p>User ID: {{ props.review.userId }}</p>
    <ReviewSummary :type="'user'" :id="props.review.userId"/>
    <p>평점: {{ props.review.reviewScore }}</p>
    <p>작성 날짜: {{ props.review.reviewRegisteredDate }}</p>
    <p>{{ props.review.reviewContent }}</p>
  </div>
</template>

<script setup>
import {defineProps} from 'vue';
import ReviewSummary from '@/components/review/ReviewSummary.vue'

const props = defineProps({
  review: {
    type: Object,
    required: true
  }
});

</script>

<style scoped>
.review-container {
  padding: 10px;
  border: 1px solid;
  margin-bottom: 10px;
  border-radius: 5px;
}
</style>