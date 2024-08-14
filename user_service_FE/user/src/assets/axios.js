import axios from 'axios';

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

export default instance;
