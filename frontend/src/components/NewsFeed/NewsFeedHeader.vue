<template>
  <div>
    <div class="newsfeed-header-title">
      {{ Category }}
      <!-- <i class="fas fa-house-user">{{ Category }}</i> -->
    </div>
    <!-- <div class="newsfeed-header-search">
      <i class="fas fa-search"></i>
      <input type="text" v-model="keyword" @keyup.enter="goToSearch">
    </div> -->
  </div>
</template>

<script>
export default {
  name: "NewsFeedHeader",
  props: {
    Category: Number,
  },
  data() {
    return {};
  },
  methods: {},
};
</script>

<style scoped></style>
