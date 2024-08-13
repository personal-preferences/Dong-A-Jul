<template>
  <div id="table-container">
    <table class="table table-hover">
      <thead>
      <tr>
        <th class="POSTING_ID" scope="col">번호</th>
        <th class="TITLE" scope="col">제목</th>
        <th class="MEMBER_ID" scope="col">글쓴이</th>
        <th class="HITS" scope="col">조회</th>
        <!-- <th class="LANGUAGE" scope="col">좋아요</th>
        <th class="LOCATION_ID" scope="col">싫어요</th> -->
        <th class="POSTED_DATE" scope="col">게시 날짜</th>
      </tr>
      </thead>
      <tbody class="table-group-divider" v-if="props.posts">
      <tr v-for="post in currentPagePosts" :key="posts.postingId">
        <td class="POSTING_ID">{{ post.postingId }}</td>
        <td class="TITLE" @click="goDetailPage(post.postingId)">{{ post.title }}</td>
        <td class="MEMBER_ID">{{ post.memberId }}</td>
        <td class="HITS">{{ post.hits }}</td>
        <td class="LANGUAGE">{{ post.language }}</td>
        <td class="LOCATION_ID">{{ post.locationId }}</td>
        <td class="POSTED_DATE">{{ post.postedDate }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  review: {},
})

</script>

<style scoped>

</style>