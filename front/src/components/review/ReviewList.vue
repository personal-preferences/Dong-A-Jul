<template>
  <div>
    <!-- 리뷰 헤더 -->
    <div class="d-flex flex-column align-items-center mb-4">
      <h4 class="fw-bold p-3">리뷰 요약</h4>
      <ReviewSummary :type="'location'" :id="props.id"/>
      <div>
        <!-- 정렬 기준 버튼 그룹 -->
        <button
          type="button"
          class="btn me-2"
          :style="getButtonStyle('reviewRegisteredDate')"
          @click="toggleSort('reviewRegisteredDate')">
          날짜순
          <i :class="getSortIcon('reviewRegisteredDate')"></i>
        </button>
        <button
          type="button"
          class="btn"
          :style="getButtonStyle('reviewScore')"
          @click="toggleSort('reviewScore')">
          별점순
          <i :class="getSortIcon('reviewScore')"></i>
        </button>
      </div>
    </div>

    <!-- 리뷰 목록 -->
    <div class="review-list">
      <div v-if="loadingState" class="text-center">Loading reviews...</div>
      <div v-else>
        <div v-if="reviews.length === 0" class="card card-body text-center">남겨진 리뷰가 없습니다.<br>첫 리뷰를 남겨보세요!</div>
        <div v-else>
          <div v-for="review in copyReviews" :key="review.reviewId" class="review-item mb-3 d-flex align-items-center">
            <ReviewComponent :review="review" class="flex-grow-1"/>
            <button v-if="props.type === 'user'" class="btn btn-danger btn-sm ms-3" @click="deleteReview(review.reviewId)">
              삭제
            </button>
          </div>



          <!-- 페이지네이션 -->
          <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
              <li class="page-item" :class="{ disabled: page.value === 0 }">
                <button class="page-link" @click="changePage(page.value - 1)">Previous</button>
              </li>
              <li class="page-item" :class="{ active: page.value === i }" v-for="i in visiblePages" :key="i">
                <button class="page-link" @click="changePage(i)">{{ i + 1 }}</button>
              </li>
              <li class="page-item" :class="{ disabled: page.value === totalPages - 1 }">
                <button class="page-link" @click="changePage(page.value + 1)">Next</button>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import ReviewComponent from '@/components/review/ReviewComponent.vue';
import ReviewSummary from '@/components/review/ReviewSummary.vue';

const props = defineProps({
  type: String,
  id: Number
});

const reviews = ref([]);
const copyReviews = ref([]);
const loadingState = ref(true);
const page = ref(0);
const size = ref(10); // 페이지당 항목 수 (기본값)
const totalPages = ref(1);
const sort = ref('reviewRegisteredDate');
const direction = ref('DESC');

// 페이지네이션을 1부터 10까지로 제한하기 위한 변수
const visiblePages = computed(() => {
  const start = Math.max(0, page.value - 4);
  const end = Math.min(totalPages.value, start + 10);
  return Array.from({ length: end - start }, (_, i) => start + i);
});

const fetchReviews = async () => {
  try {
    loadingState.value = true;
    const response = await axios.get(`${import.meta.env.VITE_REVIEW_SERVICE_BASE_URL}/${props.type}/${props.id}`, {
      params: {
        page: page.value,
        size: size.value,
        sort: sort.value,
        direction: direction.value
      }
    });

    if (response.status === 204) {
      reviews.value = [];
      totalPages.value = 1; // 204 응답 시 총 페이지 수는 1로 설정
    } else {
      reviews.value = response.data.content;
      totalPages.value = response.data.totalPages;
    }
    copyReviews.value = reviews.value;
  } catch (error) {
    console.error("Error fetching reviews:", error);
  } finally {
    loadingState.value = false;
  }
};

const changePage = (newPage) => {
  if (newPage >= 0 && newPage < totalPages.value) {
    page.value = newPage;
    fetchReviews();
  }
};

const toggleSort = (newSort) => {
  if (sort.value === newSort) {
    // 이미 선택된 정렬 기준이라면 방향 변경
    direction.value = direction.value === 'DESC' ? 'ASC' : 'DESC';
  } else {
    // 새로운 정렬 기준이 선택된 경우, 방향을 내림차순으로 설정
    sort.value = newSort;
    direction.value = 'DESC';
  }
  fetchReviews();
};

const getSortIcon = (currentSort) => {
  if (sort.value !== currentSort) {
    return ''; // 비활성화된 경우 아이콘 없음
  }
  return direction.value === 'DESC'
    ? 'bi bi-caret-down-fill'  // Bootstrap Icons: 내림차순
    : 'bi bi-caret-up-fill';    // Bootstrap Icons: 오름차순
};

// 버튼 스타일을 직접 설정하는 함수
const getButtonStyle = (currentSort) => {
  if (sort.value === currentSort) {
    return {
      backgroundColor: '#007bff', // 활성화된 버튼 색상
      color: '#ffffff',           // 텍스트 색상
    };
  } else {
    return {
      backgroundColor: '#f8f9fa', // 비활성화된 버튼 색상
      color: '#6c757d'            // 텍스트 색상
    };
  }
};

const deleteReview = async (reviewId) => {
  try {
    const confirmDelete = confirm("정말로 이 리뷰를 삭제하시겠습니까?");
    if (!confirmDelete) return;

    await axios.delete(`${import.meta.env.VITE_REVIEW_SERVICE_BASE_URL}/${reviewId}`);
    alert('리뷰가 삭제되었습니다.');
    fetchReviews(); // 리뷰 목록 갱신
  } catch (error) {
    console.error("Error deleting review:", error);
    alert('리뷰 삭제 중 오류가 발생했습니다.');
  }
};


onMounted(() => {
  fetchReviews();
});
</script>


<style scoped>
.btn {
  border-radius: 0.25rem;
  border: 1px solid #ced4da;
  font-size: 0.875rem; /* 버튼 텍스트 크기 */
}

i {
  margin-left: 0.5rem;
}
</style>
