<template>
    <div>
      <h1>Regist Page</h1>
      <form @submit.prevent="registUser">
        <div>
          <label for="email">이메일:</label>
          <input type="email" v-model="email" id="email" required />
        </div>
        <div>
          <label for="nickname">닉네임:</label>
          <input type="text" v-model="nickname" id="nickname" required />
        </div>
        <div>
          <label for="password">비밀번호:</label>
          <input type="password" v-model="password" id="password" required />
        </div>
        
        <button type="submit">회원가입</button>
      </form>
    </div>

    
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import axiosWithToken from '@/assets/axiosWithToken';
  
  const email = ref('');
  const nickname = ref('');
  const password = ref('');
  const router = useRouter();
  
  const registUser = async () => {
    try {
      const response = await axiosWithToken.post('/users/regist', {
        userEmail: email.value,
        userPassword: password.value,
        userNickname: nickname.value,
      });
      
      const access = response.headers['access'];
      localStorage.setItem('access', access);
      console.log('회원가입 성공!');
  

      router.push('/Login');
    } catch (error) {

      console.error('회원가입 실패:', error);
    }
  };
  
  
  </script>
  