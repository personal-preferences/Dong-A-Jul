import { ref, readonly } from 'vue'
import { jwtDecode } from 'jwt-decode'

const userInfo = ref(null)

export function useUserStore() {
  function setUser(token) {
    if (token) {
      const tokenWithoutBearer = token.replace('Bearer ', '');
      localStorage.setItem('access', token)  // 원본 토큰 저장
      try {
        userInfo.value = jwtDecode(tokenWithoutBearer)
      } catch (error) {
        console.error('Token decoding error:', error)
        userInfo.value = null
      }
    } else {
      localStorage.removeItem('access')
      userInfo.value = null
    }
  }

  function logout() {
    setUser(null)
  }

  // 초기화: 페이지 새로고침 시에도 사용자 정보 유지
  const storedToken = localStorage.getItem('access')
  if (storedToken) {
    setUser(storedToken)
  }

  

  return {
    userInfo: readonly(userInfo),
    setUser,
    logout
  }
}