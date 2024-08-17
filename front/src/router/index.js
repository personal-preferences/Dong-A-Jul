import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import ToiletRegistList from '@/components/ToiletRegistList.vue'
import ToiletDetails from '@/components/ToiletDetails.vue';
import RegisterToilet from '@/components/RegisterToilet.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
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

    // {
    //   path: '/',
    //   name: 'home',
    //   component: HomeView
    // },
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue')
    // }
  ]
})

export default router
