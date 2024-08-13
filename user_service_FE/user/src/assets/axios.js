import axios from 'axios';

// Axios 인스턴스 생성
const instance = axios.create({
  baseURL: 'http://localhost:8765',
  headers: {
    'Content-Type': 'application/json',
  }
});

// 요청 인터셉터: access token을 헤더에 추가
instance.interceptors.request.use(config => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

// 응답 인터셉터: token refresh 처리
instance.interceptors.response.use(response => {
  return response;
}, async error => {
  if (error.response.status === 401) {
    // Refresh token으로 새 access token 요청 처리
    try {
      const refreshToken = getCookie('refreshToken');
      const response = await axios.post('/api/refresh-token', {}, {
        headers: { 'Authorization': `Bearer ${refreshToken}` }
      });
      
      // 새로운 access token 저장
      localStorage.setItem('accessToken', response.data.accessToken);
      
      // 요청을 다시 시도
      error.config.headers['Authorization'] = `Bearer ${response.data.accessToken}`;
      return axios(error.config);
    } catch (refreshError) {
      console.error('토큰 갱신 실패:', refreshError);
      // 로그아웃 처리 및 로그인 화면으로 리디렉션
      localStorage.removeItem('accessToken');
      document.cookie = 'refreshToken=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/;';
      window.location.href = '/login';
    }
  }
  return Promise.reject(error);
});

// 쿠키에서 값 가져오기
const getCookie = (name) => {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
};

export default instance;
