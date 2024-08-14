// src/components/KakaoLogin.vue
<template>
  <div>
    <p>로그인 성공! 잠시만 기다려 주세요...</p>
  </div>
</template>

<script>
export default {
  mounted() {
    const urlParams = new URLSearchParams(window.location.search);
    const accessToken = urlParams.get('access');
    console.log(accessToken);
    if (accessToken ) {
      // 토큰을 로컬 스토리지에 저장
      localStorage.setItem('access', 'Bearer '+ accessToken);
      // 필요에 따라 추가적인 작업을 수행
      this.$router.push('/'); // 홈으로 리다이렉트
    } else {
      console.error('토큰이 존재하지 않습니다.');
    }
  },
};
</script>
