import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ToiletRegistList from '@/components/ToiletRegistList.vue'
import ToiletDetails from '@/components/ToiletDetails.vue';
import RegisterToilet from '@/components/RegisterToilet.vue';
import UserInfo from '@/components/user/UserInfo.vue';
import Login from '@/components/user/Login.vue';
import KakaoLogin from '@/components/user/KakaoLogin.vue';
import Regist from '@/components/user/Regist.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'HomeView',
      component: HomeView
    },
    {
      path: '/toiletRegistList',
      name: 'ToiletRegistList',
      component: ToiletRegistList
    },
    { path: '/details/:id',
      name: 'ToiletDetails',
      component: ToiletDetails,
      props: true
    },
    { path: '/register',
      name: 'RegisterToilet',
      component: RegisterToilet
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
    },
    {
      path: '/regist',
      name: 'Regist',
      component: Regist,
    },
    {
      path: '/userInfo',
      name: 'UserInfo',
      component: UserInfo,
    }

  ]
})

export default router
