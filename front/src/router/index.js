import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ToiletRegistList from '@/components/ToiletRegistList.vue'
import ToiletDetails from '@/components/ToiletDetails.vue';
import RegisterToilet from '@/components/RegisterToilet.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'HomeView',
      component: HomeView
    },
    {
      path: '/details/list',
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
  ]
})

export default router
