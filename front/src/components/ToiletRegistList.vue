<template>
  <div>
    <h2>Toilet Registration List</h2>
    <table>
      <thead>
      <tr>
        <th>Toilet Name</th>
        <th>Road Name Address</th>
        <th>Number Address</th>
        <th>Latitude</th>
        <th>Longitude</th>
        <th>Status</th>
        <th>Details</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="toilet in paginatedData" :key="toilet.toiletRegistDate">
        <td>{{ toilet.toiletRegistToiletName }}</td>
        <td>{{ toilet.toiletRegistRoadNameAddress }}</td>
        <td>{{ toilet.toiletRegistNumberAddress }}</td>
        <td>{{ toilet.toiletRegistLatitude }}</td>
        <td>{{ toilet.toiletRegistLongitude }}</td>
        <td>{{ getStatus(toilet) }}</td>
        <td>
          <button @click="goToDetails(toilet.toiletRegistId)">Details</button>
        </td>
      </tr>
      </tbody>
    </table>
    <div>
      <button @click="prevPage" :disabled="currentPage === 1">Previous</button>
      <span>Page {{ currentPage }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages">Next</button>
    </div>
    <div style="margin-top: 20px;">
      <button @click="goToRegister">등록</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      toiletRegistData: [],
      currentPage: 1,
      itemsPerPage: 10,
      totalItems: 0,
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage);
    },
    paginatedData() {
      return this.toiletRegistData;
    },
  },
  methods: {
    async fetchData(pageNum) {
      try {
        const response = await axios.get(`http://localhost:8765/approves`, {
          params: {
            pageNum: pageNum - 1, // Spring에서 페이지는 0부터 시작하므로
          },
        });
        this.toiletRegistData = response.data.content;
        this.totalItems = response.data.totalElements;
        this.currentPage = pageNum;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.fetchData(this.currentPage - 1);
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.fetchData(this.currentPage + 1);
      }
    },
    getStatus(toilet) {
      if (!toilet.toiletRegistIsApproved && toilet.toiletRegistConfirmedDate === null) {
        return "처리중";
      } else if (!toilet.toiletRegistIsApproved && toilet.toiletRegistConfirmedDate !== null) {
        return "반려";
      } else if (toilet.toiletRegistIsApproved && toilet.toiletRegistConfirmedDate !== null) {
        return "승인";
      } else {
        return "알 수 없음";
      }
    },
    goToDetails(toiletRegistId) {
      this.$router.push({ name: 'ToiletDetails', params: { id: toiletRegistId } });
    },
    goToRegister() {
      this.$router.push({ name: 'RegisterToilet' });
    },
  },
  mounted() {
    this.fetchData(this.currentPage);
  },
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

table, th, td {
  border: 1px solid black;
}

th, td {
  padding: 10px;
  text-align: left;
}

button {
  margin: 5px;
}
</style>
