import axios from 'axios';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';
const router = useRouter();

// Axios 인스턴스 생성
const instance = axios.create({
  baseURL: 'http://localhost:8765',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true  // withCredentials 추가
});

// 요청 인터셉터: access token을 헤더에 추가
instance.interceptors.request.use(config => {
  const token = localStorage.getItem('access');
  if (token) {
    config.headers.access = token;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

// 응답 인터셉터: 406 응답 시 새 access token 처리
instance.interceptors.response.use(response => {
  return response;
}, async error => {
  const originalRequest = error.config;

  // 406 상태코드 처리 (새로운 access token 수신 시)
  if (error.response.status === 406 && error.response.headers['access']) {
    const newAccessToken = error.response.headers['access'];
    console.log(`new Access token: ${newAccessToken}`);
    // 새로운 access token 저장
    localStorage.setItem('access', newAccessToken);
    // Authorization 헤더 갱신 및 요청 재시도
    originalRequest.headers.Authorization = newAccessToken;
    return instance(originalRequest);
  }
  
  // 403 상태코드 처리 (새로운 access token 수신 시)
  else if (error.response.status === 403 ) {
    localStorage.removeItem('access');
    router.push('/Login');
    return ;
  }


  return Promise.reject(error);
});

export default instance;
