<template>
  <div class="addon-details">
    <!-- 조회 섹션 -->
    <div class="section">
      <h2>조회</h2>
      <input v-model="viewUserEmail" placeholder="User Email" />
      <input v-model="viewToiletLocationId" type="number" placeholder="Toilet Location ID" />
      <button @click="getAddon">조회</button>

      <div v-if="viewAddonInfo">
        <p><strong>Memo Content:</strong> {{ viewAddonInfo.memoContent }}</p>
        <p><strong>Bookmarked:</strong> {{ viewAddonInfo.isBookmarked ? 'Yes' : 'No' }}</p>
      </div>
    </div>

    <!-- 생성 섹션 -->
    <div class="section">
      <h2>생성</h2>
      <input v-model="createAddon.memoContent" placeholder="Memo Content" />
      <input v-model="createAddon.userEmail" placeholder="User Email" />
      <input v-model="createAddon.toiletLocationId" type="number" placeholder="Toilet Location ID" />
      <label>
        Bookmarked:
        <input v-model="createAddon.isBookmarked" type="checkbox" />
      </label>
      <button @click="createAddonFn">생성</button>
    </div>

    <!-- 수정 섹션 -->
    <div class="section">
      <h2>수정</h2>
      <input v-model="updateUserEmail" placeholder="User Email" />
      <input v-model="updateToiletLocationId" type="number" placeholder="Toilet Location ID" />
      <button @click="getAddonForUpdate">조회</button>

      <div v-if="updateAddonInfo">
        <input v-model="updateAddonInfo.memoContent" placeholder="Memo Content" />
        <label>
          Bookmarked:
          <input v-model="updateAddonInfo.isBookmarked" type="checkbox" />
        </label>
        <button @click="updateAddonFn">수정</button>
      </div>
    </div>

    <!-- 에러 메시지 표시 -->
    <div v-if="errorMessage" class="error-message">
      <p>{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const errorMessage = ref('')

// 조회 섹션
const viewUserEmail = ref('')
const viewToiletLocationId = ref(null)
const viewAddonInfo = ref(null)

const getAddon = async () => {
  try {
    const response = await axios.post('http://localhost:8080/addons/get', {
      userEmail: viewUserEmail.value,
      toiletLocationId: viewToiletLocationId.value
    })
    viewAddonInfo.value = response.data
    errorMessage.value = ''  // 성공 시 에러 메시지 초기화
  } catch (error) {
    errorMessage.value = error.response?.data?.message || '정보 조회에 실패했습니다.'
  }
}

// 생성 섹션
const createAddon = ref({
  memoContent: '',
  isBookmarked: false,
  userEmail: '',
  toiletLocationId: null
})

const createAddonFn = async () => {
  try {
    const response = await axios.post('http://localhost:8080/addons/create', createAddon.value)
    alert('Addon이 생성되었습니다.')
    createAddon.value = {
      memoContent: '',
      isBookmarked: false,
      userEmail: '',
      toiletLocationId: null
    }
    errorMessage.value = ''  // 성공 시 에러 메시지 초기화
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Addon 생성에 실패했습니다.'
  }
}

// 수정 섹션
const updateUserEmail = ref('')
const updateToiletLocationId = ref(null)
const updateAddonInfo = ref(null)

const getAddonForUpdate = async () => {
  try {
    const response = await axios.post('http://localhost:8080/addons/get', {
      userEmail: updateUserEmail.value,
      toiletLocationId: updateToiletLocationId.value
    })
    updateAddonInfo.value = response.data
    errorMessage.value = ''  // 성공 시 에러 메시지 초기화
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Addon 조회에 실패했습니다.'
  }
}

const updateAddonFn = async () => {
  try {
    const response = await axios.patch(`http://localhost:8080/addons/${updateAddonInfo.value.addonId}`, {
      memoContent: updateAddonInfo.value.memoContent,
      isBookmarked: updateAddonInfo.value.isBookmarked
    }, {
      headers: {
        userEmail: updateUserEmail.value
      }
    })
    alert('Addon이 수정되었습니다.')
    errorMessage.value = ''  // 성공 시 에러 메시지 초기화
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Addon 수정에 실패했습니다.'
  }
}
</script>

<style>
.addon-details {
  padding: 20px;
}
.section {
  margin-bottom: 20px;
}
label {
  margin-right: 10px;
}
input[type="checkbox"] {
  margin-left: 10px;
}
button {
  margin-top: 10px;
}
.error-message {
  color: red;
  margin-top: 20px;
}
</style>
