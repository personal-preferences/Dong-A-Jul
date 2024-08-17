<script setup>
// import { RouterView } from 'vue-router'
</script>

<template>
  <div>
   <nav>
      <router-link to="/">Home</router-link>
      &nbsp;
      <router-link to="/login">Login</router-link>
      &nbsp;
      <router-link to="/regist">Regist</router-link>
      &nbsp;
      <router-link to="/userInfo">userInfo</router-link>
    </nav>
  <RouterView />
  </div>
</template>

<style scoped>

</style>
