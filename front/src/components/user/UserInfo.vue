<template>
  <div>
    <h2>회원 정보</h2>
    <!-- userData 값이 있을 경우에만 출력 -->
    <p>이메일: {{ userData.userEmail || '로그인 필요' }}</p>
    <p>닉네임: {{ userData.userNickname || '로그인 필요' }}</p>
    <p>권한: {{ userData.userRole || '로그인 필요' }}</p>
  </div>

  <div>
    <input type="button" @click="getUserInfo" value="정보 가져오기" /> 
    <input type="button" @click="getUsersTest" value="유저 목록 테스트" /> 
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import axiosWithToken from '@/assets/axiosWithToken';
import { useUserStore } from '@/stores/user';

const router = useRouter();


const userData = ref({
  userEmail: '',
  userNickname: '',
  userRole: ''
});

const { userInfo } = useUserStore();

// userInfo가 변경될 때마다 userData를 업데이트
watch(userInfo, (newUserInfo) => {
  if (newUserInfo) {
    userData.value = newUserInfo;
  } else {
    userData.value = {
      userEmail: '',
      userNickname: '',
      userRole: ''
    };
  }
});

// 현재 로그인 정보가 있는지 확인
if (userInfo.value) {
  userData.value = userInfo.value;
}else{
  router.push('/login');
}

const getUserInfo = async () => {
  try {
    const response = await axiosWithToken.get('/users/info');
    console.log('User Info:', response.data);
  } catch (error) {
    console.error('User Info Fetch Error:', error);
    router.push('/login');
  }
};


</script>
