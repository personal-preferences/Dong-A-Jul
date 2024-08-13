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
</template>

<script setup>
import { ref } from 'vue';
import axios from '@/assets/axios';  // Axios 인스턴스를 import

const email = ref('');
const password = ref('');
// const router = useRouter();

const handleLogin = async () => {
  
  console.log(email, password);
try {
  
  const response = await axios.post('/login', 
      {
          userEmail: email.value,
          userPassword: password.value
      }
  );
  
  localStorage.setItem('access', response.data.access);
  document.cookie = `refresh=${response.data.refresh}; path=/; HttpOnly; Secure; SameSite=Strict;`;

  // router.push('/dashboard');
} catch (error) {
  console.error('로그인 실패:', error);
}
};
</script>

