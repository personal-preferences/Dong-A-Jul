<template>
    <div>
      <h1>Login Page</h1>
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
    <input type="button" @click="kakaoLogin" value="카카오로 로그인">
    <input type="button" @click="getUsersTest" value="토큰 없을 떄의 테스트">
    
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import axiosWithToken from '@/assets/axiosWithToken';
  import { useUserStore } from '@/stores/user';

  
  const email = ref('');
  const password = ref('');
  const router = useRouter();
  //const { setUser } = useUserStore();

  const handleLogin = async () => {
    try {
      const response = await axiosWithToken.post('/login', {
        userEmail: email.value,
        userPassword: password.value,
      });
      
      const access = response.headers['access'];
      localStorage.setItem('access', access);
      useUserStore(access);  // 사용자 정보 설정
      console.log('로그인 성공!');
  
      // 로그인 성공 시 홈으로 이동
      router.push('/');
    } catch (error) {
      console.error('로그인 실패:', error);
    }
  };
  const kakaoLogin = async () => {
    try {
      const response = await axiosWithToken.get('/login/kakao');
      const loginUrl = response.data; 
      // URL로 이동
      window.location.href = loginUrl;

      //router.push('/');
    } catch (error) {
      console.error('로그인 실패:', error);
    }
  };

  const getUsersTest = async () => {
  try {
    const response = await axiosWithToken.get('/users');
    console.log('User Info:', response.data);
  } catch (error) {
    console.error('User Info Fetch Error:', error);
    router.push('/login');
  }
};
  
  </script>
  