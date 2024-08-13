<template>
  <div id="review-container">
    <p>User ID: {{ review.userId }}</p>
    <p>평점: {{ review.reviewScore }}</p>
    <p>작성 날짜: {{ review.reviewRegisteredDate }}</p>
    <p>{{ review.reviewContent }}</p>
  </div>
</template>

<script setup>
import {defineProps} from 'vue';

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