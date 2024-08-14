<template>
    <div>
      <h1>Home Page</h1>
      <p>이곳은 홈 페이지입니다.</p>
    </div>
    <div>
        <input type="button" @click="getUserInfo" value="asdf"/> 
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import axios from '@/assets/axios';
  const getUserInfo = async ()=>{
  try {
    const response = await axios.get('/users/info');
    console.log('User Info:', response.data);
  } catch (error) {
    console.error('User Info Fetch Error:', error);
  }
};
  </script>
  