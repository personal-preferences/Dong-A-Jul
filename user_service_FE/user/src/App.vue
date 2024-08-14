<template>
  <div>
    <h2>로그인</h2>
    <form @submit.prevent="handleLogin">
      <div>
        <label for="email">이메일:</label>
        <input type="email" v-model="email" id="email" required />
      </div>
      <div>
        <label for="password">비밀번호:</label>
        <input type="password" v-model="password" id="password" required />
      </div>
      <button type="submit">로그인</button>
    </form>
  </div>

  <div>
    <input type="button" @click="getUserInfo" value="asdf"/>  

  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from '@/assets/axios';  // Axios 인스턴스를 import

const email = ref('');
const password = ref('');


const getUserInfo = async ()=>{
  try {
    const response = await axios.get('/users/info');
    console.log('User Info:', response.data);
  } catch (error) {
    console.error('User Info Fetch Error:', error);
  }
};

// 로그인 핸들러
const handleLogin = async () => {
  try {
    const response = await axios.post('/login', {
        userEmail: email.value,
        userPassword: password.value
    });
    
    // access token 처리
    const access = response.headers['access'];
    console.log('access token:', access);
    localStorage.setItem('access', access);

    // refresh token은 쿠키로 처리되므로, 클라이언트에서 직접 접근하지 않습니다.
    console.log('로그인 성공! 필요한 요청에서 자동으로 쿠키를 사용합니다.');

  } catch (error) {
    console.error('로그인 실패:', error);

  }
};

</script>
