import { reactive, computed } from 'vue';

const state = reactive({
  user: null
});

export const useUserStore = () => {
  const setUser = (user) => {
    state.user = user;
  };

  const getUser = computed(() => state.user);

  return {
    setUser,
    getUser
  };
};
