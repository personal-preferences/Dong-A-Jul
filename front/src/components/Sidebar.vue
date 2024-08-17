<template>
  <div :class="['sidebar', { 'active': isSidebarOpen }]">
    <div class="p-3">
      <!-- 검색창 -->
      <input type="text" class="form-control mb-3" placeholder="Search..." />

      <!-- 메뉴 리스트 -->
      <ul class="list-unstyled ps-0">
        <li><a href="#" class="link-dark rounded">메뉴1</a></li>
        <li><a href="#" class="link-dark rounded">메뉴2</a></li>
        <li><a href="#" class="link-dark rounded">메뉴3</a></li>
        <li><a href="#" class="link-dark rounded">메뉴4</a></li>
      </ul>
    </div>

    <!-- 토글 버튼 -->
    <button class="btn btn-primary sidebar-toggle" @click="$emit('toggle-sidebar')">
      {{ isSidebarOpen ? '<' : '>' }}
    </button>
  </div>
</template>

<script setup>
defineProps({
  isSidebarOpen: Boolean,
});
</script>

<style scoped>
.sidebar {
  width: 250px;
  transition: transform 0.3s ease;
  background-color: #f8f9fa;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  transform: translateX(-100%);
}

.sidebar.active {
  transform: translateX(0);
}

.sidebar-toggle {
  position: absolute;
  top: 50%;
  right: -37px;
  transform: translateY(-50%);
  z-index: 1000;
  font-weight: bold;
}
</style>
