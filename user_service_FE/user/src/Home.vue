<template>
  <div>
    <h1>Home Page</h1>
    <p>이곳은 홈 페이지입니다.</p>
  </div>
  
  
    <h2>회원 정보</h2>
    <p>이메일: {{ userInfo.userEmail }}</p>
    <p>이메일: {{ userInfo.userNickname }}</p>
    <p>이메일: {{ userInfo.userRole }}</p>

    <div>
        <input type="button" @click="getUserInfo" value="asdf"/> 
    </div>
  </template>
  
<script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import axios from '@/assets/axios';
  import { useUserStore } from '@/stores/user';

  const { userInfo } = useUserStore();
  console.log(userInfo.value.userEmail);

  const getUserInfo = async ()=>{
  try {
    const response = await axios.get('/users/info');
    console.log('User Info:', response.data);
  } catch (error) {
    console.error('User Info Fetch Error:', error);
  }
};



const router = useRouter();


</script>