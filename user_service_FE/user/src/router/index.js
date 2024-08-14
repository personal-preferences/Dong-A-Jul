// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/Home.vue';
import Login from '@/Login.vue';
import KakaoLogin from '@/KakaoLogin.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/kakaoLogin',
    name: 'KakaoLogin',
    component: KakaoLogin,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
