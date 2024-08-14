// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/Home.vue';
import Login from '@/Login.vue';

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
  // 필요한 다른 경로를 추가
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
